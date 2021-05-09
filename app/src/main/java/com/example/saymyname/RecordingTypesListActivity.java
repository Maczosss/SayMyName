package com.example.saymyname;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.adapters.RecordingMessageTypeAdapter;
import com.amplifyframework.auth.AuthException;
import com.types.RecordingMessage;
import java.util.HashMap;
import java.util.Map;

public class RecordingTypesListActivity extends AppCompatActivity implements RecordingMessageTypeAdapter.onItemListClick {

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

//    @Override
//    public void onStop() {
//        super.onStop();
//        if (isPlaying) {
//            stopAudio();
//        }
//        Amplify.Auth.signOut(
//                this::onSuccess,
//                this::onError
//        );
//    }

    private void onError(AuthException e) {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "there was problem with logging out", Toast.LENGTH_LONG).show());
    }

    private void onSuccess() {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "log out success", Toast.LENGTH_LONG).show());
    }

    public void goToTypeListActivity(View view) {
    }
}