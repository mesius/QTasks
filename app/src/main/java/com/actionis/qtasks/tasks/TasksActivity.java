package com.actionis.qtasks.tasks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.actionis.qtasks.R;

public class TasksActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "extra_task_id";

    public String listTask = "All";



    /*TODO Traduccion-strings
    - checkbox
    + Date
    + row list view
    - Category List
    + Cabecera gris
    - sharedpreferneces si no hay items
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tasks);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TasksFragment fragment = (TasksFragment)
                getSupportFragmentManager().findFragmentById(R.id.tasks_container);


        if (fragment == null) {


            fragment = TasksFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.tasks_container, fragment)
                    .commit();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                //Toast.makeText(getApplicationContext(),"Settings", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_tasks_all:
                //Toast.makeText(getApplicationContext(),"All", Toast.LENGTH_SHORT).show();
                listTask="All";
                return true;
            case R.id.action_tasks_done:
                //Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_SHORT).show();
                listTask="Done";
                return true;
            case R.id.action_tasks_pending:
                //Toast.makeText(getApplicationContext(),"Pending", Toast.LENGTH_SHORT).show();
                listTask="Pending";
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    */



    public String setFilter() {
        //listTask = filter;
        return listTask;
    }

}
