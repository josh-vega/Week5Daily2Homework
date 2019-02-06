package com.example.week5daily2homework.model.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class StudentContentProvider extends ContentProvider {
    private static final int STUDENT_NAME = 100;
    private static final int STUDENT_NAME_ID = 101;
    private static final int STUDENT_GPA = 200;
    private static final int STUDENT_GPA_ID = 201;
    private static final int STUDENT_PHONE = 300;
    private static final int STUDENT_PHONE_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private StudentDatabaseHelper studentDatabaseHelper;

    @Override
    public boolean onCreate() {
        studentDatabaseHelper = new StudentDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = studentDatabaseHelper.getWritableDatabase();
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            case STUDENT_NAME:
                retCursor = db.query(
                        StudentProviderContract.StudentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case STUDENT_NAME_ID:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        StudentProviderContract.StudentEntry.TABLE_NAME,
                        projection,
                        StudentProviderContract.StudentEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case STUDENT_GPA:
                retCursor = db.query(
                        StudentProviderContract.StudentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case STUDENT_GPA_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        StudentProviderContract.StudentEntry.TABLE_NAME,
                        projection,
                        StudentProviderContract.StudentEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case STUDENT_PHONE:
                retCursor = db.query(
                        StudentProviderContract.StudentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case STUDENT_PHONE_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        StudentProviderContract.StudentEntry.TABLE_NAME,
                        projection,
                        StudentProviderContract.StudentEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set the notification URI for the cursor to the one passed into the function. This
        // causes the cursor to register a content observer to watch for changes that happen to
        // this URI and any of it's descendants. By descendants, we mean any URI that begins
        // with this path.
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(sUriMatcher.match(uri)){
            case STUDENT_NAME:
                return StudentProviderContract.StudentEntry.CONTENT_TYPE;
            case STUDENT_NAME_ID:
                return StudentProviderContract.StudentEntry.CONTENT_ITEM_TYPE;
            case STUDENT_GPA:
                return StudentProviderContract.StudentEntry.CONTENT_TYPE;
            case STUDENT_GPA_ID:
                return StudentProviderContract.StudentEntry.CONTENT_ITEM_TYPE;
            case STUDENT_PHONE:
                return StudentProviderContract.StudentEntry.CONTENT_TYPE;
            case STUDENT_PHONE_ID:
                return StudentProviderContract.StudentEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = studentDatabaseHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch(sUriMatcher.match(uri)){
            case STUDENT_NAME:
                _id = db.insert(StudentProviderContract.StudentEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  StudentProviderContract.StudentEntry.buildStudentUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case STUDENT_GPA:
                _id = db.insert(StudentProviderContract.StudentEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  StudentProviderContract.StudentEntry.buildStudentUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case STUDENT_PHONE:
                _id = db.insert(StudentProviderContract.StudentEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  StudentProviderContract.StudentEntry.buildStudentUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = studentDatabaseHelper.getWritableDatabase();
        int rows; // Number of rows effected

        switch(sUriMatcher.match(uri)){
            case STUDENT_NAME:
                rows = db.delete(StudentProviderContract.StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STUDENT_GPA:
                rows = db.delete(StudentProviderContract.StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STUDENT_PHONE:
                rows = db.delete(StudentProviderContract.StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because null could delete all rows:
        if(selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = studentDatabaseHelper.getWritableDatabase();
        int rows;

        switch(sUriMatcher.match(uri)){
            case STUDENT_NAME:
                rows = db.update(StudentProviderContract.StudentEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case STUDENT_GPA:
                rows = db.update(StudentProviderContract.StudentEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case STUDENT_PHONE:
                rows = db.update(StudentProviderContract.StudentEntry.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    public static UriMatcher buildUriMatcher(){
        String content = StudentProviderContract.CONTENT_AUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, StudentProviderContract.PATH_NAME, STUDENT_NAME);
        matcher.addURI(content, StudentProviderContract.PATH_NAME + "/#", STUDENT_NAME_ID);

        return matcher;
    }
}
