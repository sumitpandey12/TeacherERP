package com.example.teachererp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachererp.Model.ListYourClassesModel;
import com.example.teachererp.R;

public class ListYourClassesAdapter extends RecyclerView.Adapter<ListYourClassesAdapter.ViewHolder> {

    private ListYourClassesModel[] classesData;

    public ListYourClassesAdapter(ListYourClassesModel[] classesData) {
        this.classesData = classesData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View Item= layoutInflater.inflate(R.layout.item_list_classes, parent, false);
        ViewHolder viewHolder = new ViewHolder(Item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListYourClassesModel myListData = classesData[position];
        holder.Course.setText(classesData[position].getCourse());
        holder.Branch.setText(classesData[position].getBranch());
        holder.Semester.setText(classesData[position].getSemester());
        holder.Subject.setText(classesData[position].getSubject());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),myListData.getSubject()+" Item Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classesData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Course;
        public TextView Branch;
        public TextView Semester;
        public TextView Subject;
        public TextView Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.Course = itemView.findViewById(R.id.txt_item_list_classes_course);
            this.Branch = itemView.findViewById(R.id.txt_item_list_classes_branch);
            this.Semester = itemView.findViewById(R.id.txt_item_list_classes_semester);
            this.Subject = itemView.findViewById(R.id.txt_item_list_classes_subject);
            this.Delete = itemView.findViewById(R.id.txt_item_list_classes_del);
        }
    }
}
