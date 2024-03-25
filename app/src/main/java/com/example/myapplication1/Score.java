package com.example.myapplication1;


import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Score {    //Class Variables.
    public int score_value;
    String mode;
    String playerName;
    String UUID;
    public  Object timeStamp;


    public Score(){
        /*TODO: Default constructor is required for calls to DataSnapshot.getValue(Score.class)*/
    }

    public Score(int score_value){
        this.score_value = score_value;
        this.mode = "unknown";
        this.playerName = "anonymous";
        this.UUID = "anonymous";

        // Firebase hooks
        this.timeStamp = ServerValue.TIMESTAMP;
    } //End Constructor

    public Score(int score_value, String UUID, String playerName, String mode){
        this.score_value = score_value;
        this.mode = mode;
        this.playerName = playerName;
        this.UUID = UUID;

        // Firebase hooks
        this.timeStamp = ServerValue.TIMESTAMP;
    } //End Constructor

    public int getScore_value(){ return this.score_value; }
    public String getMode(){ return this.mode; }
    public Object getPlayerName(){ return this.playerName; }
    public String getUUID(){ return this.UUID; }
    public Object getTimeStamp(){ return this.timeStamp; }

    public void setMode( String mode){ this.mode = mode; }
    public void setPlayerName( String playerName ){ this.playerName = playerName; }
} //end class Score definition
