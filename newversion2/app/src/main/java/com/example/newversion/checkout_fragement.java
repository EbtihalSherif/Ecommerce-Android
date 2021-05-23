package com.example.newversion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class checkout_fragement extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.checkout_fragment, container, false);
       EcommerceDBHelper database=new EcommerceDBHelper(getActivity());

        Button final_order_btn=(Button)view.findViewById(R.id.btn_order_databse);
        EditText ord_Address=(EditText)view.findViewById(R.id.txt_orderAddress);
        EditText ord_date=(EditText)view.findViewById(R.id.txt_order_date);
        EditText ord_Mail=(EditText)view.findViewById(R.id.txt_customerMail);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
       String formattedDate = df.format(c);
       // String formattedDate = "30-12-2020";

       ord_date.setText(formattedDate);

        final_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.add_Order(formattedDate,MainActivity.currrent_user_ID,ord_Address.getText().toString());
                String order_id=database.get_order_ID();
                if (!MainActivity.user_cart.isEmpty()) {

                    for (Cart_item_container cartITEM :MainActivity.user_cart) {

                        String price = cartITEM.getmtxtprice();
                        String quantity = cartITEM.getTextqty();

                        String Name=cartITEM.getTextName();
                       int OldQty=cartITEM.getMaxavaliable();
                       int qty_int=Integer.parseInt(quantity) ;
                        int newQty=OldQty-qty_int;
                       database.update_prod_qty(Name,String.valueOf(newQty));

                        String product_id=database.searchProducts_specific(Name);
                        database.add_OrderDetails(order_id,product_id,quantity);


                    }
                    MainActivity.user_cart.clear();
                    javaMailAPI javaMailAPI = new javaMailAPI(getActivity(), ord_Mail.getText().toString(), "order submitted succesfully", "your online order with number : "+order_id+" is submitted successfully");
                    javaMailAPI.execute();
                    Toast.makeText(getActivity(), "submitted successfully", Toast.LENGTH_SHORT).show();
                  //  getActivity().onBackPressed();
                    getFragmentManager().popBackStack();

                }

            }
        });
        return view;
        }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
