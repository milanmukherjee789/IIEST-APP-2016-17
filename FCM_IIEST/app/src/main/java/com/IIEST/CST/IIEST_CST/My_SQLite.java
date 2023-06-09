package com.IIEST.CST.IIEST_CST;

/**
 * Created by CST on 29/12/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by CST on 27/12/16.
 */

class My_SQLite extends SQLiteOpenHelper {
    private final String TABLE_NAME=URL_Strings.table_name;
    private final String Email="Email";
    private final String RollNo="Rollno";
    private final String Reg_status = "Reg_status";
    public My_SQLite(Context context) {
        super(context, URL_Strings.DB_name, null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String create_table="CREATE TABLE "+TABLE_NAME+"("+Email+" TEXT,"+RollNo+" TEXT,"+Reg_status+" TEXT"+")";
            sqLiteDatabase.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            // Create tables again
            onCreate(sqLiteDatabase);

    }
    protected String[] read(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+";",null);
        cursor.moveToFirst();
        String[] res=new String[3];
        res[0]=res[1]=res[2]="";
        try {
            res[0] = cursor.getString(0);
            res[1] = cursor.getString(1);
            res[2] =  cursor.getString(2);
            Log.d("SUCCESSFULLY READ DB :", res[0]+" ----- "+res[1]+" ---- "+res[2]);
        }catch (Exception e){
            //insert("","","N");
        }
        return res;
    }

    protected void insert(String a, String b,String c) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Email,a);
        values.put(RollNo,b);
        values.put(Reg_status,c);
        db.insert(TABLE_NAME,null,values);
        Log.e("insert => ",a+" "+c);
        db.close();
    }

    protected  void update(String a,String b,String c){
        ContentValues values=new ContentValues();
        values.put(Email,a);
        values.put(RollNo,b);
        values.put(Reg_status,c);
        SQLiteDatabase db=this.getWritableDatabase();
        db.update(TABLE_NAME,values,null,null);
        db.close();
        Log.d("SUCCESFULLY UPDATED ",a+" "+b+" "+c);
    }
}
