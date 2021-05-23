package com.example.newversion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Products_recycler_Adapter extends RecyclerView.Adapter<Products_recycler_Adapter.ProductsViewHolder>
{
    private ArrayList<product_item_container> mExampleList;
    private Products_recycler_Adapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(Products_recycler_Adapter.OnItemClickListener listener) {
        mListener = listener;
    }
    public static class ProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxt_price;
        public TextView mTextViewName;
        public TextView mTextViewqty;
        public ProductsViewHolder(View itemView, final Products_recycler_Adapter.OnItemClickListener listener) {
            super(itemView);
            mTxt_price = itemView.findViewById(R.id.textView3);
            mTextViewName = itemView.findViewById(R.id.textView);
            mTextViewqty = itemView.findViewById(R.id.textView4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public Products_recycler_Adapter(ArrayList<product_item_container> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_container, parent, false);
        ProductsViewHolder evh = new ProductsViewHolder(v,mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(Products_recycler_Adapter.ProductsViewHolder holder, int position) {
        product_item_container currentItem = mExampleList.get(position);
        holder.mTxt_price.setText(currentItem.getmtxtprice());
        holder.mTextViewName.setText(currentItem.getTextName());
        holder.mTextViewqty.setText(currentItem.getTextqty());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
