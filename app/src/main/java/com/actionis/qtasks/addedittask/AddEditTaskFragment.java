package com.actionis.qtasks.addedittask;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.actionis.qtasks.R;
import com.actionis.qtasks.data.Task;
import com.actionis.qtasks.data.TasksDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditTaskFragment extends Fragment {
    private static final String ARG_TASK_ID = "arg_task_id";

    private String mTaskId;

    private TasksDbHelper mTasksDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mTitleField;
    private TextInputEditText mCategoryField;
    private TextInputEditText mSummaryField;
    private TextInputEditText mDescriptionField;
    private TextInputLayout mTitleLabel;
    private TextInputLayout mCategoryLabel;
    private TextInputLayout mSummaryLabel;
    private TextInputLayout mDescriptionLabel;
    private CheckBox mDoneField;


    public AddEditTaskFragment() {
        // Required empty public constructor
    }


    public static AddEditTaskFragment newInstance(String taskId) {
        AddEditTaskFragment fragment = new AddEditTaskFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_task, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mTitleField = (TextInputEditText) root.findViewById(R.id.et_title);
        mCategoryField = (TextInputEditText) root.findViewById(R.id.et_category);
        mSummaryField = (TextInputEditText) root.findViewById(R.id.et_summary);
        mDescriptionField = (TextInputEditText) root.findViewById(R.id.et_description);
        mTitleLabel = (TextInputLayout) root.findViewById(R.id.til_title);
        mCategoryLabel = (TextInputLayout) root.findViewById(R.id.til_category);
        mSummaryLabel = (TextInputLayout) root.findViewById(R.id.til_summary);
        mDescriptionLabel = (TextInputLayout) root.findViewById(R.id.til_description);

        mDoneField = (CheckBox) root.findViewById(R.id.et_done);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditTask();
            }
        });

        mTasksDbHelper = new TasksDbHelper(getActivity());

        // Carga de datos
        if (mTaskId != null) {
            loadTask();
        }

        mDoneField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "Estado: " + (mDoneField.isChecked() ? "Marcado" : "No Marcado");
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }



    private void loadTask() {
        // AsyncTask
        new GetTaskByIdTask().execute();
    }

    private void addEditTask() {
        boolean error = false;

        String title = mTitleField.getText().toString();
        String category = mCategoryField.getText().toString();
        String summary = mSummaryField.getText().toString();
        String description = mDescriptionField.getText().toString();
        String date = "";
        String done = "";

        if (TextUtils.isEmpty(title)) {
            mTitleLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(category)) {
            mCategoryLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(summary)) {
            mSummaryLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(description)) {
            mDescriptionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);

        SimpleDateFormat formatter = new SimpleDateFormat("d'/'M'/'y");
        date = formatter.format(curDateTime);

        if (mDoneField.isChecked()) {
            done="Yes";
        } else {
            done="No";
        }

        Task task = new Task(title, category, summary, description, date, done);

        new AddEditTaskTask().execute(task);

    }

    private void showTasksScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showLawyer(Task task) {
        mTitleField.setText(task.getTitle());
        mCategoryField.setText(task.getCategory());
        mSummaryField.setText(task.getSummary());
        mDescriptionField.setText(task.getDescription());
        String tmpDone = task.getDone();
        //if (tmpDone == "Yes") {
        if (tmpDone.equals("Yes")) {
            mDoneField.setChecked(true);
        } else {
            mDoneField.setChecked(false);
        }
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetTaskByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mTasksDbHelper.getTaskById(mTaskId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Task(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditTaskTask extends AsyncTask<Task, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Task... tasks) {
            if (mTaskId != null) {
                return mTasksDbHelper.updateTask(tasks[0], mTaskId) > 0;

            } else {
                return mTasksDbHelper.saveTask(tasks[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showTasksScreen(result);
        }
    }
}
