package com.example.saymyname;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.core.Amplify;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordingActivity extends AppCompatActivity {

    private int PERMISSION_CODE = 21;
    //private NavController navController;

    private ImageButton listButton;
    private ImageButton recordButton;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private MediaRecorder mediaRecorder;
    private TextView fileNameText;

    //storing files
    String recordFile;

    //Record timer
    private Chronometer timer;

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        //timer = new Chronometer(getApplicationContext(), null, 0);
        timer = findViewById(R.id.record_timer);
        fileNameText = findViewById(R.id.record_filename);
        recordButton = findViewById(R.id.record_button);
        listButton = findViewById(R.id.record_list_button);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Amplify.Auth.signOut(
                this::onSuccess,
                this::onError
        );
    }

    private void onError(AuthException e) {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void onSuccess() {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "log out success", Toast.LENGTH_LONG).show());
    }

    public void recordButton(View view) {
        if (isRecording) {
            //Stop recording
            stopRecording();
            recordButton.setImageDrawable(getResources().getDrawable(R.drawable.microphone_on, null));
            isRecording = false;
        } else {
            //Start Recording
            if (checkPermissions()) {
                startRecording();
                recordButton.setImageDrawable(getResources().getDrawable(R.drawable.microphone_off, null));
                isRecording = true;
            }
        }
    }

    private void stopRecording() {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "recording stopped", Toast.LENGTH_LONG).show());
        this.timer.stop();
        this.timer.setBase(SystemClock.elapsedRealtime());
        fileNameText.setText("Recording stopped, File saved: " + recordFile);

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void startRecording() {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "recording started", Toast.LENGTH_LONG).show());
        this.timer.setBase(SystemClock.elapsedRealtime());
        this.timer.start();
        String recordPath = this.getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ENGLISH);
        Date now = new Date();
        //name of File
        recordFile = "Recording_" + formatter.format(now) + ".3gp";

        fileNameText.setText("Recording, File name: " + recordFile);

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE);
        }
        return false;
    }
}