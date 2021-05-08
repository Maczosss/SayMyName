package com.example.saymyname;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AudioListActivity extends AppCompatActivity implements AudioListAdapter.onItemListClick {

    //TODO previous and next buttons on mediaplayer

    private ConstraintLayout playerSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView audioList;
    private Map<String, Integer> newAllFiles = new HashMap<>();
    private File[] allFiles;
            int id = 0;


    private AudioListAdapter audioListAdapter;

    //audio play
    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;
    private File fileToPlay = null;

    //UI elements buttons
    private ImageButton playButton;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private TextView playerHeader;
    private TextView playerFileName;

    private SeekBar playerSeekBar;
    private Handler seekBarHandler;
    private Runnable updateSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);

        playerSheet = findViewById(R.id.player_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet);
        audioList = findViewById(R.id.audio_list_view);

        playButton = findViewById(R.id.player_play_button);
        nextButton = findViewById(R.id.player_next_button);
        previousButton = findViewById(R.id.player_previous_button);

        playerHeader = findViewById(R.id.player_header_title);
        playerFileName = findViewById(R.id.player_fileName);

        playerSeekBar = findViewById(R.id.player_seekBar);

        String recordPath = this.getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(recordPath);
        allFiles = directory.listFiles();

        for (File file : allFiles) {
            newAllFiles.put(file.getName(), id);
            id++;
        }

        audioListAdapter = new AudioListAdapter(allFiles, this);

        audioList.setHasFixedSize(true);
        audioList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        audioList.setAdapter(audioListAdapter);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //nothing here 404
            }
        });

        playButton.setOnClickListener(v -> {
            if (isPlaying) {
                pauseAudio();
            } else {
                if (fileToPlay != null)
                    resumeAudio();
            }
        });

        previousButton.setOnClickListener(v -> {
            if(fileToPlay!=null) {
                if (isPlaying) {
                    pauseAudio();
                    startPreviousAudio(true);
                }
                else
                startPreviousAudio(false);
            }

        });

        nextButton.setOnClickListener(v -> {
            if (fileToPlay != null) {
                if (isPlaying) {
                    pauseAudio();
                    startNextAudio(true);
                }
                else
                startNextAudio(false);
            }
        });

        playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseAudio();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (fileToPlay != null) {
                    int progress = seekBar.getProgress();
                    mediaPlayer.seekTo(progress);
                    resumeAudio();
                }
            }
        });
    }

    private void startPreviousAudio(boolean wasPlaying) {
        int position = newAllFiles.get(fileToPlay.getName());
        if ((position-1) >= 0)
            fileToPlay = allFiles[position-1];
        if (wasPlaying) {
            stopAudio();
            playAudio(fileToPlay);
        }
        else{
            playAudio(fileToPlay);
        }
    }

    //next and previous buttons
    private void startNextAudio(boolean wasPlaying) {
        int position = newAllFiles.get(fileToPlay.getName());
        if ((position + 1) < allFiles.length)
            fileToPlay = allFiles[position + 1];
        if (wasPlaying) {
            stopAudio();
            playAudio(fileToPlay);
        }
        else{
            playAudio(fileToPlay);
        }
    }

    @Override
    public void onClickListener(File file, int position) {
        fileToPlay = file;
        if (isPlaying) {
            stopAudio();
            playAudio(fileToPlay);
        } else {
            playAudio(fileToPlay);
        }
    }

    private void pauseAudio() {
        playButton.setImageDrawable(this.getResources().getDrawable(R.drawable.play, null));
        mediaPlayer.pause();
        isPlaying = false;
        seekBarHandler.removeCallbacks(updateSeekbar);
    }

    private void resumeAudio() {
        playButton.setImageDrawable(this.getResources().getDrawable(R.drawable.pause, null));
        mediaPlayer.start();
        isPlaying = true;
        updateRunnable();
        seekBarHandler.postDelayed(updateSeekbar, 0);
    }

    private void stopAudio() {
        playButton.setImageDrawable(this.getResources().getDrawable(R.drawable.play, null));
        playerHeader.setText("Stopped");
        isPlaying = false;
        mediaPlayer.stop();
        seekBarHandler.removeCallbacks(updateSeekbar);
    }

    private void playAudio(File fileToPlay) {
        mediaPlayer = new MediaPlayer();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        try {
            mediaPlayer.setDataSource(fileToPlay.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playButton.setImageDrawable(this.getResources().getDrawable(R.drawable.pause, null));
        playerFileName.setText(fileToPlay.getName());
        playerHeader.setText("Playing");

        //Play audio file
        isPlaying = true;

        mediaPlayer.setOnCompletionListener(mp -> {
            stopAudio();
            playerHeader.setText("Finished");
        });

        playerSeekBar.setMax(mediaPlayer.getDuration());

        seekBarHandler = new Handler();
        updateRunnable();
        seekBarHandler.postDelayed(updateSeekbar, 0);
    }

    private void updateRunnable() {
        updateSeekbar = new Runnable() {
            @Override
            public void run() {
                playerSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                seekBarHandler.postDelayed(this, 500);
            }
        };
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
}