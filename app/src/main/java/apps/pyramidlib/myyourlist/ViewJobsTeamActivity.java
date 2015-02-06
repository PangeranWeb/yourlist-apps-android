package apps.pyramidlib.myyourlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import apps.pyramidlib.myyourlist.connection.ServiceHandler;

/**
 * Created by ALIE on 17/01/2015.
 */
public class ViewJobsTeamActivity extends Activity{
    String msg = "View Jobs Team :";
    private EditText kode_project = null;
    ServiceHandler sHandler;

    List<Jobs> jobsList;
    TeamManagerAdapter teamManagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String codeProject = getIntent().getExtras().getString("kode_project");
        Log.d(msg, "View jobs");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_view_jobs);

        kode_project = (EditText) findViewById(R.id.textKodeProject);
        kode_project.setText(codeProject);

        jobsList = new ArrayList<Jobs>();

        String getJobsAction = "";
        try {
            getJobsAction = String.valueOf(new GetJobsAction().execute("").get());
            Log.d(msg, "jobs : "+ getJobsAction);
            JSONObject respondProject = new JSONObject(getJobsAction);
//            if(respondProject.optInt("status", 0) == 1){
//
//                JSONArray dataProject =  respondProject.getJSONArray("user");
//                for (int n = 0; n < dataProject.length(); n++) {
//
//                    JSONObject data = dataProject.getJSONObject(n);
//                    Jobs jobs = new Jobs();
//                    jobs.kode_project
//                    Team jo = new Team();
//                    team.id_tim= data.getString("id_user");
//                    team.nama_tim= data.getString("username");
//                    team.job_tim= "Developer";
//
//                    jobsList.add(team);
//                }
//
//                ListView listView = (ListView) findViewById(R.id.listView2);
//
//                teamManagerAdapter = new TeamManagerAdapter(this, jobsList);
//                listView.setAdapter(teamManagerAdapter);
//
//                listView.setOnItemClickListener(teamManagerAdapter);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    GetJobsAction getJobsAction;

    protected void onDestroy(){
        super.onDestroy();
        if(getJobsAction != null && getJobsAction.getStatus() == AsyncTask.Status.RUNNING) getJobsAction.cancel(true);
    }

    private void initializeComponents(){

    }

    private class GetJobsAction extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        public String respond;

        @Override
        protected void onPreExecute(){
            sHandler = new ServiceHandler();
        }

        @Override
        protected String doInBackground(String... params) {
            String respondBack = "";
            try {
                String respond= sHandler.makeServiceCall(Constants.URL_SERVER+"/api/project/get_jobs_by_project_team_get?key=" + Constants.User.USER_KEY +
                        "&kode_project="+kode_project.getText().toString()
                        ,ServiceHandler.GET);

                return respond;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
