package apps.pyramidlib.myyourlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import apps.pyramidlib.myyourlist.connection.ServiceHandler;

/**
 * Created by ALIE on 14/01/2015.
 */
public class ViewJobsManagerActivity extends Activity {
    Context c;
    String msg = "View Jobs Project Owner:";
    ServiceHandler sHandler;
    private EditText kode_project = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String codeProject = getIntent().getExtras().getString("kode_project");
        Log.d(msg, "View jobs list :" +codeProject);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_view_jobs);

        kode_project = (EditText) findViewById(R.id.textKodeProject);
        kode_project.setText(codeProject);

        String getJobsAct = "";
//        try {

//            getJobsAct = String.valueOf(new GetJobsAction().execute("").get());
//            Log.d(msg, "View Jobs : " +getJobsAct);
//            JSONObject respondProject = new JSONObject(getTeamAct);
//            if(respondProject.optInt("status", 0) == 1){
//
//                JSONArray dataProject =  respondProject.getJSONArray("user");
//                for (int n = 0; n < dataProject.length(); n++) {
//
//                    JSONObject data = dataProject.getJSONObject(n);
//                    Team team = new Team();
//                    team.id_tim= data.getString("id_user");
//                    team.nama_tim= data.getString("username");
//                    team.job_tim= "Developer";
//
//                    teamList.add(team);
//                }
//
//                ListView listView = (ListView) findViewById(R.id.listView2);
//
//                teamManagerAdapter = new TeamManagerAdapter(this, teamList);
//                listView.setAdapter(teamManagerAdapter);
//
//                listView.setOnItemClickListener(teamManagerAdapter);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
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
                String respond= sHandler.makeServiceCall(Constants.URL_SERVER+"/api/project/get_jobs_project?key=" + Constants.User.USER_KEY+"&kode_project=" + kode_project,ServiceHandler.GET);

                return respond;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
