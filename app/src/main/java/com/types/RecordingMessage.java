package com.types;

import java.util.Arrays;
import java.util.List;

public enum RecordingMessage {
    WHERE_ARE_YOU("Where are You?"),
    SHOW_YOURSEF("Please <name> show Yourself"),
    COME_WITH_ME("Come with Me"),
    CALM_DOWN("Please calm down"),
    LISTEN_TO_THIS_PERSON_MALE("Listen, You can trust this man, he will help You until I come for You ok? Do everything that this mister is saying"),
    LISTEN_TO_THIS_PERSON_FEMALE("Listen, You can trust this woman, She will help You until I come for You ok? Do everything that this lady is saying");

    String message;
    boolean isSelected;

    RecordingMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return this.name();
    }

    public static RecordingMessage getById(int id){
        for(RecordingMessage type : RecordingMessage.values()){
            if(type.ordinal() == id){
                return type;
            }
        }
        return null;
    }

    public static List<RecordingMessage> getAll(){
        return Arrays.asList(RecordingMessage.values());
    }

    public void changeSelectedState() {
        if (isSelected)
            isSelected = false;
        else
            isSelected = true;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
