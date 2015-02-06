package apps.pyramidlib.myyourlist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import apps.pyramidlib.myyourlist.connection.ConnectionDetector;
import apps.pyramidlib.myyourlist.connection.ServiceHandler;

/**
 * Created by ALIE on 13/01/2015.
 */
public class SetManagerProjectOwnerActivity extends Activity {
    String msg = "Home:";
    Context c;
    ServiceHandler sHandler;
    private boolean isSuccess;
    private ConnectionDetector connection;
    private EditText kodeProject = null;
    private EditText idUser = null;
    private String[] arraySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String codeProject = getIntent().getExtras().getString("kode_project");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_set_manager);

        c = this;

        kodeProject = (EditText) findViewById(R.id.textKodeProject);
//        idUser = (EditText) findViewById(R.id.spinner);
        kodeProject.setText(codeProject);

        this.arraySpinner = new String[] {
                "nia", "avitaunaya", "andylib"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        connection = new ConnectionDetector(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setProjectManager(View view) {
        if(connection.isConnectToInternet()){
            if(setManagerAct != null && setManagerAct.getStatus() == AsyncTask.Status.RUNNING) setManagerAct.cancel(true);
            AsyncTask setManagerAct = new SetManagerAct().execute("");
        }
        else
            Toast.makeText(this, "You're is offline", Toast.LENGTH_LONG).show();
    }

    SetManagerAct setManagerAct;

    protected void onDestroy(){
        super.onDestroy();
        if(setManagerAct != null && setManagerAct.getStatus() == AsyncTask.Status.RUNNING) setManagerAct.cancel(true);
    }

    private void initializeComponents()
    {
    }

    private class SetManagerAct extends AsyncTask<String, String, Void> {
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
            isSuccess=false;
        }

        @Override
        protected Void doInBackground(String... params) {
            isSuccess = true;
//            String respondHttpRequest = sHandler.makeServiceCall(Constants.URL_SERVER+"/api/auth/login?username="+
//                    username.getText().toString()+"&password="+
//                    password.getText().toString(), ServiceHandler.GET);
//
//            try {
//                JSONObject respondUser = new JSONObject(respondHttpRequest);
//                if(respondUser.optInt("status", 0) == 1){
//                    isExist=true;
//                    Constants.User.USER_ID = respondUser.getJSONObject("data").getString("id_user");
//                    Constants.User.USER_ID_LEVEL = respondUser.getJSONObject("data").getInt("id_level");
//                    Constants.User.USER_NAME = respondUser.getJSONObject("data").getString("username");
//                    Constants.User.USER_KEY = respondUser.getJSONObject("data").getString("key");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return null;
        }

        protected void onPostExecute(Void a) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(isSuccess){
                startActivity(new Intent(
                                c,
                                SingleProjectOwnerActivity.class)
                                .putExtra("kode_project",kodeProject.getText().toString())
                );
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Set Manager unsuccessful!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
