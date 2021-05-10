package com.types;

import android.content.Context;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface CheckFiles {

    default List<String> checkWhatIsMade(Context context) {
        String recordPath = context.getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(recordPath);
        List<File> allFiles = Arrays.asList(directory.listFiles());
        List<String> result = allFiles.stream()
                .map(File::getName)
                .map(name -> name.substring(0, name.indexOf("-")))
                .collect(Collectors.toList());
        return result;
    }

    //void check();

//    static List<String> checkWhatIsMade(Context context){
//        String recordPath = context.getExternalFilesDir("/").getAbsolutePath();
//        File directory = new File(recordPath);
//        List<File> allFiles = Arrays.asList(directory.listFiles());
//        List<String> result = allFiles.stream()
//                .map(File::getName)
//                .map(name -> name.substring(0, name.indexOf("-")))
//                .collect(Collectors.toList());
//        return result;
//    }
//
    static File[] createListOfRecordings(Context context){
        String recordPath = context.getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(recordPath);
        return directory.listFiles();
    }
}
