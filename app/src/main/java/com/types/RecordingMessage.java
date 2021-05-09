package com.types;

public enum RecordingMessage {
    WHERE_ARE_YOU("Where are You?"),
    SHOW_YOURSEF("Please <name> show Yourself"),
    COME_WITH_ME("Come with Me"),
    CALM_DOWN("Please calm down"),
    LISTEN_TO_THIS_PERSON_MALE("Listen, You can trust this man, he will help You until I come for You ok? Do everything that this mister is saying"),
    LISTEN_TO_THIS_PERSON_FEMALE("Listen, You can trust this woman, She will help You until I come for You ok? Do everything that this lady is saying");

    String message;

    RecordingMessage(String message){
        this.message=message;
    }

    public String getMessage(){
        return message;
    }
}
