package com.example.newversion;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Product_Fragement extends Fragment {
    private RecyclerView mRecyclerView;
    private Products_recycler_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<product_item_container> product_list;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product, container, false);
        String category_ID=null;
        Bundle b = this.getArguments();
        if(b != null){


             category_ID =b.getString("cat_id");
        }
        RecyclerView product_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        product_list = new ArrayList<>();

        EcommerceDBHelper database = new EcommerceDBHelper(getActivity());
        Cursor c = database.FetchAllProductsincategory(category_ID);
        while (!c.isAfterLast()) {
            product_list.add(new product_item_container(c.getString(2), c.getString(1), c.getString(3)));
            c.moveToNext();
        }
       // product_list.add(new product_item_container("60","testtt","10"));

        mRecyclerView = (RecyclerView)view. findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new Products_recycler_Adapter(product_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new Products_recycler_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
              //  Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
              /*  Intent products_intent=new Intent(getActivity(),Products.class);
                products_intent.putExtra("category_ID",String.valueOf(position+1));
                getActivity().startActivity(products_intent);*/
                String Prod_Name=  product_list.get(position).getTextName();
                String Prod_price=  product_list.get(position).getmtxtprice();
                String Prod_Qty=  product_list.get(position).getTextqty();

                Bundle bundle_data=new Bundle();
                bundle_data.putString("Name",Prod_Name );
                bundle_data.putString("Price",Prod_price );
                bundle_data.putString("Max_qty",Prod_Qty );

                Product_details_Fragement nextFrag= new Product_details_Fragement();
                nextFrag.setArguments(bundle_data);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });
        return view;



    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
