package com.example.newversion;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import static com.example.newversion.R.drawable.blouse;
import static com.example.newversion.R.drawable.blusher;
import static com.example.newversion.R.drawable.milk;

public class Product_details_Fragement extends Fragment {

    int minteger = 0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_details, container, false);
        String prod_Name=null,prod_Price=null;
        String Qty=null;
        Bundle b = this.getArguments();
        if(b != null){


            prod_Name =b.getString("Name");
            prod_Price =b.getString("Price");
            Qty=b.getString("Max_qty");
        }

      //  Button decrease_btn = (Button) view.findViewById(R.id.decrease);
      //  Button increase_btn = (Button) view.findViewById(R.id.increase);
        TextView Name_txt = (TextView) view.findViewById(R.id.txt_Name);
        TextView price_txt = (TextView) view.findViewById(R.id.txt_Price_det);
        TextView Quantity_num=(TextView)view.findViewById(R.id.integer_number);
        Button Add_to_cart=(Button)view.findViewById(R.id.btn_add_to_cart);
        ImageView pro_image=(ImageView)view.findViewById(R.id.imageView3) ;
        Name_txt.setText(prod_Name);
        price_txt.setText(prod_Price);
        prod_Name=prod_Name.replaceAll("\\s+","");

   //     pro_image.setBackgroundResource(Integer.parseInt(prod_Name));
     //   pro_image.setBackgroundResource(milk);
       // pro_image.setImageResource(milk);
       //  pro_image.setImageURI(Uri.parse("drawable\\"+prod_Name+".jpg"));
        Resources resources = getActivity().getResources();

        int resourceId =resources.getIdentifier(prod_Name, "drawable",
                getActivity().getPackageName());//initialize res and context in adapter's contructor
        pro_image.setImageResource(resourceId);

        String finalQty = Qty;


        Add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  int total=Integer.parseInt(Quantity_num.getText().toString())*Integer.parseInt(price_txt.getText().toString());


                 MainActivity.user_cart.add(new Cart_item_container(price_txt.getText().toString(),Name_txt.getText().toString(),"1",Integer.parseInt(finalQty)));
                Toast.makeText(getActivity().getApplicationContext(), "Added to cart", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



}
