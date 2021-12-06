package com.example.grocerystoredemoapp.ui.Admin;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;

import java.util.ArrayList;

public class ProductListAdapter extends ArrayAdapter<Product> {


    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView brand;
        TextView price;
    }

    public ProductListAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getName();
        String brand = getItem(position).getBrand();
        Double price = getItem(position).getPrice();

        //Create the person object with the information
        Product product = new Product(name,brand,price);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvName = (TextView) convertView.findViewById(R.id.adapterName);
        TextView tvBirthday = (TextView) convertView.findViewById(R.id.adapterBrand);
        TextView  tvSex = (TextView) convertView.findViewById(R.id.adapterPrice);

        tvName.setText(name);
        tvBirthday.setText(brand);
        tvSex.setText("$" + price);

        return convertView;
    }
}



