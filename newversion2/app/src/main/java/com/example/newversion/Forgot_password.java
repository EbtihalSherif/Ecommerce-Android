package com.example.newversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;

public class Forgot_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EcommerceDBHelper database=new EcommerceDBHelper(getApplicationContext());
        TextView userName=(TextView)findViewById(R.id.txt_forgot_username);
        TextView realName=(TextView)findViewById(R.id.txt_forgot_Name);
        TextView userEmail=(TextView)findViewById(R.id.txt_recovery_mail);
        Button check=(Button)findViewById(R.id.btn_check);
       //  foundPassword=null;
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  foundPassword =database.find_password(userName.getText().toString(),realName.getText().toString());

                if(foundPassword!=null)
                {
                    Toast.makeText(Forgot_password.this,"Password is : "+ foundPassword , Toast.LENGTH_LONG).show();
                    javaMailAPI javaMailAPI = new javaMailAPI(getApplicationContext(), userEmail.getText().toString(), "password recovery", "your password is : "+foundPassword);

                    javaMailAPI.execute();
                   /* Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("message/rfc822");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, userEmail.getText().toString());
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ecommerce password recovery");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "your password is : "+foundPassword);
                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        finish();
                        Log.i("Finished sending email...", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Forgot_password.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        });


    }
}