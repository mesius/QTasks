package com.actionis.qtasks.addedittask;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.actionis.qtasks.R;
import com.actionis.qtasks.tasks.TasksActivity;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String taskId = getIntent().getStringExtra(TasksActivity.EXTRA_TASK_ID);

        setTitle(taskId == null ? "Add task" : "Edit task");

        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_task_container);
        if (addEditTaskFragment == null) {
            addEditTaskFragment = AddEditTaskFragment.newInstance(taskId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_task_container, addEditTaskFragment)
                    .commit();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
