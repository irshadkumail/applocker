package com.androidclarified.applocker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.model.AppBean;
import com.androidclarified.applocker.utils.AppSharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 10/19/2016.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppItemViewHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<AppBean> appBeanList;


    public AppListAdapter(Context context, ArrayList<AppBean> appBeanList) {
        this.context = context;
        this.appBeanList=appBeanList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        holder.imageIcon.setImageDrawable(appBeanList.get(position).getAppIcon());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppSharedPreferences.putAppSharedPreferences(context,appBeanList.get(position).getPackName(),isChecked);
            }
        });



    }

    @Override
    public int getItemCount() {
        return appBeanList.size();
    }


    class AppItemViewHolder extends RecyclerView.ViewHolder {

        TextView appLabel;
        ImageView imageIcon;
        CheckBox checkBox;


        AppItemViewHolder(View view) {
            super(view);

            appLabel = (TextView) view.findViewById(R.id.app_item_vh_label);
            imageIcon= (ImageView) view.findViewById(R.id.app_item_vh_icon);
            checkBox= (CheckBox) view.findViewById(R.id.app_item_vh_checkbox);

        }

    }


}
