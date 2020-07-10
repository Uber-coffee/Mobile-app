package com.example.ubercoffee;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter  extends BaseAdapter {

    private List<Drinkables> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public Integer[] mThumbIds = { R.drawable.cappuccino, R.drawable.latte, R.drawable.macchiato,
            R.drawable.espresso, R.drawable.americano, R.drawable.glace,  R.drawable.raf,
            R.drawable.frappe};

    public MenuAdapter(Context mContext,  List<Drinkables> listData) {
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
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.nameDrinkablesView = (TextView) convertView.findViewById(R.id.nameDrinkables);
            holder.sizeDrinkablesView = (TextView) convertView.findViewById(R.id.sizeDrinkables);
            holder.imageDrinkablesView = (ImageView) convertView.findViewById(R.id.imageDrinkables);
            holder.priceDrinkablesView = (TextView) convertView.findViewById(R.id.priceDrinkables);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Drinkables drinkables = this.listData.get(position);
        holder.nameDrinkablesView.setText(drinkables.getNameDrinkables());
        int countSize = drinkables.getCountSize();
        ArrayList<Double> sizeAll = drinkables.getSizeDrinkables();
        holder.sizeDrinkablesView.setText("Size:");
        for (int i = 0; i < countSize; i++){
            double size = sizeAll.get(i);
            holder.sizeDrinkablesView.append("  " + size);
        }
        //holder.sizeDrinkablesView.setText("Size: " +  drinkables.getSizeDrinkables());
        holder.priceDrinkablesView.setText("from " + drinkables.getPriceDrinkables() + "₽");

       // int imageId = this.getMipmapResIdByName(drinkables.getNameDrinkables());
        //holder.imageDrinkablesView.setImageResource(imageId);
        int pos = -1;
        if (drinkables.getNameDrinkables().equals("Cappuccino"))
            pos = 0;
        if (drinkables.getNameDrinkables().equals("Latte"))
            pos = 1;
        if (drinkables.getNameDrinkables().equals("Macchiato"))
            pos = 2;
        if (drinkables.getNameDrinkables().equals("Espresso"))
            pos = 3;
        if (drinkables.getNameDrinkables().equals("Americano"))
            pos = 4;
        if (drinkables.getNameDrinkables().equals("Glace"))
            pos = 5;
        if (drinkables.getNameDrinkables().equals("Raf"))
            pos = 6;
        if (drinkables.getNameDrinkables().equals("Frappe"))
            pos = 7;
        if (pos != -1)
            holder.imageDrinkablesView.setImageResource(mThumbIds[pos]);

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {

        int resId = 0;

        if (resName.equals("cappuccino"))
            resId = mThumbIds[0];

        if (resName.equals("latte"))
            resId = mThumbIds[1];

        if (resName.equals("macchiato"))
            resId = mThumbIds[2];

        if (resName.equals("expresso"))
            resId = mThumbIds[3];

        if (resName.equals("americano"))
            resId = mThumbIds[4];

        //String pkgName = context.getPackageName();

        // Return 0 if not found.
        //int resId = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        //Log.i("MenuAdapter", "Res Name: "+ resName+"==> Res ID = "+ resId);
        return resId;
    }

    static class ViewHolder {
        TextView nameDrinkablesView;
        TextView sizeDrinkablesView;
        ImageView imageDrinkablesView;
        TextView priceDrinkablesView;
    }

}