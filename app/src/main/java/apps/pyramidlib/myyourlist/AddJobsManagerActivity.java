package apps.pyramidlib.myyourlist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import apps.pyramidlib.myyourlist.connection.ConnectionDetector;

/**
 * Created by ALIE on 14/01/2015.
 */
public class AddJobsManagerActivity extends Activity {
    Context c;
    String msg = "Add Jobs Project Owner:";
    private EditText kode_project = null;
    private ConnectionDetector connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(msg, "Add jobs view");
        String codeProject = getIntent().getExtras().getString("kode_project");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_add_jobs);

        kode_project = (EditText) findViewById(R.id.textKodeProject);
        kode_project.setText(codeProject);

        connection = new ConnectionDetector(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addJobsAction(View view) {
//        Log.d(msg, "create jobs actoin");
//        if(connection.isConnectToInternet()) {
//            AsyncTask createProjectAct = new CreateProjectAct().execute("");
//        }
//        else {
//            Toast.makeText(this, "You're is offline", Toast.LENGTH_LONG).show();
//        }
//
//        projectCode = (EditText) findViewById(R.id.textKodeProject);
//        projectName = (EditText) findViewById(R.id.textProjectName);
//        projectPlatform = (EditText) findViewById(R.id.textProjectPlatform);
//        projectAuthor = (EditText) findViewById(R.id.textProjectAuthor);
//        projectDescription = (EditText) findViewById(R.id.textProjectDescription);
    }
}
