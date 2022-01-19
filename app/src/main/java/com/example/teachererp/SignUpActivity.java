package com.example.teachererp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class SignUpActivity extends AppCompatActivity {

    LinearLayout layout1,layout2,layout3;
    TextInputEditText name,user,password;
    Button sbmt;
    private static final String url = "http://10.0.2.2/LoginRegister/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Init();

        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });
    }

//    private void InsertData() {
//        name = findViewById(R.id.etxt_fullname);
//        user = findViewById(R.id.etxt_username);
//        password = findViewById(R.id.etxt_password);
//        final String Mname = name.getText().toString().trim();
//        final String uname = user.getText().toString().trim();
//        final String pwd = password.getText().toString().trim();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
//                name.setText("");
//                user.setText("");
//                password.setText("");
//                if(response.equals("Inserted Successfully")){
//                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
//            }
//        }){
//
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> param = new HashMap<String,String>();
//                param.put("t1",Mname);
//                param.put("t2",uname);
//                param.put("t3",pwd);
//                return param;
//            }
//        };
//
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(stringRequest);
//    }

    private void InsertData() {
        name = findViewById(R.id.etxt_fullname);
        user = findViewById(R.id.etxt_username);
        password = findViewById(R.id.etxt_password);
        final String Mname = name.getText().toString().trim();
        final String uname = user.getText().toString().trim();
        final String email = "sumitpandeyjnv@gmail.com";
        final String pwd = password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                name.setText("");
                user.setText("");
                password.setText("");
                if(response.equals("Sign Up Success")){
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
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
                param.put("tablename","Login");
                param.put("fullname",Mname);
                param.put("username",uname);
                param.put("password",pwd);
                param.put("email",email);
                return param;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    private void Init() {
        layout1 = findViewById(R.id.layout_1);
        layout2 = findViewById(R.id.layout_2);
        layout3 = findViewById(R.id.layout_3);
        
        sbmt = findViewById(R.id.btn_sbmt);
    }
}