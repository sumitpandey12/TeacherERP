package com.example.teachererp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teachererp.Adapter.ListItemAttendanceAdapter;
import com.example.teachererp.Model.BranchModel;
import com.example.teachererp.Model.CoursesModel;
import com.example.teachererp.Model.ListItemAttendanceModel;
import com.example.teachererp.Model.SemesterModel;
import com.example.teachererp.Model.StudentsModel;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView expand,back;
    TextView info;
    TextView presentCount;
    TextView absentCount;
    TextView leaveCount;
    LinearLayout layout_choose;
    RecyclerView recy_attendance;
    AutoCompleteTextView period,course,branch,semester;
    MaterialButton submit,home,seeList,btnUpdate;
    ProgressBar progressBar, progressBar1, progressBar2;
    RelativeLayout relativeLayout1,relativeLayout2;
    RadioButton radioPresent, radioAbsent, radioLeave;
    CardView cardView2;

    private static final String url = "http://192.168.1.77/LoginRegister/login.php";

    List<CoursesModel> coursesList;
    List<BranchModel> branchModelList;
    List<SemesterModel> semesterModelList;
    ArrayList<String> periodList;
    ArrayList<ListItemAttendanceModel> attendanceModels,remainStudent;
    ListItemAttendanceAdapter attendanceAdapter;

    String date;

    //store student selected ID's
    String courseID,branchID,semesterID,periodID;

    //store student status count
    int presentStudent, absentStudent, leaveStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        Init();

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        //initialise the coursesList
        attendanceModels = new ArrayList<>();
        periodList = new ArrayList<>();
        coursesList = new ArrayList<>();
        remainStudent = new ArrayList<>();
        course.setSelected(false);
        branchModelList = new ArrayList<>();
        branch.setSelected(false);
        semesterModelList = new ArrayList<>();
        semester.setSelected(false);

        //add period into the period array list;
        periodList.add("1");
        periodList.add("2");
        periodList.add("3");
        periodList.add("4");
        periodList.add("5");
        periodList.add("6");

        //set period list into the adapter and adapter into the spinner
        ArrayAdapter<String> periodAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.item_dropdown,periodList);
        period.setAdapter(periodAdapter);

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

        branch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BranchModel branchModel = (BranchModel) adapterView.getItemAtPosition(i);
                branchID = String.valueOf(branchModel.getId());
            }
        });

        semester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semesterID = semester.getText().toString();
                if(periodID==null){
                    Toast.makeText(AttendanceActivity.this, "Select Period First", Toast.LENGTH_SHORT).show();
                }else if(branchID == null){
                    semester.setText(null);
                    Toast.makeText(AttendanceActivity.this, "Select Branch First", Toast.LENGTH_SHORT).show();
                } else  {
                    progressBar2.setVisibility(View.VISIBLE);
                    checkAttendanceStatus(courseID,branchID,semesterID,periodID,date);
                }
            }
        });

        period.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                periodID = period.getText().toString().trim();
                if(!(courseID==null) && !(branchID==null) && !(semesterID==null)){
                    progressBar2.setVisibility(View.VISIBLE);
                    checkAttendanceStatus(courseID,branchID,semesterID,periodID,date);
                }
            }
        });

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpandMore();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remainStudent.clear();
                try {
                    ListItemAttendanceModel[] models = attendanceAdapter.getAttendanceModel();
                    for (int i = 0; i < models.length; i++) {
                        String id = String.valueOf(models[i].getId());
                        String status = String.valueOf(models[i].getStatus());
                        if (Integer.parseInt(status) > 0) {
                            CountStatus(status);
                            SetStudentsAttendance(id, courseID, branchID, semesterID, periodID, date, status);
                        } else {
                            remainStudent.add(new ListItemAttendanceModel(models[i].getId(), models[i].getName(), models[i].getCourse_id(), models[i].getBranch_id(), models[i].getSemester_id()));
                        }
                    }
                    SetStudentsAdapterList(remainStudent);

                    if (remainStudent.isEmpty()) {
                        ShowStatusFragment();
                    }
                }catch (Exception e){};
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this, MainActivity.class));
                finish();
            }
        });

        seeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchAttendance(courseID,branchID,semesterID,periodID,date);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentStudent=0;
                absentStudent=0;
                leaveStudent =0;
                ListItemAttendanceModel[] models = attendanceAdapter.getAttendanceModel();
                for (int i =0; i<models.length;i++){
                    String id = String.valueOf(models[i].getId());
                    String status = String.valueOf(models[i].getStatus());
                    if (Integer.parseInt(status)>0){
                        CountStatus(status);
                        UpdateAttendance(id,periodID,date,status);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this, MainActivity.class));
            }
        });

        radioPresent.setOnClickListener(this);
        radioAbsent.setOnClickListener(this);
        radioLeave.setOnClickListener(this);

    }

    private void FetchAttendance(String courseID, String branchID, String semesterID, String periodID,String date){
        attendanceModels.clear();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout2.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject course = array.getJSONObject(i);

                        attendanceModels.add(new ListItemAttendanceModel(
                                course.getInt("student_id"),
                                course.getString("student_name"),
                                Integer.parseInt(courseID),
                                Integer.parseInt(branchID),
                                Integer.parseInt(semesterID),
                                course.getInt("status")
                        ));
                    }
                    progressBar2.setVisibility(View.GONE);
                    SetStudentsAdapterList(attendanceModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String >();
                param.put("type","getAttendanceResult");
                param.put("course_id",courseID);
                param.put("branch_id",branchID);
                param.put("semester_id",semesterID);
                param.put("period",periodID);
                param.put("date",date);
                return param;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void checkAttendanceStatus(String courseID, String branchID, String semesterID, String periodID,String date){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Already Attendance Submited")){
                    FetchAttendance(courseID,branchID,semesterID,periodID,date);
                }else {
                    FetchStudents(courseID, branchID, semesterID);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String >();
                param.put("type","checkAttendanceStatus");
                param.put("course_id",courseID);
                param.put("branch_id",branchID);
                param.put("semester_id",semesterID);
                param.put("period",periodID);
                param.put("date",date);
                param.put("status","1");
                return param;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void UpdateAttendance(String student_id, String periodID,String date, String status){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ShowStatusFragment();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String >();
                param.put("type","UpdateAttendance");
                param.put("student_id",student_id);
                param.put("period",periodID);
                param.put("status", status);
                param.put("date",date);
                return param;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void ShowStatusFragment() {
        cardView2.setVisibility(View.GONE);
        relativeLayout1.setVisibility(View.GONE);
        relativeLayout2.setVisibility(View.VISIBLE);
        absentCount.setText(String.valueOf(absentStudent));
        presentCount.setText(String.valueOf(presentStudent));
        leaveCount.setText(String.valueOf(leaveStudent));
    }

    private void CountStatus(String status) {
        if(Integer.parseInt(status)==1){
            presentStudent++;
        } else if(Integer.parseInt(status)==2){
            absentStudent++;
        } else if(Integer.parseInt(status)==3){
            leaveStudent++;
        }
    }

    private void SetStudentsAttendance(String student_id,String course_id,String branch_id,String sem_id,String period, String date, String status) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

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
                param.put("type","setStudentsAttendance");
                param.put("student_id",student_id);
                param.put("course_id", course_id);
                param.put("branch_id", branch_id);
                param.put("semester_id",sem_id);
                param.put("period", period);
                param.put("date", date);
                param.put("status", status);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void Init() {
        period = findViewById(R.id.spinner_period);
        course = findViewById(R.id.spinner_course);
        branch = findViewById(R.id.spinner_branch);
        semester = findViewById(R.id.spinner_semester);
        expand = findViewById(R.id.img_expand);
        recy_attendance = findViewById(R.id.recy_list_attendance);
        info = findViewById(R.id.txt_info);
        layout_choose = findViewById(R.id.layout_choose);
        submit = findViewById(R.id.mbtn_sbmt);
        progressBar = findViewById(R.id.progress_bar);
        progressBar1 = findViewById(R.id.progress_bar_course);
        progressBar2 = findViewById(R.id.progress_bar2);
        presentCount = findViewById(R.id.txt_present);
        absentCount = findViewById(R.id.txt_absent);
        leaveCount = findViewById(R.id.txt_leave);
        home= findViewById(R.id.btn_home);
        relativeLayout1= findViewById(R.id.relative_layout1);
        relativeLayout2= findViewById(R.id.relative_layout2);
        seeList = findViewById(R.id.btn_see_list);
        btnUpdate = findViewById(R.id.mbtn_update);
        radioPresent = findViewById(R.id.radioPresent);
        radioAbsent = findViewById(R.id.radioAbsent);
        radioLeave = findViewById(R.id.radioLeave);
        cardView2 = findViewById(R.id.card2);
        back = findViewById(R.id.btn_back);
    }

    private void MarkAll(int a){
        int count = (attendanceAdapter.getItemCount()-1);
        while (count>=0){
            ListItemAttendanceModel item = attendanceAdapter.attendanceModel[count];
            if (a==1){
                item.setStatus(1);
            }else if (a==2){
                item.setStatus(2);
            }else if (a==3){
                item.setStatus(3);
            }
            count--;
        }
        attendanceAdapter.notifyDataSetChanged();
    }

    private void FetchBranch(String course_id) {
        branch.setText(null);
        branchModelList.clear();
        attendanceModels.clear();
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

    private void FetchStudents(String course_id,String branch_id,String sem_id) {
        attendanceModels.clear();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject course = array.getJSONObject(i);

                        attendanceModels.add(new ListItemAttendanceModel(
                                course.getInt("id"),
                                course.getString("name"),
                                course.getInt("course_id"),
                                course.getInt("branch_id"),
                                course.getInt("semester_id")
                        ));
                    }
                    btnUpdate.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.GONE);
                    SetStudentsAdapterList(attendanceModels);
                    ExpandMore();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
                param.put("type","getAttendenceStudentsForMark");
                param.put("course_id", course_id);
                param.put("branch_id", branch_id);
                param.put("semester_id", sem_id);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void SetStudentsAdapterList(ArrayList<ListItemAttendanceModel> a) {
        cardView2.setVisibility(View.VISIBLE);
        attendanceAdapter = new ListItemAttendanceAdapter(a,getApplicationContext());
        recy_attendance.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recy_attendance.setAdapter(attendanceAdapter);
        info.setVisibility(View.GONE);
    }

    private void ExpandMore(){
        if (layout_choose.getVisibility()== View.VISIBLE){
            expand.setImageResource(R.drawable.ic_expand_more);
            layout_choose.setVisibility(View.GONE);
        }else {
            expand.setImageResource(R.drawable.ic_expand_less);
            layout_choose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.equals(radioPresent)) {
            MarkAll(1);
            return;
        }
        if (view.equals(radioAbsent)) {
            MarkAll(2);
            return;
        }
        if (view.equals(radioLeave)) {
            MarkAll(3);
        }
    }
}