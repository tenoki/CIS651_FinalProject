package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*This class deals with calling the photo handler application.
* This class calls the photo provider app, receives an image from it, and then gives the user the
* ability to preview the photo before posting it as a post to the original caller.
* This class is aware of the card view layout requirements and the postList item requirements.*/
public class PhotoPreview extends AppCompatActivity {

    /*This is the inner class, post which will be bassed to the original caller if the picture/post
    * is approved by the user.*/
    public static class Post {
        //Inner Class Variables.
        public String uid;
        public String url;
        public Object timestamp;
        public String description;
        public int likeCount = 0;
        public Map<String, Boolean> likes = new HashMap<>();


        /*Class constructors*/
        public Post() {
            /*TODO: This is an empty default constructor. Consider initializing variables here.*/
        } //End Default Constructor Post.


        public Post(String uid, String url, String description)
        {
            this.uid = uid;
            this.url = url;
            this.description = description;
            this.timestamp = ServerValue.TIMESTAMP;
        } //End overloaded constructor Posts.


        /*This class returns a generic object that contains a timestamp.
        * It is the responsibility of the caller to know that this generic object can be cast up to a ServerValue.TIMESTAMP type */
        public Object getTimestamp(){
            return timestamp;
        } //End function getTimestamp.
    } //End inner class Post definition and implementation.

    //Outer class variables.
    Uri uri;
    String path;
    EditText description;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        String l = getIntent().getStringExtra("uri");
        uri= Uri.fromFile(new File(l));
        ImageView imageView=findViewById(R.id.previewImage);
        imageView.setImageURI(uri);
        description=findViewById(R.id.description);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    } //End onCreate callback function.


    /*This function will try to uploade the approved image to the firebase firestore storage medium.
    * The storage bucket needs to be setup with the proper permissions or else the image will not
    * be able to be uploaded to firebase.*/
    private void uploadImage(){
        //Local Variables.
        FirebaseStorage storage= FirebaseStorage.getInstance();
        final String fileNameInStorage= UUID.randomUUID().toString();
        String path="images/"+ fileNameInStorage+".jpg";
        final StorageReference imageRef=storage.getReference(path);

        //Attempt to upload the image to firestore.
        imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override public void onSuccess(final Uri uri) {
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference postsRef = database.getReference("Posts");
                        DatabaseReference newPostRef = postsRef.push();
                        newPostRef.setValue(new Post(currentUser.getUid(),uri.toString(),description.getText().toString()))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override public void onSuccess(Void aVoid) {
                                        Toast.makeText(PhotoPreview.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } //End on successful upload.
                }).addOnFailureListener(new OnFailureListener() {
                    @Override public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PhotoPreview.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } //End on uploade failure.
                });
            } //end on successful uploade.
        }).addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(PhotoPreview.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    } //End uploadImage function.


    /*This function calls the helper function uploadImage to try and uloade the approved image to
    * firestore and then publish the post.*/
    public void Publish(View view){
        uploadImage();
        finish();
    } //End function Publish
} //End PhotoPreview class definition and implementation.