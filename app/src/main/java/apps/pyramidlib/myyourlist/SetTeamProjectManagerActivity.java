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
import android.widget.Spinner;
import android.widget.Toast;

import apps.pyramidlib.myyourlist.connection.ConnectionDetector;
import apps.pyramidlib.myyourlist.connection.ServiceHandler;

/**
 * Created by ALIE on 13/01/2015.
 */
public class SetTeamProjectManagerActivity extends Activity {
    String msg = "Home:";
    Context c;
    ServiceHandler sHandler;
    private boolean isSuccess;
    private ConnectionDetector connection;
    private EditText kodeProject = null;
    private EditText idUser = null;
    private String[] arraySpinner;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String codeProject = getIntent().getExtras().getString("kode_project");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_set_new_team);

        c = this;

        kodeProject = (EditText) findViewById(R.id.textKodeProject);
//        idUser = (EditText) findViewById(R.id.spinner);
        kodeProject.setText(codeProject);

        this.arraySpinner = new String[]{
                "afif", "rio", "adhe", "amirul"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        connection = new ConnectionDetector(this);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setSaveTeam(View view) {
        progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Please waiting....");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }


}