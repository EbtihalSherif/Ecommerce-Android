package com.example.newversion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class signinactivity extends AppCompatActivity {
    EcommerceDBHelper database;
    EditText _BirthDate;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        database=new EcommerceDBHelper(signinactivity.this);
        Button _signIN = (Button) findViewById(R.id.btn_signup);
        EditText _userName = (EditText) findViewById(R.id.input_username);
        EditText _passwordText = (EditText) findViewById(R.id.input_password);
         _BirthDate = (EditText) findViewById(R.id.editTextDate);
        EditText _job = (EditText) findViewById(R.id.input_password);
        Spinner _gender=(Spinner)findViewById(R.id.spinner);
        EditText _name = (EditText) findViewById(R.id.input_name);
        TextView _loginLink = (TextView) findViewById(R.id.link_login);

        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                _BirthDate.setText(sdf.format(myCalendar.getTime()));
             //   updateLabel();

            }

        };

        _BirthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(signinactivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        _signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            database.createNewCustomer(_name.getText().toString(),_userName.getText().toString(),_passwordText.getText().toString(),
                    _gender.getSelectedItem().toString(),_BirthDate.getText().toString(),_job.getText().toString());
                  Toast.makeText(signinactivity.this, "created account", Toast.LENGTH_LONG).show();
            }
        });
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_activity = new Intent(signinactivity.this, loginActivity.class);
                signinactivity.this.startActivity(login_activity);

            }
        });
    }


}