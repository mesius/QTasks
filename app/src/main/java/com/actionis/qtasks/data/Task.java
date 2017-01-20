package com.actionis.qtasks.data;

/**
 * Created by fm on 12/01/17.
 */

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

import static com.actionis.qtasks.data.TasksContract.TaskEntry;

/**
 * Entidad "task"
 */

public class Task {

    private String id;
    private String title;
    private String category;
    private String summary;
    private String description;
    private String date;
    private String done;

    public Task(String title,
                  String category, String summary,
                  String description, String date, String done) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.category = category;
        this.summary = summary;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public Task(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(TaskEntry.ID));
        title = cursor.getString(cursor.getColumnIndex(TaskEntry.TITLE));
        category = cursor.getString(cursor.getColumnIndex(TaskEntry.CATEGORY));
        summary = cursor.getString(cursor.getColumnIndex(TaskEntry.SUMMARY));
        description = cursor.getString(cursor.getColumnIndex(TaskEntry.DESCRIPTION));
        date = cursor.getString(cursor.getColumnIndex(TaskEntry.DATE));
        done = cursor.getString(cursor.getColumnIndex(TaskEntry.DONE));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.ID, id);
        values.put(TaskEntry.TITLE, title);
        values.put(TaskEntry.CATEGORY, category);
        values.put(TaskEntry.SUMMARY, summary);
        values.put(TaskEntry.DESCRIPTION, description);
        values.put(TaskEntry.DATE, date);
        values.put(TaskEntry.DONE, done);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getDone() {
        return done;
    }


}
