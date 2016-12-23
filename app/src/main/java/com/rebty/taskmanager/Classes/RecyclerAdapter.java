package com.rebty.taskmanager.Classes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rebty.taskmanager.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskViewHolder> {

    List<Task> tasks;

    public RecyclerAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        TaskViewHolder pvh = new TaskViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.taskText.setText(tasks.get(i).text);
        taskViewHolder.taskTime.setText(tasks.get(i).timeString);
        taskViewHolder.taskHeader.setText(tasks.get(i).header);
        taskViewHolder.cv.setBackgroundColor(tasks.get(i).iconColor);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView taskText;
        TextView taskHeader;
        TextView taskTime;

        TaskViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            taskText = (TextView) itemView.findViewById(R.id.task_text);
            taskTime = (TextView) itemView.findViewById(R.id.task_time);
            taskHeader = (TextView) itemView.findViewById(R.id.task_header);
        }
    }
}