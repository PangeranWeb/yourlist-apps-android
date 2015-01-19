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
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import apps.pyramidlib.myyourlist.connection.ConnectionDetector;
import apps.pyramidlib.myyourlist.connection.ServiceHandler;

public class SingleProjectManagerActivity extends Activity {
    Context c;
    ServiceHandler sHandler;
    String msg = "Single Project Owner:";
    private ConnectionDetector connection;

    private EditText kode_project = null;
    private EditText nama_project = null;
    private EditText platfrom_project = null;
    private EditText author_project = null;
    private EditText working_project = null;
    private EditText desc_project = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String codeProject = getIntent().getExtras().getString("kode_project");
        Log.d(msg, "Single project view : " + codeProject);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_single_project);

        c = this;

        String singleProjectAct = "";
        try {
            singleProjectAct = new SingleProjectAct(codeProject).execute("").get();

            JSONObject respondProject = new JSONObject(singleProjectAct);
            if(respondProject.optInt("status", 0) == 1){
                Log.d(msg, "kode  : " + respondProject.getJSONObject("data").getString("kode_project"));

                kode_project = (EditText) findViewById(R.id.textKodeProject);
                kode_project.setText(respondProject.getJSONObject("data").getString("kode_project"));

                nama_project = (EditText) findViewById(R.id.textProjectName);
                nama_project.setText(respondProject.getJSONObject("data").getString("project_name"));

                platfrom_project = (EditText) findViewById(R.id.textProjectPlatform);
                platfrom_project.setText(respondProject.getJSONObject("data").getString("project_platform"));

                author_project = (EditText) findViewById(R.id.textProjectAuthor);
                author_project.setText(respondProject.getJSONObject("data").getString("project_author"));

                desc_project = (EditText) findViewById(R.id.textProjectDescription);
                desc_project.setText(respondProject.getJSONObject("data").getString("description"));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void createJobs(View view) {
        Log.d(msg, "create jobs");
        startActivity(new Intent(
             getApplicationContext(),
             AddJobsManagerActivity.class)
                 .putExtra("kode_project", kode_project.getText()
                 .toString()
             )
        );
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void viewJobs(View view) {
        Log.d(msg, "view jobs");
        startActivity(new Intent(
             getApplicationContext(),
             ViewJobsManagerActivity.class)
                 .putExtra("kode_project", kode_project.getText()
                 .toString()
             )
        );
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void viewTeam(View view) {
        Log.d(msg, "view team");
        startActivity(new Intent(
                        getApplicationContext(),
                        ViewTeamManagerActivity.class)
                        .putExtra("kode_project", kode_project.getText()
                                        .toString()
                        )
        );
    }

    SingleProjectAct singleProjectAct;

    protected void onDestroy(){
        super.onDestroy();
    }

    private void initializeComponents(){
    }

    private class SingleProjectAct extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        public String respond;
        private String kodeProject;

        public SingleProjectAct(String codeProject) {
            this.kodeProject = codeProject;
        }

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
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Constants.User.USER_KEY = "8895a2e62346fa0aa9fc7f47ba3fd5efd6616a86";
                String respond= sHandler.makeServiceCall(Constants.URL_SERVER+"/api/project/get_single_project?key=" + Constants.User.USER_KEY +
                        "&kode_project=" + this.kodeProject,ServiceHandler.GET);

                return respond;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String a) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }

    }
}