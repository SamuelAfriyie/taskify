package com.example.taskify.Task.presentation.TaskList.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.taskify.R;
import com.example.taskify.Task.domain.TaskItem;
import com.example.taskify.Task.presentation.AddTask.ui.AddTaskActivity;
import com.example.taskify.Task.presentation.TaskList.TaskAdapter;
import com.example.taskify.Task.presentation.TaskList.TaskListViewModel;
import com.example.taskify.common.NavHost;
import com.example.taskify.core.failures.Failure;
import com.example.taskify.core.utils.Response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TaskActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<TaskItem> taskItemList;

    private TaskListViewModel taskListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        recyclerView = findViewById(R.id.recyclerView);

        taskListViewModel = new ViewModelProvider(this).get(TaskListViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        taskItemList = new ArrayList<>();

        taskAdapter = new TaskAdapter(this, taskItemList);
        recyclerView.setAdapter(taskAdapter);
        taskListViewModel.getTask().observe(this, v -> observeNoteData(v, taskItemList));
        taskListViewModel.deletionStatus.observe(this, v -> {
            if (v.isSuccess()) {
                taskListViewModel.fetchAllTask();
                Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnClickListener(v -> NavHost.navigateTo(this, AddTaskActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu_items, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Set up search view actions
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TextSubmit", query);
                // Handle search query submission
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TextChange", newText);
                taskListViewModel.filterAllTask(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_priority_high) {
            taskListViewModel.filterAllTaskByPriority("High");
            return true;
        } else if (id == R.id.action_sort_priority_medium) {
            taskListViewModel.filterAllTaskByPriority("Medium");
            return true;
        } else if (id == R.id.action_sort_priority_low) {
            taskListViewModel.filterAllTaskByPriority("Low");
            return true;
        } else if (id == R.id.action_sort_reset) {
            taskListViewModel.filterAllTaskByPriority("");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void observeNoteData(Response<List<TaskItem>, Failure> v, List<TaskItem> noteArrayList) {
        taskAdapter.notifyDataSetChanged();
        if (v.isSuccess()) {
            Response.Success<List<TaskItem>, Failure> success = (Response.Success<List<TaskItem>, Failure>) v;
            noteArrayList.clear();
            noteArrayList.addAll(success.getValue());
        } else {
            Response.Failure<List<TaskItem>, Failure> failure = (Response.Failure<List<TaskItem>, Failure>) v;
            Failure res = failure.getValue();
            Toast.makeText(this, res.getRES_MSG(), Toast.LENGTH_SHORT).show();
        }
    }

}