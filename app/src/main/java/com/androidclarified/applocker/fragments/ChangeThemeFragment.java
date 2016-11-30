package com.androidclarified.applocker.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
    private static final int STORAGE_REQUEST_CODE=1001;



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
        pickGalleryButton.setVisibility(View.GONE);
        pickGalleryButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_theme_pick_gallery:

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

    public void onRequestPermissionsResult(int requestCode,String[] permission,int[] grantResults)
    {
        switch (requestCode)
        {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    pickPhoto();
                else
                    Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
                break;

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
                    changeThemeAdapter.notifyItemChanged(AppConstants.GALLERY_THEME);

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

    @Override
    public void onAddGallery(int position) {
        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)&& ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
        else
            pickPhoto();
    }
}
