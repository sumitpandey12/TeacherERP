package com.example.teachererp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teachererp.Model.ListItemAttendanceModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    LinearLayout layout1,layout2,layout3;
    TextInputEditText name,user,password;
    Button sbmt;
    private static final String url = "http://192.168.1.77/LoginRegister/login.php";
    TextView temp;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences("TeacherERP",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Init();
        temp = findViewById(R.id.temp);

        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });

    }

    private void InsertData() {
        name = findViewById(R.id.etxt_fullname);
        user = findViewById(R.id.etxt_username);
        password = findViewById(R.id.etxt_password);

        if (!(name.getText().length() >0)){
            Toast.makeText(this, "Fill name first", Toast.LENGTH_SHORT).show();
            return;
        }else if (!(user.getText().length()>0)){
            Toast.makeText(this, "Fill username First", Toast.LENGTH_SHORT).show();
            return;
        }else if(!(password.getText().length()>0)){
            Toast.makeText(this, "Fill Password First", Toast.LENGTH_SHORT).show();
            return;
        } else if ((password.getText().length())<6){
            Toast.makeText(this, "Password should be minimum 6 digit", Toast.LENGTH_SHORT).show();
            return;
        }

        final String Mname = name.getText().toString().trim();
        final String uname = user.getText().toString().trim();
        final String pwd = password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject verify = array.getJSONObject(0);
                    if (verify.getString("status").equals("Sign Up Success")){
                        editor.putString("teacher_id", String.valueOf(verify.getInt("id")));
                        editor.putString("username",uname);
                        editor.putString("name", Mname);
                        editor.commit();
                        startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                        finish();

                    }else if(response.equals("Username Already Taken Try Diffrent")){
                        user.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
                param.put("tablename","teachers");
                param.put("fullname",Mname);
                param.put("username",uname);
                param.put("password",pwd);
                param.put("type","signup");
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