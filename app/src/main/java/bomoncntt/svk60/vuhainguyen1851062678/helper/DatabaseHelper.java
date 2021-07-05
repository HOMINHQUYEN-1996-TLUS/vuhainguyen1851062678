package bomoncntt.svk60.vuhainguyen1851062678.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import bomoncntt.svk60.vuhainguyen1851062678.model.student;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_name = "Student.db";
    public static final String Table_name = "student_table";
    public static final String col_stdid = "stdid";
    public static final String col_fullname = "fullname";
    public static final String col_sex = "sex";
    public static final String col_grade = "grade";
    public static final String col_image = "stdimage";
    public static final String Table_user = "Login_table";


    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_name + " (stdid TEXT primary key,fullname TEXT, sex TEXT,grade TEXT,stdimage TEXT)");
        db.execSQL("create table " + Table_user + " (username TEXT primary key,password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Table_name);
        db.execSQL("drop table if exists " + Table_user);

        onCreate(db);//tạo lại
    }
    
    public boolean insertData(String stdid, String fullname, String sex, String grade, String stdimage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_stdid, stdid);
        cv.put(col_fullname, fullname);
        cv.put(col_sex, sex);
        cv.put(col_grade, grade);
        cv.put(col_image, stdimage);
        Long result = db.insert(Table_name, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor showData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Table_name, null);
        return cursor;

    }

    public Integer delete(String stdid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_name, "stdid = ?", new String[]{stdid});
    }

    public boolean update(String stdid, String fullname, String sex, String grade, String stdimage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_stdid, stdid);
        cv.put(col_fullname, fullname);
        cv.put(col_sex, sex);
        cv.put(col_grade, grade);
        cv.put(col_image, stdimage);

        int result = db.update(Table_name, cv, "stdid = ?", new String[]{stdid});
        if (result == -1) {
            return false;//insert không thành công
        } else {
            return true;
        }
    }
    public boolean checkid(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cs=db.rawQuery("select * from " + Table_name+" where stdid = ?", new String[]{id});
        if(cs.getCount()==0) return true;
        else return false;
    }
    //dbhelper login
    public boolean insertUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        long result = db.insert(Table_user, null, cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" Select * from " + Table_user + " where username = ? ", new String[]{username});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkUserNamePassWord(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" Select * from "+ Table_user + " where username = ? and password = ? ", new String[]{username, password});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else{
            return false;
        }
    }
}
