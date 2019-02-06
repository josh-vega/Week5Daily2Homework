package com.example.week5daily2homework.model.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class StudentDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "student_gpa_table";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_GPA = "gpa";
    private static final String FIELD_PHONE = "phone";
    public StudentDatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + FIELD_NAME + " TEXT PRIMARY KEY, "
                + FIELD_GPA + " TEXT, "
                + FIELD_PHONE + " TEXT);";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public ArrayList<Student> getAllStudents(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query ="SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            ArrayList<Student> arrayList = new ArrayList<>();
            do {
                String name = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
                String gpa = cursor.getString(cursor.getColumnIndex(FIELD_GPA));
                String phone = cursor.getString(cursor.getColumnIndex(FIELD_PHONE));
                arrayList.add(new Student(name, gpa, phone));
            } while(cursor.moveToNext());
            return arrayList;
        } else {
            return null;
        }
    }

    public Student getSingleStudentByName(String passedName){
        Student returnStudent = null;
        if(passedName != null && !passedName.isEmpty()) {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME
                    + " WHERE " + FIELD_NAME + " = \"" + passedName + "\"";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
                String gpa = cursor.getString(cursor.getColumnIndex(FIELD_GPA));
                String phone = cursor.getString(cursor.getColumnIndex(FIELD_PHONE));
                returnStudent = new Student(name, gpa, phone);
            }
            cursor.close();
        }
        return returnStudent;
    }

    public void insertNewStudent(Student passedStudent){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(passedStudent != null){
            String name = passedStudent.getStudentName();
            String gpa = passedStudent.getStudentGPA();
            String phone = passedStudent.getStudentPhone();

            contentValues.put(FIELD_NAME, name);
            contentValues.put(FIELD_GPA, gpa);
            contentValues.put(FIELD_PHONE, phone);

            database.insert(TABLE_NAME, null, contentValues);
        }
    }

    public int deleteStudent(String passedName){
        String whereClause = FIELD_NAME + " = \"" + passedName + "\"";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, whereClause, null);
    }

    public int updateStudent(Student passedStudent){
        if(passedStudent != null) {
            String whereClause = FIELD_NAME + " = \"" + passedStudent.getStudentName() + "\"";
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FIELD_NAME, passedStudent.getStudentName());
            contentValues.put(FIELD_GPA, passedStudent.getStudentGPA());
            contentValues.put(FIELD_PHONE, passedStudent.getStudentPhone());
            return sqLiteDatabase.update(TABLE_NAME, contentValues, whereClause, null);
        }
        return 0;
    }
}
