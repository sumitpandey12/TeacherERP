package com.example.teachererp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachererp.Model.ListItemAttendanceModel;
import com.example.teachererp.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ListItemAttendanceAdapter extends RecyclerView.Adapter<ListItemAttendanceAdapter.ViewHolder>{

    public ListItemAttendanceModel[] attendanceModel;
    public Context context;

    public ListItemAttendanceAdapter(ArrayList<ListItemAttendanceModel> attendanceModel, Context context) {
        this.attendanceModel = attendanceModel.toArray(new ListItemAttendanceModel[0]);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_attendance_students,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItemAttendanceModel item = attendanceModel[position];
        holder.id.setText(String.valueOf(item.getId()));
        holder.name.setText(item.getName());
        if(item.getStatus()==1){
            holder.present.setChecked(true);
        }else{
            holder.present.setChecked(false);
        }
        if(item.getStatus()==2){
            holder.absent.setChecked(true);
        }else{
            holder.absent.setChecked(false);
        }
        if(item.getStatus()==3){
            holder.leave.setChecked(true);
        }else{
            holder.leave.setChecked(false);
        }
        if (item.getStatus()==0){
            holder.present.setChecked(false);
            holder.absent.setChecked(false);
            holder.leave.setChecked(false);
        }
        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setStatus(2);
            }
        });

        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setStatus(1);
            }
        });

        holder.leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setStatus(3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attendanceModel.length;
    }

    public ListItemAttendanceModel[] getAttendanceModel(){
        return attendanceModel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView name;
        public RadioButton present;
        public RadioButton absent;
        public RadioButton leave;
        public RadioGroup statusGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.txt_item_attendance_id);
            this.name = itemView.findViewById(R.id.txt_item_attendance_name);
            this.present = itemView.findViewById(R.id.radio_item_attendance_present);
            this.absent = itemView.findViewById(R.id.radio_item_attendance_absent);
            this.leave = itemView.findViewById(R.id.radio_item_attendance_leave);
            this.statusGroup = itemView.findViewById(R.id.status_group);
        }
    }
}
