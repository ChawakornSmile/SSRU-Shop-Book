package ssru.saranontawat.chawakorn.ssrushopboook;

import android.app.DownloadManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


public class MainActivity extends ActionBarActivity {

    private MyManage myManage;
    private static final String urlJSON = "http://swiftcodingthai.com/ssru/get_user_EIK.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myManage = new MyManage(MainActivity.this);

        //test add value to sqlite
        //myManage.addNewUser("Name","sur","user","pass","money");

        //Delete All userTABLE
        deletealluserTABLE();

        synJSONtoSQLite();



    }   //method

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
