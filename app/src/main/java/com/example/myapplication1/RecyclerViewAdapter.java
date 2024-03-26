package com.example.myapplication1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter< RecyclerViewAdapter.ViewHolder > {
    //Class RecyclerViewAdapter Variables.
    SimpleDateFormat localDateFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allScoresRef = database.getReference("Scores");
    ChildEventListener usersRefListener;
    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;
    private final List<ScoreModel> scoresList;
    private final RecyclerView recyclerView;


    /*Constructor RecyclerViewAdapter*/
    public RecyclerViewAdapter(RecyclerView recyclerView){
        scoresList = new ArrayList<>();
        this.recyclerView = recyclerView;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        allScoresRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                ScoreModel scoreModel = new ScoreModel( dataSnapshot.child("uuid").getValue().toString(),
                                                        dataSnapshot.getKey(),
                                                        Integer.parseInt(dataSnapshot.child("score_value").getValue().toString()),
                                                        dataSnapshot.child("timestamp").toString(),
                                                        dataSnapshot.child("playerName").toString(),
                                                        "Easy" );
                scoresList.add(scoreModel);
                RecyclerViewAdapter.this.notifyItemInserted( scoresList.size() -1);
                recyclerView.scrollToPosition(scoresList.size()-1);
            } //End callback function onChildAdded.

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                /*TODO: Currently does nothing.*/
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                /*TODO: Currently does nothing*/
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                /*TODO Currently Does nothing.*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /*TODO: Currently does nothing.*/
            }
        });
    } //End Constructor RecyclerViewAdaptor.


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent,false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    } //End onCreateViewHolder callback function.


    /*This function links each element of the view holder to the actual post in the post object
    * returned from the firebase realtime database.*/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Local Variables.
        //final PostModel u =postsList.get(position);
        final ScoreModel u = scoresList.get(position);
        String uid = u.uid; //Holds the UID of the creator of the current post. Use it to compare for permissions.

        //Remove the database reference listener.
        if(holder.uref!=null && holder.urefListener!=null)
            { holder.uref.removeEventListener(holder.urefListener); }


        //Otherwise we can assume that the database reference is valid references in the database.
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        holder.uref = database.getReference("Users").child(uid);

        //Check to see if the user owns the current score.
        String currentUserUID = mAuth.getUid();
        Log.d("CURRENTUSERUID", currentUserUID);
        String scoreOwnerUID = uid;
        Log.d("SCOREOWNERUID", scoreOwnerUID);

        //Get scores and add them to the recycler view
        holder.scoreReference = database.getReference("Scores").child(scoresList.get(position).scoreKey);
        holder.scoreReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String playerUUID = dataSnapshot.child("uuid").getValue().toString();
                DatabaseReference usersReference = database.getReference("Users/" + playerUUID);

                String playerName = usersReference.child("displayname").toString();


                Log.d("DISPLAY_NAME", playerName);
                String playerDisplayName = dataSnapshot.child("playerName").getValue().toString();
                if(playerDisplayName == null){ playerDisplayName = "";}
                holder.player_v.setText("Player: " + playerDisplayName);
                holder.score_v.setText("Score: " + dataSnapshot.child("score_value").getValue().toString() +
                                       " (" + dataSnapshot.child("mode").getValue().toString() + ")" );
                Long longDate = Long.parseLong(Objects.requireNonNull(dataSnapshot.child("timeStamp").getValue()).toString());
                holder.date_v.setText("Date: " + (localDateFormat.format( longDate)) );
                holder.timestamp_v.setText("Timestamp" + dataSnapshot.child("timeStamp").getValue().toString());
            } //End callback function onChange.

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /*TODO: Currently does nothing*/
            }
        });


    } //End function onBindViewHolder.


    public void removeListener(){
        if( allScoresRef !=null && usersRefListener!=null)
            allScoresRef.removeEventListener(usersRefListener);
    } //End function removeListener.


    @Override
    public int getItemCount() {
//        return postsList.size();
        return scoresList.size();
    } //End function getItemCount.


    /*Inner class definition ViewHolder*/
    public static class ViewHolder extends RecyclerView.ViewHolder{
        boolean canDelete = false;
        public TextView player_v;
        public TextView score_v;
        public TextView date_v;
        public TextView timestamp_v;
        DatabaseReference uref;
        ValueEventListener urefListener;
        DatabaseReference scoreReference;
        ValueEventListener scoreReferenceListener;

        public ViewHolder(View v){
            super(v);
            player_v = v.findViewById(R.id.player_view); //For the user name
            score_v = v.findViewById(R.id.score_view); //For the score.
            date_v = v.findViewById(R.id.date_view); //for the human readable date
            timestamp_v = v.findViewById(R.id.timestamp_view); //For the server timestamp.
        } //End Constructor ViewHolder.
    } //End inner class definition ViewHolder


    private class ScoreModel{
        //Inner class variables.
        public int score_value;
        public String mode;
        public String playerName;
        public String scoreKey;
        public String uid;
        public String timestamp;

        /*Class constructors*/
        public ScoreModel(String uid, String key, int score_value, String timestamp, String playerName, String mode) {
            this.uid = uid;
            this.scoreKey = key;
            this.playerName = playerName;
            this.score_value = score_value;
            this.timestamp = timestamp;
            this.mode = mode;
        } //End overloaded constructor PostModel
    }//End Inner class definition PostModel.

} //End class definition and implementation of RecyclerViewAdaptor.
