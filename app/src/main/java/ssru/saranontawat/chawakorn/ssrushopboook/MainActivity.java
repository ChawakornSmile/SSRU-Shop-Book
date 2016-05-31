package ssru.saranontawat.chawakorn.ssrushopboook;

import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private MyManage myManage;
    private static final String urlJSON = "http://swiftcodingthai.com/ssru/get_user_EIK.php";
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] loginString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);




        myManage = new MyManage(MainActivity.this);

        //test add value to sqlite
        //myManage.addNewUser("Name","sur","user","pass","money");

        //Delete All userTABLE
        deletealluserTABLE();

        synJSONtoSQLite();



    }   //method

    @Override
    protected void onRestart() {
        super.onRestart();

        deletealluserTABLE();
        synJSONtoSQLite();

    }

    public  void clickSignIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // check space
        if (userString.equals("") || passwordString.equals("")) {
            Myalert myalert = new Myalert();
            myalert.myDialog(this, "Have Space", "Please Try Again");
        } else {
            checkUserAnPassword();

        }


    }   //clicksighin

    private void checkUserAnPassword() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,MODE_PRIVATE,null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'", null);
            cursor.moveToFirst();

            loginString = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                loginString[i] = cursor.getString(i);

            } //for
            cursor.close();
            // check password
            if (passwordString.equals(loginString[4])) {
                // password true
                Toast.makeText(this, "Welcome WTF " + " "+loginString[1]+ " " + loginString[2],
                        Toast.LENGTH_SHORT).show();
            } else {
                //password false
                Myalert myalert = new Myalert();
                myalert.myDialog(this, "Fucking Stupid ", "PassWord False Try Again");
            }

        } catch (Exception e) {

            Myalert myalert = new Myalert();
            myalert.myDialog(this,"Fucking Stupid","No  " + userString + " In Database");
        }

    } //check user and password

    private void synJSONtoSQLite() {

        ConnectedUserTABLE connectedUserTABLE = new ConnectedUserTABLE();
        connectedUserTABLE.execute();
    }

    public class ConnectedUserTABLE extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();



            } catch (Exception e) {
                Log.d("31May", "My ERROR ==> " + e.toString());
                return null;
            }

        } // doInback

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Log.d("31May", "JSON ==> " + s);

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strName = jsonObject.getString(MyManage.coLumn_name);
                    String strSurname = jsonObject.getString(MyManage.coLumn_surname);
                    String strUser = jsonObject.getString(MyManage.coLumn_user);
                    String strPassword = jsonObject.getString(MyManage.coLumn_password);
                    String strMoney = jsonObject.getString(MyManage.coLumn_money);

                    myManage.addNewUser(strName, strSurname, strUser, strPassword, strMoney);


                }   //for



            } catch (Exception e) {
                e.printStackTrace();
            }

        }   //onPose
    }   // Connect Class

    private void deletealluserTABLE() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,MODE_PRIVATE,null);
        sqLiteDatabase.delete(MyManage.user_TABLE, null, null);
    }


    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}   //main class
