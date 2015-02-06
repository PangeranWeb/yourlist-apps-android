package apps.pyramidlib.myyourlist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by ALIE on 17/01/2015.
 */
public class SingleJobsTeamManagerActivity extends Activity{
    String msg = "View Jobs Team :";
    private EditText kode_project = null;
    private EditText nama_tim = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String codeProject = getIntent().getExtras().getString("kode_project");
        String namaTeam = getIntent().getExtras().getString("nama");
        Log.d(msg, "View team list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_single_team_jobs);

        kode_project = (EditText) findViewById(R.id.textKodeProject);
        kode_project.setText(codeProject);

        nama_tim = (EditText) findViewById(R.id.namaTeam);
        nama_tim.setText(namaTeam);
    }
}
