package com.example.kangdong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;


    public SearchAdapter(List<String> list, Context context){
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.listview,null);

            viewHolder = new ViewHolder();
            viewHolder.lable = (TextView) convertView.findViewById(R.id.lable);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.lable.setText(list.get(position));
        return convertView;
    }

    class ViewHolder{
        public TextView lable;
    }
}
