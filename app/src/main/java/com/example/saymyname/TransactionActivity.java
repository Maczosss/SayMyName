package com.example.saymyname;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adapters.AudioListSelectedAdapter;
import com.types.CheckFiles;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity implements CheckFiles, AudioListSelectedAdapter.OnItemListClick {

    private RecyclerView audioList;
    private Map<String, Boolean> newAllFiles = new HashMap<>();
    private File[] allFiles;

    private AudioListSelectedAdapter audioListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        audioList = findViewById(R.id.transaction_recording_list_view);

        allFiles = CheckFiles.createListOfRecordings(this.getApplicationContext());

        for (File file : allFiles) {
            newAllFiles.put(file.getName(), false);
        }

        audioListAdapter = new AudioListSelectedAdapter(allFiles, this);

        audioList.setHasFixedSize(true);
        audioList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        audioList.setAdapter(audioListAdapter);

    }

    @Override
    public void onClickListener(File file, int position) {
        if (file != null) {
            boolean whatState = newAllFiles.get(file.getName());
            if (whatState) {
                newAllFiles.computeIfPresent(file.getName(), (k, v) -> v = false);
            }
            if (!whatState) {
                newAllFiles.computeIfPresent(file.getName(), (k, v) -> v = true);
            }
        }
    }

    public void deleteSelectedFiles(View view) {
        List<String> fileNamesForDeletion = new LinkedList<>();
        for (String name : newAllFiles.keySet()) {
            if (newAllFiles.get(name)) {
                fileNamesForDeletion.add(name);
            }
        }
        File[] files = CheckFiles.createListOfRecordings(this.getApplicationContext());
        for (File file : files) {
            if (fileNamesForDeletion.contains(file.getName())) {
                if (file.delete())
                    Log.i("deletion", "deletion passed");
                else
                    Log.i("deletion", "deletion failed");
            }
        }
        allFiles = CheckFiles.createListOfRecordings(this.getApplicationContext());

        audioListAdapter.notifyDataSetChanged();
    }

    public void sendSelectedFiles(View view) {

    }
}
