package apps.pyramidlib.myyourlist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import apps.pyramidlib.myyourlist.connection.ConnectionDetector;
import apps.pyramidlib.myyourlist.connection.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddProjectActivity extends Activity {
    Context c;
    String msg = "Android : ";
    private ConnectionDetector connection;
    ServiceHandler sHandler;
    private boolean isSuccess;

    private EditText projectCode = null;
    private EditText projectName = null;
    private EditText projectPlatform = null;
    private EditText projectAuthor = null;
    private EditText projectDescription = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(msg, "Muncul tampilan create project");
        setContentView(R.layout.owner_create_single_project);

        c = this;

        connection = new ConnectionDetector(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void createProjectAction(View view) {

        if(connection.isConnectToInternet()) {
            AsyncTask createProjectAct = new CreateProjectAct().execute("");
        }
        else {
            Toast.makeText(this, "You're is offline", Toast.LENGTH_LONG).show();
        }

        projectCode = (EditText) findViewById(R.id.textKodeProject);
        projectName = (EditText) findViewById(R.id.textProjectName);
        projectPlatform = (EditText) findViewById(R.id.textProjectPlatform);
        projectAuthor = (EditText) findViewById(R.id.textProjectAuthor);
        projectDescription = (EditText) findViewById(R.id.textProjectDescription);
    }

    CreateProjectAct createProjectAct;

    protected void onDestroy(){
        super.onDestroy();
        if(createProjectAct != null && createProjectAct.getStatus() == Status.RUNNING) createProjectAct.cancel(true);
    }

    private void initializeComponents(){
        projectCode = (EditText) findViewById(R.id.textKodeProject);
        projectName = (EditText) findViewById(R.id.textProjectName);
        projectPlatform = (EditText) findViewById(R.id.textProjectPlatform);
        projectAuthor = (EditText) findViewById(R.id.textProjectAuthor);
        projectDescription = (EditText) findViewById(R.id.textProjectDescription);

        connection = new ConnectionDetector(this);
    }

    private class CreateProjectAct extends AsyncTask<String, String, Void> {

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
            List<NameValuePair> param =  new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("key",  Constants.User.USER_KEY));
            param.add(new BasicNameValuePair("kode_project",  projectCode.getText().toString()));
            param.add(new BasicNameValuePair("project_name",  projectName.getText().toString()));
            param.add(new BasicNameValuePair("project_author",  projectAuthor.getText().toString()));
            param.add(new BasicNameValuePair("project_platform",  projectPlatform.getText().toString()));
            param.add(new BasicNameValuePair("description",  projectDescription.getText().toString()));

            String respondHttpRequest = sHandler.makeServiceCall(Constants.URL_SERVER+"/api/project/create_project", ServiceHandler.POST, param);

            try {
                JSONObject respondUser = new JSONObject(respondHttpRequest);
                if(respondUser.optInt("status", 0) == 1){
                    isSuccess = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void a) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(isSuccess){
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), HomeOwnerActivity.class));
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();
            }
        }

        private void finish() {
        }

    }
}
