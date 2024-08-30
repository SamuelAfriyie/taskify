package com.example.taskify.Task.presentation.AddTask.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskify.R;
import com.example.taskify.Task.domain.TaskItem;
import com.example.taskify.Task.presentation.AddTask.AddTaskViewModel;
import com.example.taskify.Task.presentation.TaskList.ui.TaskActivity;
import com.example.taskify.common.NavHost;
import com.example.taskify.core.failures.Failure;
import com.example.taskify.core.utils.Response;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.richeditor.RichEditor;

@AndroidEntryPoint
public class AddTaskActivity extends AppCompatActivity {
    private AddTaskViewModel addTaskViewModel;
    private TextView title;
    private String priority;
    private RichEditor description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Find the Spinner in the layout
        Spinner spinner = findViewById(R.id.spinner);
        title = findViewById(R.id.txt_title);
        description = findViewById(R.id.editor);
        addTaskViewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                priority = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                priority = "";
                // Do nothing
            }
        });

        // Enable the Up (Back) button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        addTaskViewModel.addNoteEventListener.observe(this, v -> {
            if (v.isSuccess()) {
                NavHost.navigateTo(this, TaskActivity.class);
//                onBackPressed();
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            } else {
                Response.Failure<Integer, Failure> failure = (Response.Failure<Integer, Failure>) v;
                Failure res = failure.getValue();
                Toast.makeText(this, res.getRES_MSG(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_save) {
            //saving records
            addTaskViewModel.AddNoteEvent(new TaskItem(title.getText().toString(), description.getHtml(), priority));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu_option, menu);
        return true;
    }


}