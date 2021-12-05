package com.example.grocerystoredemoapp.ui.Admin;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;

import java.util.ArrayList;
import java.util.List;
public class viewOrderAdapter extends ArrayAdapter<Product> {


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

    public viewOrderAdapter(Context context, int resource, ArrayList<Product> objects) {
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
        TextView tvName = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvBirthday = (TextView) convertView.findViewById(R.id.textView3);
        TextView  tvSex = (TextView) convertView.findViewById(R.id.textView1);


        tvName.setText(name);
        tvBirthday.setText(brand);
        tvSex.setText("Quantity: " + price.intValue());


        return convertView;
    }
}



