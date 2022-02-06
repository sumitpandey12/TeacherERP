package com.example.teachererp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teachererp.Adapter.ListYourClassesAdapter;
import com.example.teachererp.Model.BranchModel;
import com.example.teachererp.Model.CoursesModel;
import com.example.teachererp.Model.ListYourClassesModel;
import com.example.teachererp.Model.SemesterModel;
import com.example.teachererp.Model.SubjectModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    AutoCompleteTextView subject;
    TextInputEditText eName,eDesignation,eEmail,ePhone;
    TextView name,email,save;
    RecyclerView recyclerView;
    ProgressBar progressBar1;
    TextInputLayout layout_subject;

    MaterialTextView ChangeProfile;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SubjectModel item;


    private static final String url = "http://192.168.1.77/LoginRegister/login.php";

    List<SubjectModel> subjectModels;

    String branchID="0",subjectID = "0",teacher_id, StringName;


/*    SharedPreferences Variable name
    id = teacher id;
    username = teacher username;
    name = teacher name;
    phone = teacher phone;
    email = teacher email
    subject = teacher subject
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Init();

        sharedPreferences = getSharedPreferences("TeacherERP",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        GetPreference();


        subjectModels = new ArrayList<>();

        FetchSubject(branchID);

        subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = (SubjectModel) adapterView.getItemAtPosition(i);
                subjectID = String.valueOf(item.getId());
                Toast.makeText(UserProfile.this, ""+subjectID, Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((eName.getText().length()) >0)){
                    eName.setFocusable(true);
                    Toast.makeText(UserProfile.this, "Name Required", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!((eDesignation.getText().length()) >0)){
                    eDesignation.setFocusable(true);
                    Toast.makeText(UserProfile.this, "Designation Required", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!((eEmail.getText().length()) >0)){
                    eEmail.setFocusable(true);
                    Toast.makeText(UserProfile.this, "Email Required", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!((ePhone.getText().length()) >=10)){
                    ePhone.setFocusable(true);
                    Toast.makeText(UserProfile.this, "Phone Not Correct", Toast.LENGTH_SHORT).show();
                    return;
                }else if (subjectID.equals("0")){
                    subject.setFocusable(true);
                    Toast.makeText(UserProfile.this, "Subject Required", Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    InsertTeacherProfile(teacher_id,eName.getText().toString(),eDesignation.getText().toString(),eEmail.getText().toString(),ePhone.getText().toString(),subjectID);
                }
            }
        });


    }

    private void FetchSubject(String branch_id){
        subjectModels.clear();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject course = array.getJSONObject(i);

                        subjectModels.add(new SubjectModel(
                                course.getInt("id"),
                                course.getString("subject_name")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<SubjectModel> adapter =
                        new ArrayAdapter<SubjectModel>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, subjectModels);

                subject.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("type","getSubject");
                param.put("branch_id", branch_id);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void InsertTeacherProfile(String teacher_id,String mname, String designation, String email, String phone, String subject_id){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UserProfile.this, ""+response, Toast.LENGTH_SHORT).show();
                SetPreference();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("type","UpdateTeacherDetails");
                param.put("teacher_id",teacher_id);
                param.put("subject_id",subject_id);
                param.put("name",mname);
                param.put("designation",designation);
                param.put("email",email);
                param.put("phone",phone);
                return param;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void SetPreference() {
        editor.putString("designation",eDesignation.getText().toString());
        editor.putString("phone",ePhone.getText().toString());
        editor.putString("email",eEmail.getText().toString());
        editor.putString("name",eName.getText().toString());
        editor.putString("subject",item.getName());
        editor.commit();
    }

    private void GetPreference(){
        teacher_id = sharedPreferences.getString("teacher_id","0");
        StringName = sharedPreferences.getString("name","Sumit Pandey");
        eName.setText(StringName);
        name.setText(StringName);
        eEmail.setText(sharedPreferences.getString("email",""));
        ePhone.setText(sharedPreferences.getString("phone",""));
        eDesignation.setText(sharedPreferences.getString("designation",""));
        layout_subject.setHint(sharedPreferences.getString("subject","Select Subject"));
    }

    private void Init(){
        subject = findViewById(R.id.spinner_subject);
        eDesignation = findViewById(R.id.etxt_designation);
        name = findViewById(R.id.txt_user_name);
        save = findViewById(R.id.txt_btn_save);
        email = findViewById(R.id.txt_user_email);
        eName = findViewById(R.id.etxt_full_name);
        eEmail = findViewById(R.id.etxt_email);
        ePhone = findViewById(R.id.etxt_phone);
        ChangeProfile = findViewById(R.id.txt_change_profile);
        recyclerView = findViewById(R.id.recy_list_classes);
        progressBar1 = findViewById(R.id.progress_bar_course);
        layout_subject = findViewById(R.id.layout_subject);
    }
}