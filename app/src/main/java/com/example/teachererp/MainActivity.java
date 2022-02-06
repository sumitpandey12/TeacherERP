package com.example.teachererp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    LinearLayout attendence;
    SharedPreferences sharedPreferences;
    TextView Name;
    ImageView profile;
    SharedPreferences.Editor editor;
    String name, designation;
    DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("TeacherERP",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("admin",1);
        editor.commit();
        name = sharedPreferences.getString("name","Sumit");
        Name.setText("Hii "+name);
        Toast.makeText(this, ""+sharedPreferences.getString("name",""), Toast.LENGTH_SHORT).show();




        attendence = findViewById(R.id.layout_attendence);
        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AttendanceActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getTitle().equals("Admin")){
                    startAdmin();
                }
                return false;
            }
        });
    }

    private void startAdmin() {
        int adminid = sharedPreferences.getInt("admin",0);
        if (adminid==1){
            startActivity(new Intent(MainActivity.this,UserProfile.class));
        }else {
            Toast.makeText(this, "Only Admin Can Assess!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void Init(){
        Name = findViewById(R.id.txt_name);
        profile = findViewById(R.id.img_profile);
        drawerLayout = findViewById(R.id.nav_drawer);
        navView = findViewById(R.id.navi_view);
    }
}