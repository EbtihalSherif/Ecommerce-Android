package com.example.newversion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Cart_Fragement extends Fragment {
    private RecyclerView mRecyclerView;
    private Cart_recycler_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Cart_item_container> cart_list;
    public static int subtotalOrder = 0;
    Button checkout_order;
    TextView subTotal;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        checkout_order = (Button) view.findViewById(R.id.btn_submit_order);
        subTotal = (TextView) view.findViewById(R.id.txt_subtotal);


        if (!MainActivity.user_cart.isEmpty()) {
            buildRecyclerView(view);
            subTotal.setText("0");

            update_total();
        } else {
            subTotal.setText(String.valueOf(subtotalOrder));
        }

        checkout_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bundle bundle_data=new Bundle();
                // bundle_data.putString("Name",Prod_Name );
                // bundle_data.putString("Price",Prod_price );
                // bundle_data.putString("Max_qty",Prod_Qty );

               /*  checkout_fragement nextFrag= new checkout_fragement();
                 nextFrag.setArguments(bundle_data);
                 getActivity().getSupportFragmentManager().beginTransaction()
                         .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                         .addToBackStack(null)
                         .commit();*/
                if (subtotalOrder != 0) {
                    Intent gotoAddress = new Intent(getActivity(), MapsActivity.class);
                    Bundle b = new Bundle();

                    //  String address = addressText.getText().toString();
                    //   b.putString("address",address);
                    //  b.putString("order total", suttotalO);
                    //   gotoAddress.putExtras(b);
                    startActivity(gotoAddress);
                      // subTotal.setText("0");

                   // getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Your cart is Empty", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    public void update_total() {
        subtotalOrder = 0;

        for (Cart_item_container cartITEM : MainActivity.user_cart) {

            if (!MainActivity.user_cart.isEmpty()) {
                String price = cartITEM.getmtxtprice();
                int priceint = Integer.parseInt(price);
                String quantity = cartITEM.getTextqty();
                int qtyint = Integer.parseInt(quantity);
                int item_total = priceint * qtyint;
                subtotalOrder += item_total;
            }
        }
        subTotal.setText(String.valueOf(subtotalOrder));
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



    public void removeItem(int position) {
        MainActivity.user_cart.remove(position);
        mAdapter.notifyItemRemoved(position);
        update_total();
    }

    public void changeItem(int position, String text) {
        String Prod_Name = MainActivity.user_cart.get(position).getTextName();
        mAdapter.notifyItemChanged(position);
    }

    public void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recycler_cart);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new Cart_recycler_Adapter(MainActivity.user_cart);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new Cart_recycler_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //  changeItem(position, "Clicked");
                mAdapter.notifyItemChanged(position);
                update_total();


            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                update_total();
            }

            @Override
            public void onIncDecClick(int position) {
                update_total();
            }
        });
    }

}
