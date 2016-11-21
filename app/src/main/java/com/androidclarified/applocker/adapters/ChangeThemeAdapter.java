package com.androidclarified.applocker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.ParcelUuid;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.listeners.OnThemeSelectedListener;
import com.androidclarified.applocker.listeners.OverlayScreenListener;
import com.androidclarified.applocker.utils.AppConstants;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.androidclarified.applocker.utils.AppUtils;
import com.androidclarified.applocker.widgets.LockOverlayView;
import com.squareup.picasso.Picasso;

import java.io.File;

import static com.androidclarified.applocker.utils.AppConstants.BLUE_GREY_THEME;
import static com.androidclarified.applocker.utils.AppConstants.CHATIT_BLUE_THEME;
import static com.androidclarified.applocker.utils.AppConstants.CYAN_THEME;
import static com.androidclarified.applocker.utils.AppConstants.DEEP_ORANGE_THEME;
import static com.androidclarified.applocker.utils.AppConstants.HOME_BLACK_THEME;
import static com.androidclarified.applocker.utils.AppConstants.HOME_BLUE_THEME;
import static com.androidclarified.applocker.utils.AppConstants.JET_BLACK_THEME;
import static com.androidclarified.applocker.utils.AppConstants.TEAL_THEME;

/**
 * Created by My Pc on 11/6/2016.
 */

public class ChangeThemeAdapter extends RecyclerView.Adapter<ChangeThemeAdapter.ChangeThemeViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private OnThemeSelectedListener onThemeSelectedListener;
    //private int selectedTheme = 0;

    public ChangeThemeAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  selectedTheme = AppSharedPreferences.getLockThemePreference(context);
    }

    public void setOnThemeSelectedListener(OnThemeSelectedListener onThemeSelectedListener)
    {
        this.onThemeSelectedListener=onThemeSelectedListener;
    }

    @Override
    public ChangeThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = layoutInflater.inflate(R.layout.change_theme_item, parent, false);
        ChangeThemeViewHolder changeThemeViewHolder = new ChangeThemeViewHolder(rootView);

        return changeThemeViewHolder;
    }

    @Override
    public void onBindViewHolder(ChangeThemeViewHolder holder, final int position) {

        if (position == AppConstants.GALLERY_THEME) {
            Log.d("Irshad","Gallery "+position);
            holder.themeColourFrame.setVisibility(View.GONE);
            holder.themeImage.setVisibility(View.VISIBLE);
            Picasso.with(context).load("file://"+AppSharedPreferences.getGalleryImagePreference(context)).centerCrop().fit().into(holder.themeImage);
        } else {
            Log.d("Irshad","Normal "+position);
            holder.themeImage.setVisibility(View.GONE);
            holder.themeColourFrame.setVisibility(View.VISIBLE);
            holder.themeColourFrame.setBackgroundColor(AppUtils.getColor(context, position));
        }
        setImageHolderColour(holder, position);
        holder.themeImageLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                onThemeSelectedListener.onThemeSelected(position);
                /*AppSharedPreferences.putLockThemePreference(context, position);
                holder.themeImageLayout.setBackgroundColor(context.getResources().getColor(R.color.amber));
                notifyItemChanged(selectedTheme);
                selectedTheme = position;
                Log.d("Irshad", "Selected Theme" + selectedTheme);*/
                return false;
            }
        });

    }


    private void setImageHolderColour(ChangeThemeViewHolder holder, int position) {
        if (AppSharedPreferences.getLockThemePreference(context) == position) {
            holder.themeImageLayout.setBackgroundColor(context.getResources().getColor(R.color.amber));
        } else {
            holder.themeImageLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class ChangeThemeViewHolder extends RecyclerView.ViewHolder {
        ImageView themeImage;
        FrameLayout themeColourFrame;
        RelativeLayout themeImageLayout;


        ChangeThemeViewHolder(View view) {
            super(view);
            themeImage = (ImageView) view.findViewById(R.id.change_theme_item_image);
            themeColourFrame= (FrameLayout) view.findViewById(R.id.change_theme_item_frame);
            themeImageLayout = (RelativeLayout) view.findViewById(R.id.change_theme_item_rl);


        }

    }


}
