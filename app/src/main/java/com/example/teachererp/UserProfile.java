package com.example.teachererp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.teachererp.Adapter.ListYourClassesAdapter;
import com.example.teachererp.Model.ListYourClassesModel;

public class UserProfile extends AppCompatActivity {

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
}