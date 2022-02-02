package com.example.teachererp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView NewUser;
    TextInputEditText txtUsername,txtPassword;
    Button btnLogin;
    final String url = "http://192.168.1.77/LoginRegister/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NewUser = findViewById(R.id.txt_new_user);
        btnLogin = findViewById(R.id.btn_login);

        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,UserProfile.class));
            }
        });
    }

    private void Login() {

        txtUsername = findViewById(R.id.etxt_username);
        txtPassword = findViewById(R.id.etxt_password);


        final String username = txtUsername.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();

        if (username.isEmpty()){
            Toast.makeText(this, "Enter Username First", Toast.LENGTH_SHORT).show();
            return;
        }else if (password.isEmpty()){
            Toast.makeText(this, "Enter Password First", Toast.LENGTH_SHORT).show();
            return;
        }


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                if (response.equals("Login Success")){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("username",username);
                param.put("password",password);
                param.put("type","login");
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }
}