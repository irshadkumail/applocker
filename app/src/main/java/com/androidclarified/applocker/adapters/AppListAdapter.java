package com.androidclarified.applocker.adapters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.activities.MainActivity;
import com.androidclarified.applocker.listeners.OnAppCheckedListener;
import com.androidclarified.applocker.model.AppBean;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 10/19/2016.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppItemViewHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<AppBean> appBeanList;
    private OnAppCheckedListener onAppCheckedListener;


    public AppListAdapter(Context context, List<AppBean> appBeanList) {
        this.context = context;
        this.appBeanList=appBeanList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onAppCheckedListener= (OnAppCheckedListener) context;

    }


    @Override
     public AppItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = layoutInflater.inflate(R.layout.app_item_viewholder, parent, false);
        AppItemViewHolder appItemViewHolder = new AppItemViewHolder(container);

        return appItemViewHolder;
    }

    @Override
    public void onBindViewHolder(AppItemViewHolder holder, final int position) {

        holder.appLabel.setText(appBeanList.get(position).getAppLabel());
        holder.imageIcon.setImageDrawable(getImageIcon(appBeanList.get(position).getPackName()));
        holder.checkBox.setChecked(AppSharedPreferences.getAppSharedPreference(context,appBeanList.get(position).getPackName()));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean value=appBeanList.get(position).isChecked();
                if(value)
                    appBeanList.get(position).setChecked(false);
                else
                    appBeanList.get(position).setChecked(true);

                Log.d("Irshad","Check Changed "+appBeanList.get(position).isChecked());
                AppSharedPreferences.putAppSharedPreferences(context,appBeanList.get(position).getPackName(),appBeanList.get(position).isChecked());
                onAppCheckedListener.onAppChecked(appBeanList.get(position).getPackName());

            }
        });

    }

    @Override
    public int getItemCount() {
        return appBeanList.size();
    }

    private Drawable getImageIcon(String packName)
    {
        try {
            return context.getPackageManager().getApplicationIcon(packName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return context.getResources().getDrawable(R.drawable.lock_icon);
    }


    class AppItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView appLabel;
        ImageView imageIcon;
        CheckBox checkBox;
        View container;


        AppItemViewHolder(View view) {
            super(view);

            container=view;
            appLabel = (TextView) view.findViewById(R.id.app_item_vh_label);
            imageIcon= (ImageView) view.findViewById(R.id.app_item_vh_icon);
            checkBox= (CheckBox) view.findViewById(R.id.app_item_vh_checkbox);
            view.setOnClickListener(this);
           


        }

        @Override
        public void onClick(View v) {

        }
    }


}
