package com.example.teachererp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    AutoCompleteTextView period,course,branch,semester;
    MaterialButton btnAddSubject;
    TextInputLayout etxtDesignation;
    TextView name,email;
    RecyclerView recyclerView;
    ProgressBar progressBar1;

    private static final String url = "http://192.168.1.77/LoginRegister/login.php";

    List<CoursesModel> coursesList;
    List<BranchModel> branchModelList;
    List<SemesterModel> semesterModelList;

    String courseID,branchID,semesterID,periodID;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        coursesList = new ArrayList<>();
        branchModelList = new ArrayList<>();
        semesterModelList = new ArrayList<>();

        Init();

        FetchCourse();

        course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CoursesModel coursesModel = (CoursesModel) adapterView.getItemAtPosition(i);
                progressBar1.setVisibility(View.VISIBLE);
                FetchBranch(String.valueOf(coursesModel.getId()));
                FetchSem(String.valueOf(coursesModel.getId()));
                courseID= String.valueOf(coursesModel.getId());
                progressBar1.setVisibility(View.VISIBLE);
            }
        });


    }


    private void FetchBranch(String course_id) {
        branch.setText(null);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject course = array.getJSONObject(i);

                        branchModelList.add(new BranchModel(
                                course.getInt("id"),
                                course.getString("branch_name")
                        ));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressBar1.setVisibility(View.GONE);
                ArrayAdapter<BranchModel> adapter =
                        new ArrayAdapter<BranchModel>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, branchModelList);

                branch.setAdapter(adapter);
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
                param.put("type","getbranch");
                param.put("course_id", course_id);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void FetchCourse(){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject course = array.getJSONObject(i);

                        coursesList.add(new CoursesModel(
                                course.getInt("id"),
                                course.getString("course_name")
                        ));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar1.setVisibility(View.GONE);
                ArrayAdapter<CoursesModel> adapter =
                        new ArrayAdapter<CoursesModel>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, coursesList);

                course.setAdapter(adapter);
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
                param.put("type","getcourse");
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void FetchSem(String course_id){
        semester.setText(null);
        semesterModelList.clear();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject course = array.getJSONObject(i);

                        semesterModelList.add(new SemesterModel(
                                course.getInt("id"),
                                course.getInt("semester")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<SemesterModel> adapter =
                        new ArrayAdapter<SemesterModel>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, semesterModelList);

                semester.setAdapter(adapter);


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
                param.put("type","getsemester");
                param.put("course_id", course_id);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void FetchSubject(String branch_id){
        semester.setText(null);
        semesterModelList.clear();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject course = array.getJSONObject(i);

                        semesterModelList.add(new SemesterModel(
                                course.getInt("id"),
                                course.getInt("semester")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<SemesterModel> adapter =
                        new ArrayAdapter<SemesterModel>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, semesterModelList);

                semester.setAdapter(adapter);


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

    private void InsertTeacherProfile(String desi, String course_id, String branch_id, String sem_id, String period, String subject_id){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                param.put("desig",desi);
                param.put("course_id",course_id);
                param.put("branch_id",branch_id);
                param.put("semester_id",sem_id);
                param.put("period",period);
                param.put("subject_id",subject_id);
                return param;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void Init(){
        period = findViewById(R.id.spinner_period);
        course = findViewById(R.id.spinner_course1);
        branch = findViewById(R.id.spinner_branch);
        semester = findViewById(R.id.spinner_semester);
        btnAddSubject = findViewById(R.id.btn_add_subject);
        etxtDesignation = findViewById(R.id.etxt_designation);
        name = findViewById(R.id.txt_user_name);
        email = findViewById(R.id.txt_user_email);
        recyclerView = findViewById(R.id.recy_list_classes);
        progressBar1 = findViewById(R.id.progress_bar_course);
    }
}