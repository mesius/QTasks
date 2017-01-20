package com.actionis.qtasks.taskdetail;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionis.qtasks.R;
import com.actionis.qtasks.addedittask.AddEditTaskActivity;
import com.actionis.qtasks.data.Task;
import com.actionis.qtasks.data.TasksDbHelper;
import com.actionis.qtasks.tasks.TasksActivity;
import com.actionis.qtasks.tasks.TasksFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {
    private static final String ARG_TASK_ID = "taskId";

    private String mTaskId;

    //private CollapsingToolbarLayout mCollapsingView;
    //private ImageView mAvatar;
    private TextView mSummary;
    private TextView mCategory;
    private TextView mDate;
    private TextView mTitle;
    private TextView mDone;

    private TasksDbHelper mTasksDbHelper;


    public TaskDetailFragment() {
        // Required empty public constructor
    }


    public static TaskDetailFragment newInstance(String taskId) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTaskId = getArguments().getString(ARG_TASK_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_task_detail, container, false);

        View root = inflater.inflate(R.layout.fragment_task_detail, container, false);
        //mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        //mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mTitle = (TextView) root.findViewById(R.id.tv_title);
        mSummary = (TextView) root.findViewById(R.id.tv_summary);
        mCategory = (TextView) root.findViewById(R.id.tv_category);
        mDate = (TextView) root.findViewById(R.id.tv_date);
        mDone = (TextView) root.findViewById(R.id.tv_done);

        mTasksDbHelper = new TasksDbHelper(getActivity());

        loadTask();

        return root;
    }

    private void loadTask() {
        new GetTaskByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteTaskTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Acciones
        if (requestCode == TasksFragment.REQUEST_UPDATE_DELETE_TASK) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showTask(Task task) {
        //mCollapsingView.setTitle(lawyer.getName());
        //Glide.with(this)
         //       .load(Uri.parse("file:///android_asset/" + lawyer.getAvatarUri()))
          //      .centerCrop()
            //    .into(mAvatar);
        mTitle.setText(task.getTitle());
        mSummary.setText(task.getSummary());
        mCategory.setText(task.getCategory());
        mDate.setText(task.getDate());
        mDone.setText(task.getDone());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
        intent.putExtra(TasksActivity.EXTRA_TASK_ID, mTaskId);
        startActivityForResult(intent, TasksFragment.REQUEST_UPDATE_DELETE_TASK);
    }

    private void showTasksScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetTaskByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mTasksDbHelper.getTaskById(mTaskId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showTask(new Task(cursor));
            } else {
                showLoadError();
            }
        }

    }



    private class DeleteTaskTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mTasksDbHelper.deleteTask(mTaskId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showTasksScreen(integer > 0);
        }

    }

}
