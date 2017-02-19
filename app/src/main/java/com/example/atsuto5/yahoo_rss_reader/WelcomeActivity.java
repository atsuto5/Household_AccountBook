//
//
// Copyright 2017 Kii Corporation
// http://kii.com
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
//

package com.example.atsuto5.yahoo_rss_reader;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.atsuto5.yahoo_rss_reader.kiiobject.Constants;
import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;

public class WelcomeActivity extends Activity {

    private static final String TAG = "WelcomeActivity";

    // Define the UI elements.
    private TextView mUsernameField;
    private TextView mPasswordField;
    private ProgressDialog mProgress;
    String mUserName;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        // Link the variables to the UI elements.
        mUsernameField = (TextView) findViewById(R.id.username_field);
        mPasswordField = (TextView) findViewById(R.id.password_field);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Yahoo! Login!");
        toolbar.setTitleMargin(360,0,0,0);

        if (savedInstanceState == null) {
            // initialize
            initKiiSDK();

            // check access token
            String token = Pref.getStoredAccessToken(getApplicationContext());
            if (TextUtils.isEmpty(token)) {
                return;
            }

            // check access token
//            ProgressDialogFragment progress = ProgressDialogFragment.newInstance(getString(R.string.login), getString(R.string.login));
//            progress.show(getSupportFragmentManager(), ProgressDialogFragment.FRAGMENT_TAG);

            // login with token
            KiiUser.loginWithToken(new KiiUserCallBack() {
                @Override
                public void onLoginCompleted(int token, KiiUser user, Exception e) {
                    super.onLoginCompleted(token, user, e);
                    //ProgressDialogFragment.hide(getSupportFragmentManager());

                    // error check
                    if (e != null) {
                        // go to normal login
                        //showTitlePage();
                        return;
                    }
                    // login is succeeded then go to List Fragment
                    //showListPage();
                    goToMainActivity();
                }
            }, token);
        } else {
            // Restore Kii SDK states
            Kii.onRestoreInstanceState(savedInstanceState);
        }

    }

    /**
     * Initialize KiiSDK
     * Please change APP_ID/APP_KEY to your application
     */
    private void initKiiSDK() {

        Kii.initialize(
                Constants.APP_ID,  // Put your App ID
                Constants.APP_KEY, // Put your App Key
                Kii.Site.JP            // Put your site as you've specified upon creating the app on the dev portal
        );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Kii SDK states
        Kii.onSaveInstanceState(outState);
    }





    // Called by the "Sign Up" button.
    public void handleSignUp(View v) {

        // Show a progress dialog.
        mProgress = ProgressDialog.show(WelcomeActivity.this, "",
                "登録しています・・・", true);

        // Get the username and password from the UI.
        mUserName = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        Log.v(TAG, "Registering: " + mUserName + ":" + password);

        // Create a KiiUser object.
        try {
            KiiUser user = KiiUser.createWithUsername(mUserName);
            // Register the user asynchronously.
            user.register(new KiiUserCallBack() {

                // Catch the result from the callback method.
                public void onRegisterCompleted(int token, KiiUser user,
                                                Exception e) {

                    // Hide the progress dialog.
                    mProgress.cancel();

                    // Check for an exception. The request was successfully processed if e==null.
                    if (e == null) {

                        // Tell the console and the user that the registration was successful.
                        Log.v(TAG, "Registered: " + user.toString());
                        showToast("登録完了しました!");

                        SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = data.edit();
                        editor.putString("USERNAME",mUserName);
                        editor.apply();

                        // Go to the main screen.
                        goToMainActivity();

                    }

                    // A failure occurred when processing the request.
                    else {

                        // Tell the console and the user that the registration failed.
                        Log.v(TAG, "Error registering: " + e.getLocalizedMessage());
                        showToast("Error registering: " + e.getLocalizedMessage());

                    }

                }

            }, password);

        } catch (Exception e) {
            mProgress.cancel();
            showToast("Error signing up: " + e.getLocalizedMessage());
        }

    }

    // Called by the "Log In" button.
    public void handleLogin(View v) {

        // Show a progress dialog.
        mProgress = ProgressDialog.show(WelcomeActivity.this, "",
                "ログインしています...", true);

        // Get the username and password from the UI.
        mUserName = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        Log.v(TAG, "Logging in: " + mUserName + ":" + password);

        // Authenticate the user asynchronously.
        KiiUser.logIn(new KiiUserCallBack() {

            // Catch the result from the callback method.
            public void onLoginCompleted(int token, KiiUser user, Exception e) {

                // Hide the progress dialog.
                mProgress.cancel();

                // Check for an exception. The request was successfully processed if e==null.
                if (e == null) {

                    // Tell the console and the user that the login was successful.
                    Log.v(TAG, "Logged in: " + user.toString());
                    showToast("ログインしました!");

                    SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putString("USERNAME",mUserName);
                    editor.apply();

                    // Go to the main screen.
                    goToMainActivity();

                }

                // A failure occurred when processing the request.
                else {

                    // Tell the console and the user that the login failed.
                    Log.v(TAG, "Error authenticating: " + e.getLocalizedMessage());
                    showToast("Error authenticating: " + e.getLocalizedMessage());

                }

            }

        }, mUserName, password);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void goToMainActivity(){
        Intent myIntent = new Intent(WelcomeActivity.this,
                MainActivity.class);
        WelcomeActivity.this.startActivity(myIntent);
    }
}
