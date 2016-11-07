package com.androidclarified.applocker.adapters;

import android.content.Context;
import android.os.ParcelUuid;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidclarified.applocker.R;

/**
 * Created by My Pc on 11/6/2016.
 */

public class ChangeThemeAdapter extends RecyclerView.Adapter<ChangeThemeAdapter.ChangeThemeViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    public ChangeThemeAdapter(Context context)
    {
        this.context=context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ChangeThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView=layoutInflater.inflate(R.layout.change_theme_item,parent,false);
        ChangeThemeViewHolder changeThemeViewHolder=new ChangeThemeViewHolder(rootView);


        return changeThemeViewHolder;
    }

    @Override
    public void onBindViewHolder(ChangeThemeViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class ChangeThemeViewHolder extends RecyclerView.ViewHolder
    {
        ImageView themeImage;


        ChangeThemeViewHolder(View view)
        {
            super(view);
            themeImage= (ImageView) view.findViewById(R.id.change_theme_item_image);

        }

    }


}
