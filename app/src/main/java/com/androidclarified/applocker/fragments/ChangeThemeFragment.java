package com.androidclarified.applocker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.adapters.ChangeThemeAdapter;
import com.androidclarified.applocker.listeners.OnThemeSelectedListener;
import com.androidclarified.applocker.utils.AppConstants;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by My Pc on 11/6/2016.
 */

public class ChangeThemeFragment extends Fragment implements View.OnClickListener,OnThemeSelectedListener {

    private RecyclerView themeRecycler;
    private ChangeThemeAdapter changeThemeAdapter;
    private Button pickGalleryButton;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_theme_change, parent, false);
        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        themeRecycler = (RecyclerView) rootView.findViewById(R.id.fragment_theme_recycler);
        pickGalleryButton = (Button) rootView.findViewById(R.id.fragment_theme_pick_gallery);
        themeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        changeThemeAdapter = new ChangeThemeAdapter(getContext());
        changeThemeAdapter.setOnThemeSelectedListener(this);
        themeRecycler.setNestedScrollingEnabled(false);
        themeRecycler.setAdapter(changeThemeAdapter);
        pickGalleryButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_theme_pick_gallery:
                pickPhoto();
                break;

        }


    }

    private void pickPhoto() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Image"), AppConstants.GALLERY_PICK);
        }else {
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent,"Select Image"),AppConstants.GALLERY_PICK);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.GALLERY_PICK:
                    Uri imageUri = resultData.getData();
                    Log.d("Irshad", imageUri.toString());
                    Log.d("Irshad"," File "+getFilePath(imageUri));
                    AppSharedPreferences.putGalleryImagePreferences(getContext(),getFilePath(imageUri));
                    changeThemeAdapter.notifyItemChanged(9);

            }

        } else {
            Toast.makeText(getContext(), "Please pick again", Toast.LENGTH_SHORT).show();
        }
    }
    private String getFilePath(Uri uri)
    {
        String[] projectiom={MediaStore.Images.Media.DATA};

        String path="";
        Cursor cursor=getContext().getContentResolver().query(uri,projectiom,null,null,null);

        if (cursor!=null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(projectiom[0]));
        }
        return path;

    }
    @Override
    public void onThemeSelected(int position){

        int selected=AppSharedPreferences.getLockThemePreference(getContext());
        AppSharedPreferences.putLockThemePreference(getContext(), position);
        changeThemeAdapter.notifyItemChanged(selected);
        changeThemeAdapter.notifyItemChanged(position);
    }
}
