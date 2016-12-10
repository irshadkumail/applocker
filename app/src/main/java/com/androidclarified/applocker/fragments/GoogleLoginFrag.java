package com.androidclarified.applocker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.activities.SplashActivity;
import com.androidclarified.applocker.utils.AppConstants;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krazybee on 12/9/2016.
 */

public class GoogleLoginFrag extends Fragment implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private SignInButton signInButton;

    private TextView continueText;

    private GoogleSignInOptions googleSignInOptions;

    private GoogleApiClient googleApiClient;

    private static final int SIGN_IN_CODE = 101;


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_google_login, parent, false);
        init(rootView);

        return rootView;
    }

    private void init(View rootView) {

        signInButton = (SignInButton) rootView.findViewById(R.id.frag_google_login_btn);
        continueText = (TextView) rootView.findViewById(R.id.frag_gmail_continue);
        signInButton.setOnClickListener(this);
        continueText.setOnClickListener(this);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_google_login_btn:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, SIGN_IN_CODE);
                break;
            case R.id.frag_gmail_continue:
                AppSharedPreferences.putUserInfoPreference(getContext(), AppConstants.USER_INFO_EMPTY);
                ((SplashActivity)getActivity()).addPasswordFragment();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SIGN_IN_CODE:
                    try {
                        GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
                        handleSignInResult(googleSignInResult);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult) throws JSONException {
        if (googleSignInResult.isSuccess()) {
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            JSONObject userInfoJson = new JSONObject();
            userInfoJson.put("display_name", account.getDisplayName());
            userInfoJson.put("email", account.getEmail());
            userInfoJson.put("user_pic", account.getPhotoUrl());
            AppSharedPreferences.putUserInfoPreference(getContext(), userInfoJson.toString());
            Toast.makeText(getContext(),""+userInfoJson.toString(),Toast.LENGTH_SHORT);
            ((SplashActivity)getActivity()).addPasswordFragment();
        }else {
            Toast.makeText(getContext(),"Sign in Fialed",Toast.LENGTH_SHORT);
        }


    }
}
