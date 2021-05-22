package com.example.saymyname;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adapters.AudioListSelectedAdapter;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.DataStoreException;
import com.amplifyframework.datastore.generated.model.Recording;
import com.amplifyframework.storage.StorageAccessLevel;
import com.amplifyframework.storage.StorageException;
import com.amplifyframework.storage.options.StorageUploadFileOptions;
import com.amplifyframework.storage.result.StorageUploadFileResult;
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
    private AWSCognitoAuthSession cognito;

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
        //cognito from stack
//        Amplify.Auth.fetchAuthSession(
//                result -> {
//                    AWSCognitoAuthSession cognitoAuthSession = (AWSCognitoAuthSession) result;
//                    cognito = (AWSCognitoAuthSession) result;
//                    switch(cognitoAuthSession.getIdentityId().getType()) {
//                        case SUCCESS:
//                            Log.i("AuthQuickStart", "IdentityId: " + cognitoAuthSession.getIdentityId().getValue());
//                            break;
//                        case FAILURE:
//                            Log.i("AuthQuickStart", "IdentityId not present because: " + cognitoAuthSession.getIdentityId().getError().toString());
//                    }
//                },
//                error -> Log.e("AuthQuickStart", error.toString())
//        );
        //end

        List<String> fileNamesForSend = new LinkedList<>();
        for (String name : newAllFiles.keySet()) {
            if (newAllFiles.get(name)) {
                fileNamesForSend.add(name);
            }
        }
        File[] files = CheckFiles.createListOfRecordings(this.getApplicationContext());
        for (File file : files) {
            if (fileNamesForSend.contains(file.getName())) {
                String name = file.getName().substring(0,file.getName().indexOf("-"));
//                Amplify.Storage.uploadFile("Maciej",
//                        new File(fileNamesForSend.get(0)),
//                        this::onSendSuccess,
//                        this::onSendError); arn:aws:s3:::recordings220358-dev
                try {
                    Amplify.Storage.uploadFile(name,
                            file,
                            this::onSendSuccess,
                            this::onSendError);
                }catch(Exception e ){
                    Log.i("error", e.getMessage());
                }
//                try {
//                    Amplify.DataStore.save(,()->getThis() , this::onfail);
//                }catch(Exception e ){
//                    Log.i("error", e.getMessage());
//                }
            }
        }
        System.out.println("trelo");
    }

    private void onSendError(StorageException e) {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Send operation failed!", Toast.LENGTH_LONG).show());
    }

    private void onSendSuccess(StorageUploadFileResult storageUploadFileResult) {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "file send: " + storageUploadFileResult.getKey(), Toast.LENGTH_LONG).show());
    }
}
