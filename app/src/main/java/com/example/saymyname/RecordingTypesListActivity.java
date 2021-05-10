package com.example.saymyname;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.adapters.RecordingMessageTypeAdapter;
import com.types.RecordingMessage;
import java.util.HashMap;
import java.util.Map;

public class RecordingTypesListActivity extends AppCompatActivity implements RecordingMessageTypeAdapter.OnItemListClick {

//TODO previous and next buttons on mediaplayer

    private RecyclerView audioList;
    private Map<String, Integer> newAllFiles = new HashMap<>();
    private RecordingMessage[] alltypes;
    int id = 0;

    private RecordingMessageTypeAdapter recordingMessageTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_type_list);

        audioList = findViewById(R.id.recording_type_list_view);

        alltypes = RecordingMessage.values();

        for (RecordingMessage message : alltypes) {
            newAllFiles.put(message.getName(), id);
            id++;
        }

        recordingMessageTypeAdapter = new RecordingMessageTypeAdapter(alltypes, this);

        audioList.setHasFixedSize(true);
        audioList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        audioList.setAdapter(recordingMessageTypeAdapter);

        //to this
    }

    @Override
    public void onClickListener(RecordingMessage type, int position) {
        Intent intent = new Intent(this, RecordingActivity.class);
        intent.putExtra("type", type.ordinal());
        startActivity(intent);
    }

    public void goToTypeListActivity(View view) {
    }
}