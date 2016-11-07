package com.androidclarified.applocker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.fragments.DrawerFragment;
import com.androidclarified.applocker.listeners.OnDrawerItemClick;

/**
 * Created by My Pc on 10/29/2016.
 */

public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] listItemArray;
    private OnDrawerItemClick onDrawerItemClick;

    public DrawerListAdapter(Context context, String[] listItemArray, DrawerFragment drawerFragment) {
        this.context = context;
        this.onDrawerItemClick= (OnDrawerItemClick) drawerFragment;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View itemView=layoutInflater.inflate(R.layout.drawer_list_item_view,viewGroup,false);
        TextView textView= (TextView) itemView.findViewById(R.id.drawer_list_item_text);
        textView.setText(listItemArray[i]);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDrawerItemClick.onItemClicked(i);
            }
        });

        return itemView;
    }
}
