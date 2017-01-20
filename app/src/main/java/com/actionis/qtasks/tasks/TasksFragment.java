package com.actionis.qtasks.tasks;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionis.qtasks.R;
import com.actionis.qtasks.addedittask.AddEditTaskActivity;
import com.actionis.qtasks.data.TasksContract;
import com.actionis.qtasks.data.TasksDbHelper;
import com.actionis.qtasks.taskdetail.TaskDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * Vista para la lista de tasks
 */
public class TasksFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_TASK = 2;


    private TasksDbHelper mTasksDbHelper;

    private ListView mTasksList;
    private TasksCursorAdapter mTasksAdapter;
    private FloatingActionButton mAddButton;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance() {

        return new TasksFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TasksActivity activity = (TasksActivity) getActivity();
        String listTask = activity.setFilter();

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);

        // Referencias UI
        mTasksList = (ListView) root.findViewById(R.id.tasks_list);
        mTasksAdapter = new TasksCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mTasksList.setAdapter(mTasksAdapter);

        // Eventos
        mTasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mTasksAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(TasksContract.TaskEntry.ID));

                showDetailScreen(currentLawyerId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        setHasOptionsMenu(true);


        //Borrar db al iniciar
        //getActivity().deleteDatabase(LawyersDbHelper.DATABASE_NAME);

        // Instancia de helper
        mTasksDbHelper = new TasksDbHelper(getActivity());


        // Carga de datos
        loadTasks();

        //Toast.makeText(getContext(),listTask, Toast.LENGTH_SHORT).show();
        return  root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                //Toast.makeText(getApplicationContext(),"Settings", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_tasks_all:
                Toast.makeText(getContext(),"All", Toast.LENGTH_SHORT).show();
                loadTasks();
                //listTask="All";
                return true;
            case R.id.action_tasks_done:
                Toast.makeText(getContext(),"Done", Toast.LENGTH_SHORT).show();
                loadDoneTasks();
                //listTask="Done";
                return true;
            case R.id.action_tasks_pending:
                Toast.makeText(getContext(),"Pending", Toast.LENGTH_SHORT).show();
                loadPendingTasks();
                //listTask="Pending";
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditTaskActivity.REQUEST_ADD_TASK:
                    showSuccessfullSavedMessage();
                    loadTasks();
                    break;
                case REQUEST_UPDATE_DELETE_TASK:
                    loadTasks();
                    break;
            }
        }
    }

    private void loadTasks() {
        // Cargar datos...
        new TasksLoadTask().execute();
    }
    private void loadDoneTasks() {
        // Cargar datos...
        new TasksLoadDoneTask().execute();
    }
    private void loadPendingTasks() {
        // Cargar datos...
        new TasksLoadPendingTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Task guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK);
    }

    private void showDetailScreen(String taskID) {
        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
        intent.putExtra(TasksActivity.EXTRA_TASK_ID, taskID);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_TASK);
    }

    private class TasksLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {

            return mTasksDbHelper.getAllTasks();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mTasksAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

    private class TasksLoadDoneTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mTasksDbHelper.getDoneTasks();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mTasksAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

    private class TasksLoadPendingTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mTasksDbHelper.getPendingTasks();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mTasksAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
