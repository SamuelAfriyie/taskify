package com.example.taskify.Task.presentation.TaskList;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskify.R;
import com.example.taskify.Task.domain.TaskItem;
import com.example.taskify.core.utils.RichTextEditorTextRefiner;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private List<TaskItem> taskItemList;
    private Context context;

    public TaskAdapter(Context context, List<TaskItem> taskItemList) {
        this.taskItemList = taskItemList;
         this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem taskItem = taskItemList.get(position);
        holder.titleTextView.setText(taskItem.getTitle());
        holder.descriptionTextView.setText(RichTextEditorTextRefiner.fromHtml(taskItem.getDescription()));

        // Set priority color
        int priorityColor = getPriorityColor(taskItem.getPriority()); // Method to determine color based on priority
        holder.priorityIndicator.setBackgroundTintList(ColorStateList.valueOf(priorityColor));
    }

    @Override
    public int getItemCount() {
        return taskItemList.size();
    }

    private int getPriorityColor(String priority) {
        switch (priority) {
            case "High":
                return ContextCompat.getColor(context, android.R.color.holo_red_light);
            case "Medium":
                return ContextCompat.getColor(context, android.R.color.holo_orange_light);
            case "Low":
                return ContextCompat.getColor(context, android.R.color.holo_green_light);
            default:
                return ContextCompat.getColor(context, android.R.color.darker_gray);
        }
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        View priorityIndicator;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priorityIndicator = itemView.findViewById(R.id.priorityIndicator);
        }
    }
}
