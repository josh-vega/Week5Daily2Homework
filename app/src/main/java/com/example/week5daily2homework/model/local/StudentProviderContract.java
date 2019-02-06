package com.example.week5daily2homework.model.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class StudentProviderContract {
    public static final String CONTENT_AUTHORITY = "com.example.week5daily2homework";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NAME = "name";
    public static final String PATH_GPA = "gpa";
    public static final String PATH_PHONE = "phone";

    public static final class StudentEntry implements BaseColumns {
        //Content URI represents the base location for the table\
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NAME).build();

        //These are special types prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_NAME;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_NAME;

        //Define the table schema
        public static final String TABLE_NAME = "student_gpa_table";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_GPA = "gpa";
        public static final String FIELD_PHONE = "phone";

        //Define a function to build a URI to find a specific student by it's identifier
        public static Uri buildStudentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
