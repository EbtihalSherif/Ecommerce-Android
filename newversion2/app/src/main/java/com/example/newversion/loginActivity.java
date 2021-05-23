package com.example.newversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText _username = (EditText) findViewById(R.id.input_username);
        EditText _passwordText = (EditText) findViewById(R.id.input_password);
        Button _loginButton = (Button) findViewById(R.id.btn_login);
        TextView _loginLink = (TextView) findViewById(R.id.link_login);
        CheckBox _remeberme = (CheckBox) findViewById(R.id.checkBox);
        TextView _forgot_pass = (TextView) findViewById(R.id.forgot_pass);
        final EcommerceDBHelper database = new EcommerceDBHelper(getApplicationContext());
//database.delete_All();
       database.add_category("Fashion");
        database.add_category("Food and Beverage");
        database.add_category("Home");
        database.add_category("Makeup");

        database.add_product("dress",60,10,1);
        database.add_product("blouse",45,15,1);
        database.add_product("milk",20,10,2);
        database.add_product("mozarilla",30,20,2);
        database.add_product("lipstick",60,10,4);
        database.add_product("blusher",40,10,4);
        database.add_product("coffee table",60,10,3);
        database.add_product("night stand",35,10,3);



        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {

            _username.setText(loginPreferences.getString("username", ""));
            _passwordText.setText(loginPreferences.getString("password", ""));
            _remeberme.setChecked(true);
            Intent gotomain = new Intent(loginActivity.this, MainActivity.class);
            Bundle b = new Bundle();
            String name = _username.getText().toString();
            b.putString("userid", loginPreferences.getString("userid", ""));
            b.putString("username", name);
            gotomain.putExtras(b);
            loginActivity.this.startActivity(gotomain);
        }


        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosignin = new Intent(loginActivity.this, signinactivity.class);
                loginActivity.this.startActivity(gotosignin);


            }
        });
        _forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget_activity = new Intent(loginActivity.this, Forgot_password.class);
                loginActivity.this.startActivity(forget_activity);

            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor validateFound = database.getcustomerData(_username.getText().toString(), _passwordText.getText().toString());
                if (!validateFound.isAfterLast()) {
                    String ID=validateFound.getString(0);
                    validateFound.moveToNext();
                    if (_remeberme.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("userid", ID);
                        loginPrefsEditor.putString("username", _username.getText().toString());
                        loginPrefsEditor.putString("password", _passwordText.getText().toString());
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    Intent gotomain = new Intent(loginActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    String name = _username.getText().toString();
                    b.putString("userid", ID);
                    b.putString("username", name);
                    gotomain.putExtras(b);
                    loginActivity.this.startActivity(gotomain);

                } else {
                    Toast.makeText(loginActivity.this, "account not found", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}