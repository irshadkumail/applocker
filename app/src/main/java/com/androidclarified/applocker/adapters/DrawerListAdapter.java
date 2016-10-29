package com.androidclarified.applocker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidclarified.applocker.R;

/**
 * Created by My Pc on 10/29/2016.
 */

public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] listItemArray;

    public DrawerListAdapter(Context context,String[] listItemArray) {
        this.context = context;
        this.listItemArray=listItemArray;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItemArray.length;
    }

    @Override
    public Object getItem(int i) {
        return listItemArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View itemView=layoutInflater.inflate(R.layout.drawer_list_item_view,viewGroup,false);



        return itemView;
    }
}
