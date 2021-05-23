package com.example.newversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
         String address = null;
        Bundle b =getIntent().getExtras();
        if(b != null){
            address=b.getString("address");
          //  currrent_user_ID=b.getString("userid");
        }

        EcommerceDBHelper database = new EcommerceDBHelper(getApplicationContext());
        TextView total=(TextView)findViewById(R.id.txt_order_total_check);
        Button final_order_btn = (Button) findViewById(R.id.btn_order_databse);
        EditText ord_Address = (EditText) findViewById(R.id.txt_orderAddress);
        EditText ord_date = (EditText) findViewById(R.id.txt_order_date);
        EditText ord_Mail = (EditText) findViewById(R.id.txt_customerMail);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        // String formattedDate = "30-12-2020";
        total.setText("order Total :  "+ Cart_Fragement.subtotalOrder);
        ord_date.setText(formattedDate);
           ord_Address.setText(address);
        Cart_Fragement.subtotalOrder=0;
        final_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.add_Order(formattedDate, MainActivity.currrent_user_ID, ord_Address.getText().toString());
                String order_id = database.get_order_ID();
                if (!MainActivity.user_cart.isEmpty()) {

                    for (Cart_item_container cartITEM : MainActivity.user_cart) {

                        String price = cartITEM.getmtxtprice();
                        String quantity = cartITEM.getTextqty();

                        String Name = cartITEM.getTextName();
                        int OldQty = cartITEM.getMaxavaliable();
                        int qty_int = Integer.parseInt(quantity);
                        int newQty = OldQty - qty_int;
                        database.update_prod_qty(Name, String.valueOf(newQty));

                        String product_id = database.searchProducts_specific(Name);
                        database.add_OrderDetails(order_id, product_id, quantity);
                    }
                    MainActivity.user_cart.clear();

                    javaMailAPI javaMailAPI = new javaMailAPI(getApplicationContext(), ord_Mail.getText().toString(), "order submitted succesfully", "your online order with number : " + order_id + " is submitted successfully");
                    javaMailAPI.execute();
                    Toast.makeText(getApplicationContext(), "submitted successfully", Toast.LENGTH_SHORT).show();


                    finish();

                }
            }
        });
    }
}

