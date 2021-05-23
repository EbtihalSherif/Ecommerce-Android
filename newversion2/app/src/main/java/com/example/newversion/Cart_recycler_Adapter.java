package com.example.newversion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Cart_recycler_Adapter extends RecyclerView.Adapter<Cart_recycler_Adapter.CartViewHolder>
{
    private ArrayList<Cart_item_container> mExampleList;
    private Cart_recycler_Adapter.OnItemClickListener mListener;
     Cart_item_container item_now;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onIncDecClick(int position);

    }
    public void setOnItemClickListener(Cart_recycler_Adapter.OnItemClickListener listener) {
        mListener = listener;
    }
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxt_price;
        public TextView mTextViewName;
        public TextView mTextViewqty;
        public ImageButton mDeleteImage;
        public  Button mDecrease_qty;
        public  Button mIncrease_qty;
        Button checkout_order_adapter;
        TextView subTotal_adapter;
        int minteger=0;
        int Qty;

        public CartViewHolder(View itemView, final Cart_recycler_Adapter.OnItemClickListener listener) {
            super(itemView);
            mTxt_price = itemView.findViewById(R.id.textView3);
            mTextViewName = itemView.findViewById(R.id.textView);
            mTextViewqty = itemView.findViewById(R.id.integer_number_qty);
            mDeleteImage = itemView.findViewById(R.id.btn_delete_cart);
            mDecrease_qty=itemView.findViewById(R.id.decrease_qty_cart);
            mIncrease_qty=itemView.findViewById(R.id.increase_qty_cart);
            checkout_order_adapter=itemView.findViewById(R.id.btn_submit_order);
            subTotal_adapter =itemView.findViewById(R.id.txt_subtotal);
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

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mDecrease_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minteger=Integer.parseInt(mTextViewqty.getText().toString());
                 if(minteger>0){
                    minteger = minteger - 1;
                    TextView displayInteger = (TextView) itemView.findViewById(
                            R.id.integer_number_qty);
                    displayInteger.setText("" + minteger);
                     if (listener != null) {
                         int position = getAdapterPosition();
                         if (position != RecyclerView.NO_POSITION) {
                         MainActivity.user_cart.get(position).changeText1(String.valueOf(minteger));
                         listener.onIncDecClick(position);
                         }
                     }
                   // update_total();
                 }
                }
            });
         //   int finalQty = Qty;
            mIncrease_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // int maxQty=finalQty;
                    minteger=Integer.parseInt(mTextViewqty.getText().toString());

                    if(minteger<Qty){

                        minteger = minteger + 1;
                        TextView displayInteger = (TextView) itemView.findViewById(
                                R.id.integer_number_qty);
                        displayInteger.setText("" + minteger);
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                MainActivity.user_cart.get(position).changeText1(String.valueOf(minteger));
                                listener.onIncDecClick(position);

                            }
                        }
                    }
                }

            });
        }

    }

    public Cart_recycler_Adapter(ArrayList<Cart_item_container> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_container, parent, false);
        Cart_recycler_Adapter.CartViewHolder evh = new CartViewHolder(v, mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(Cart_recycler_Adapter.CartViewHolder holder, int position) {
        Cart_item_container currentItem = mExampleList.get(position);
        holder.mTxt_price.setText(currentItem.getmtxtprice());
        holder.mTextViewName.setText(currentItem.getTextName());
        holder.mTextViewqty.setText(currentItem.getTextqty());
        holder.Qty=currentItem.getMaxavaliable();
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}
