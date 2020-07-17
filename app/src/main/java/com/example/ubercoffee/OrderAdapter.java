package com.example.ubercoffee;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter  extends BaseAdapter {

    private List<Order> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    private ImageButton bMinus;
    private ImageButton bPlus;
    private ImageButton bDelete;

    public OrderAdapter(Context mContext,  List<Order> listData) {
        this.context = mContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_order, null);
            holder = new ViewHolder();
            holder.nameDrinkablesView = (TextView) convertView.findViewById(R.id.textName);
            holder.sizeDrinkablesView = (TextView) convertView.findViewById(R.id.textSize);
            holder.priceDrinkablesView = (TextView) convertView.findViewById(R.id.textPrice);
            holder.countDrinkablesView = (TextView) convertView.findViewById(R.id.textCount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Order order = this.listData.get(position);

        bMinus = (ImageButton) convertView.findViewById(R.id.minus);
        bPlus = (ImageButton) convertView.findViewById(R.id.plus);
        bDelete = (ImageButton) convertView.findViewById(R.id.delete);

        bMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = order.getCountDrinkables() - 1;
                if (count < 0)
                    count = 0;
                order.setCountDrinkables(count);
            }
        });

        bPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = order.getCountDrinkables() + 1;
                order.setCountDrinkables(count);
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.nameDrinkablesView.setText(order.getNameDrinkables());
        holder.sizeDrinkablesView.setText(String.valueOf(order.getSizeDrinkables()) + "L");
        holder.priceDrinkablesView.setText(String.valueOf(order.getPriceDrinkables()) + "₽");
        holder.countDrinkablesView.setText(String.valueOf(order.getCountDrinkables()));


        return convertView;
    }

    static class ViewHolder {
        TextView nameDrinkablesView;
        TextView sizeDrinkablesView;
        TextView priceDrinkablesView;
        TextView countDrinkablesView;
    }

}