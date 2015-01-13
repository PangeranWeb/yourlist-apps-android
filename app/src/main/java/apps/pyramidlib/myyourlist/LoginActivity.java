package apps.pyramidlib.myyourlist;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import apps.pyramidlib.myyourlist.connection.ConnectionDetector;
import apps.pyramidlib.myyourlist.connection.ServiceHandler;
import apps.pyramidlib.myyourlist.Constants;

public class LoginActivity extends ActionBarActivity {
    private EditText username = null;
    private EditText password = null;
    private Button login;
    Context c;
    ServiceHandler sHandler;
    private boolean isExist;
    private ConnectionDetector connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        c = this;

        initializeComponents();
        if(!Constants.URL_SERVER.matches("http://.*"))
            Constants.URL_SERVER = "http://"+Constants.URL_SERVER;

        username = (EditText) findViewById(R.id.textUsername);
        password = (EditText) findViewById(R.id.textPassword);
        login = (Button) findViewById(R.id.buttonSubmitLogin);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loginAction(View view) {
        if(connection.isConnectToInternet()){
            if(loginAct != null && loginAct.getStatus() == AsyncTask.Status.RUNNING) loginAct.cancel(true);
            AsyncTask loginAct = new LoginAction().execute("");
        }
        else
            Toast.makeText(this, "You're is offline", Toast.LENGTH_LONG).show();
    }

    LoginAction loginAct;

    protected void onDestroy(){
        super.onDestroy();
        if(loginAct != null && loginAct.getStatus() == AsyncTask.Status.RUNNING) loginAct.cancel(true);
    }

    private void initializeComponents(){
        username = (EditText) findViewById(R.id.textUsername);
        password = (EditText) findViewById(R.id.textPassword);
        connection = new ConnectionDetector(this);
    }

    private class LoginAction extends AsyncTask<String, String, Void> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = new ProgressDialog(c);
            progressDialog.setMessage("Please waiting....");
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);
                }
            });
            progressDialog.setCancelable(true);
            progressDialog.show();

            sHandler = new ServiceHandler();
            isExist=false;
        }

        @Override
        protected Void doInBackground(String... params) {
            String respondHttpRequest = sHandler.makeServiceCall(Constants.URL_SERVER+"/api/auth/login?username="+
                    username.getText().toString()+"&password="+
                    password.getText().toString(), ServiceHandler.GET);

            try {
                JSONObject respondUser = new JSONObject(respondHttpRequest);
                if(respondUser.optInt("status", 0) == 1){
                    isExist=true;
                    Constants.User.USER_ID = respondUser.getJSONObject("data").getString("id_user");
                    Constants.User.USER_ID_LEVEL = respondUser.getJSONObject("data").getInt("id_level");
                    Constants.User.USER_NAME = respondUser.getJSONObject("data").getString("username");
                    Constants.User.USER_KEY = respondUser.getJSONObject("data").getString("key");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void a) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(isExist){
                if (Constants.User.USER_ID_LEVEL == 1) {
                    startActivity(new Intent(getApplicationContext(), HomeOwnerActivity.class));
                } else if (Constants.User.USER_ID_LEVEL == 2) {
                    Toast.makeText(getApplicationContext(), "Manager", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HomeManagerActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Team", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HomeTeamActivity.class));
                }
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Wrong Credential!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
