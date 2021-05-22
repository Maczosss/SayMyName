package com.example.saymyname;

import android.app.Application;
import android.util.Log;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider;
//import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class SayMyName extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            AmplifyModelProvider provider = AmplifyModelProvider.getInstance();

            Amplify.addPlugin(new AWSDataStorePlugin(provider));
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());

//            JsonObject jsonObject = new JsonParser().parse("eu-central-1").getAsJsonObject();
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = new JSONObject("{\"Region\":\"eu-central-1\"}");
//            }catch (JSONException err){
//                Log.d("Error", err.toString());
//            }
//
//            AWSS3StoragePlugin plugin = new AWSS3StoragePlugin();
//            plugin.configure(jsonObject,getApplicationContext());
            ///Amplify.addPlugin(new AWSS3StoragePlugin());

            //"Region": "eu-central-1",

            Amplify.configure(getApplicationContext());
            Log.i("amplify", "configured!");
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
    }
}
