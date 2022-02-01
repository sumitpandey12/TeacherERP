package com.example.teachererp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teachererp.Adapter.ListYourClassesAdapter;
import com.example.teachererp.Model.ListYourClassesModel;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    EditText subject,designation,salary;
    Button insert;
    private static final String url = "http://192.168.1.77/LoginRegister/addTeachers.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ListYourClassesModel[] list = new ListYourClassesModel[]{
                new ListYourClassesModel("B.Tech","CSE","3","DSA"),
                new ListYourClassesModel("B.Tech","CSE","3","DSA"),
                new ListYourClassesModel("B.Tech","CSE","3","DSA")
        };

        RecyclerView recyclerView = findViewById(R.id.recy_list_classes);
        ListYourClassesAdapter adapter = new ListYourClassesAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        SetSpinner();


        insert = findViewById(R.id.btn_insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });



    }

    private void SetSpinner() {
        Spinner dropdown = findViewById(R.id.spinner_course);
        String[] items_course = new String[]{"B.Tech", "Polytechnic", "Diploma"};
        ArrayAdapter<String> course_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_course);
        dropdown.setAdapter(course_adapter);

        Spinner dropdown1 = findViewById(R.id.spinner_branch);
        String[] items_branch = new String[]{"CSE", "EC", "Civil","IT"};
        ArrayAdapter<String> branch_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_branch);
        dropdown1.setAdapter(branch_adapter);

        Spinner dropdown2 = findViewById(R.id.spinner_semester);
        String[] items_sem = new String[]{"1", "2", "3","4", "5", "6","7", "8"};
        ArrayAdapter<String> sem_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_sem);
        dropdown2.setAdapter(sem_adapter);

        Spinner dropdown3 = findViewById(R.id.spinner_course);
        String[] items_sub = new String[]{"DSA", "Material Science", "COA", "Technical Communication"};
        ArrayAdapter<String> sub_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_sub);
        dropdown3.setAdapter(sub_adapter);
    }

    private void InsertData() {
        subject = findViewById(R.id.etxt_subject);
        designation = findViewById(R.id.etxt_designation1);
        salary = findViewById(R.id.etxt_salary);

        final String usubject = subject.getText().toString().trim();
        final String udes = designation.getText().toString().trim();
        final String usalary = salary.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                subject.setText("");
                designation.setText("");
                salary.setText("");
                if(response.equals("Success")){
                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
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
                param.put("teacher_subject",usubject);
                param.put("teacher_designation",udes);
                param.put("salary",usalary);
                return param;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}