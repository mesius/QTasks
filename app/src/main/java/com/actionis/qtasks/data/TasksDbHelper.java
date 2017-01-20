package com.actionis.qtasks.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.actionis.qtasks.data.TasksContract.TaskEntry;

/**
 * Created by fm on 12/01/17.
 */

public class TasksDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tasks.db";

    public TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TaskEntry.TABLE_NAME + " ("
                + TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TaskEntry.ID + " TEXT NOT NULL,"
                + TaskEntry.TITLE + " TEXT NOT NULL,"
                + TaskEntry.CATEGORY + " TEXT NOT NULL,"
                + TaskEntry.SUMMARY + " TEXT NOT NULL,"
                + TaskEntry.DESCRIPTION + " TEXT NOT NULL,"
                + TaskEntry.DATE + " TEXT,"
                + TaskEntry.DONE + " TEXT,"
                + "UNIQUE (" + TaskEntry.ID + "))");

        // Insertar datos ficticios para prueba inicial
        //mockData(sqLiteDatabase);

    }


    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockTask(sqLiteDatabase, new Task("Carlos Perez", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "carlos_perez.jpg", "Yes"));
        mockTask(sqLiteDatabase, new Task("Tom Bonz", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "tom_bonz.jpg", "No"));
    }

    public long mockTask(SQLiteDatabase db, Task task) {
        return db.insert(
                TaskEntry.TABLE_NAME,
                null,
                task.toContentValues());
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long saveTask(Task task) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                TaskEntry.TABLE_NAME,
                null,
                task.toContentValues());

    }

    public Cursor getAllTasks() {
        return getReadableDatabase()
                .query(
                        TaskEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getDoneTasks() {
        return getReadableDatabase()
                .query(
                        TaskEntry.TABLE_NAME,
                        null,
                        TaskEntry.DONE + " = 'Yes'",
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getPendingTasks() {
        return getReadableDatabase()
                .query(
                        TaskEntry.TABLE_NAME,
                        null,
                        TaskEntry.DONE + " = 'No'",
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getTaskById(String taskId) {
        Cursor c = getReadableDatabase().query(
                TaskEntry.TABLE_NAME,
                null,
                TaskEntry.ID + " LIKE ?",
                new String[]{taskId},
                null,
                null,
                null);
        return c;
    }

    public int deleteTask(String taskId) {
        return getWritableDatabase().delete(
                TaskEntry.TABLE_NAME,
                TaskEntry.ID + " LIKE ?",
                new String[]{taskId});
    }

    public int updateTask(Task task, String taskId) {
        return getWritableDatabase().update(
                TaskEntry.TABLE_NAME,
                task.toContentValues(),
                TaskEntry.ID + " LIKE ?",
                new String[]{taskId}
        );
    }

}
