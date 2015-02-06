package apps.pyramidlib.myyourlist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
 * Created by ALIE on 14/01/2015.
 */
public class ViewTeamManagerActivity extends Activity {
    Context c;
    String msg = "View Team Project Manager :";
    ServiceHandler sHandler;

    List<Team> teamList;
    TeamManagerAdapter teamManagerAdapter;

    private EditText kode_project = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String codeProject = getIntent().getExtras().getString("kode_project");
        Log.d(msg, "View team list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_set_team);

        kode_project = (EditText) findViewById(R.id.textKodeProject);
        kode_project.setText(codeProject);

        teamList = new ArrayList<Team>();

        String getTeamAct = "";
        try {
            getTeamAct = String.valueOf(new GetTeamAction().execute("").get());
            JSONObject respondProject = new JSONObject(getTeamAct);
            if(respondProject.optInt("status", 0) == 1){

                JSONArray dataProject =  respondProject.getJSONArray("user");
                for (int n = 0; n < dataProject.length(); n++) {

                    JSONObject data = dataProject.getJSONObject(n);
                    Team team = new Team();
                    team.id_tim= data.getString("id_user");
                    team.nama_tim= data.getString("username");
                    team.job_tim= "Developer";

                    teamList.add(team);
                }

                ListView listView = (ListView) findViewById(R.id.listView2);

                teamManagerAdapter = new TeamManagerAdapter(this, teamList);
                listView.setAdapter(teamManagerAdapter);

                listView.setOnItemClickListener(teamManagerAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setTeamJobsAction(View view) {
        Log.d(msg, "view all team no in project");
        startActivity(new Intent(
                        getApplicationContext(),
                        SetTeamProjectManagerActivity.class)
                        .putExtra("kode_project", kode_project.getText()
                                        .toString()
                        )
        );
    }

    GetTeamAction getTeamAction;

    protected void onDestroy(){
        super.onDestroy();
        if(getTeamAction != null && getTeamAction.getStatus() == AsyncTask.Status.RUNNING) getTeamAction.cancel(true);
    }

    private void initializeComponents(){

    }

    private class GetTeamAction extends AsyncTask<String, String, String> {
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
                String respond= sHandler.makeServiceCall(Constants.URL_SERVER+"/api/user/get_all_team?key=" + Constants.User.USER_KEY,ServiceHandler.GET);

                return respond;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
