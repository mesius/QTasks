package com.actionis.qtasks.data;

/**
 * Created by fm on 12/01/17.
 */

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para tasks
 */

public class TasksContract {

    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME ="task";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String CATEGORY = "category";
        public static final String SUMMARY = "summary";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String DONE = "done";
    }

}
