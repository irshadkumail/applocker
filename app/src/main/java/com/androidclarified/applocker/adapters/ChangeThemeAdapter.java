package com.androidclarified.applocker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.ParcelUuid;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.utils.AppConstants;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.androidclarified.applocker.utils.AppUtils;
import com.androidclarified.applocker.widgets.LockOverlayView;

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
    private int selectedTheme=0;
    public ChangeThemeAdapter(Context context)
    {
        this.context=context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedTheme=AppSharedPreferences.getLockThemePreference(context);
    }


    @Override
    public ChangeThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView=layoutInflater.inflate(R.layout.change_theme_item,parent,false);
        ChangeThemeViewHolder changeThemeViewHolder=new ChangeThemeViewHolder(rootView);


        return changeThemeViewHolder;
    }

    @Override
    public void onBindViewHolder(final ChangeThemeViewHolder holder, final int position) {

        holder.themeImage.setBackgroundColor(AppUtils.getColor(context,position));
        setImageHolderColour(holder,position);
        holder.themeImageLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AppSharedPreferences.putLockThemePreference(context,position);
                holder.themeImageLayout.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
                notifyItemChanged(selectedTheme);
                selectedTheme=position;
                Log.d("Irshad","Selected Theme"+selectedTheme);
                return false;
            }
        });

    }
    private void setImageHolderColour(ChangeThemeViewHolder holder,int position)
    {
        if(AppSharedPreferences.getLockThemePreference(context)==position) {
            holder.themeImageLayout.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
        }else {
            holder.themeImageLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }
    }


    @Override
    public int getItemCount() {
        return 8;
    }

    public class ChangeThemeViewHolder extends RecyclerView.ViewHolder
    {
        ImageView themeImage;
        RelativeLayout themeImageLayout;


        ChangeThemeViewHolder(View view)
        {
            super(view);
            themeImage= (ImageView) view.findViewById(R.id.change_theme_item_image);
            themeImageLayout= (RelativeLayout) view.findViewById(R.id.change_theme_item_rl);

        }

    }


}
