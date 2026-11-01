package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Admission.db";
    public static final int DATABASE_VERSION = 5;

    // Students Table
    public static final String TABLE_STUDENTS = "students";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE = "phone";
    public static final String COL_PASSWORD = "password";
    public static final String COL_GENDER = "gender";
    public static final String COL_COURSE = "course";
    public static final String COL_FACILITIES = "facilities";
    public static final String COL_DEPARTMENT = "department";
    public static final String COL_IMAGE_URI = "image_uri";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStudentsTable = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_GENDER + " TEXT, " +
                COL_COURSE + " TEXT, " +
                COL_FACILITIES + " TEXT, " +
                COL_DEPARTMENT + " TEXT, " +
                COL_IMAGE_URI + " TEXT)";
        db.execSQL(createStudentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public long insertStudent(String username, String email, String phone, String password, String gender, String course, String facilities, String department, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username != null ? username : "");
        values.put(COL_EMAIL, email != null ? email : "");
        values.put(COL_PHONE, phone != null ? phone : "");
        values.put(COL_PASSWORD, password != null ? password : "");
        values.put(COL_GENDER, gender != null ? gender : "Male");
        values.put(COL_COURSE, course != null ? course : "BS Computer Science (BSCS)");
        values.put(COL_FACILITIES, facilities != null ? facilities : "General Tech");
        values.put(COL_DEPARTMENT, department != null ? department : "Department of IT");
        values.put(COL_IMAGE_URI, imageUri != null ? imageUri : "");

        try {
            return db.insert(TABLE_STUDENTS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_STUDENTS + " ORDER BY " + COL_ID + " DESC", null);
    }

    public boolean updateData(String id, String username, String email, String phone, String password, String gender, String course, String facilities, String department, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username != null ? username : "");
        values.put(COL_EMAIL, email != null ? email : "");
        values.put(COL_PHONE, phone != null ? phone : "");
        values.put(COL_PASSWORD, password != null ? password : "");
        values.put(COL_GENDER, gender != null ? gender : "Male");
        values.put(COL_COURSE, course != null ? course : "BS Computer Science (BSCS)");
        values.put(COL_FACILITIES, facilities != null ? facilities : "General Tech");
        values.put(COL_DEPARTMENT, department != null ? department : "Department of IT");
        values.put(COL_IMAGE_URI, imageUri != null ? imageUri : "");

        try {
            int result = db.update(TABLE_STUDENTS, values, COL_ID + "=?", new String[]{id});
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int result = db.delete(TABLE_STUDENTS, COL_ID + "=?", new String[]{id});
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getRecordsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_STUDENTS, null)) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
