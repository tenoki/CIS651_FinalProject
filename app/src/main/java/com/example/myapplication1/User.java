package com.example.myapplication1;

import com.google.firebase.database.ServerValue;

/*This is the definition of the class User.
* This class is used to hold the information of either the user currently logged in and/or a newly
* created user.*/
public class User {
    //Class variables.
    public String displayname;
    public String email;
    public String phone;
    public Object timestamp;

    /*Overloaded Constuctior of the User class.*/
    public User(String displayname, String email, String phone) {
        this.displayname = displayname;
        this.email = email;
        this.phone = phone;
        this.timestamp = ServerValue.TIMESTAMP;
    } //End overloaded constructor.

    /*This function returns a timestamp object to the calling function.
    * This function takes a void value as a parameter and returns an generic Object type.
    * It is the respoonsibility of the caller to know that this object can be casted to a
    * ServerValue.TIMESTAMP type.*/
    public Object getTimestamp(){
        return timestamp;
    } //End function getTimeStamp
} //End class User definition.