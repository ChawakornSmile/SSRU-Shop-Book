package ssru.saranontawat.chawakorn.ssrushopboook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Pc on 31/5/2559.
 */
public class MyManage {

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_TABLE = "userTABLE";
    public static final String coLumn_id = "_id";
    public static final String coLumn_name = "Name";
    public static final String coLumn_surname = "Surname";
    public static final String coLumn_user = "User";
    public static final String coLumn_password = "Password";
    public static final String coLumn_money = "Money";

    public MyManage(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }   //constructor

    public long addNewUser(String strName,
                           String strSurname,
                           String strUser,
                           String strPassword,
                           String strMoney) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(coLumn_name, strName);
        contentValues.put(coLumn_surname,strSurname);
        contentValues.put(coLumn_user ,strUser);
        contentValues.put(coLumn_password,strPassword);
        contentValues.put(coLumn_money,strMoney);


        return sqLiteDatabase.insert(user_TABLE, null, contentValues);
    }




}   //main class
