package apps.pyramidlib.myyourlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import apps.pyramidlib.myyourlist.connection.ServiceHandler;

/**
 * Created by ALIE on 13/01/2015.
 */
public class HomeTeamActivity extends Activity {
    String msg = "Home:";
    ServiceHandler sHandler;

    List<Project> projectList;
    ProjectTeamAdapter projectTeamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(msg, "Muncul tampilan home team");
        setContentView(R.layout.team_home);

        projectList = new ArrayList<Project>();

        String getProjectAct = "";
        try {
            getProjectAct = String.valueOf(new GetProjectAction().execute("").get());
            Log.d(msg, "Data :" + getProjectAct);
            JSONObject respondProject = new JSONObject(getProjectAct);
            if(respondProject.optInt("status", 0) == 1){

                JSONArray dataProject =  respondProject.getJSONArray("data");
                for (int n = 0; n < dataProject.length(); n++) {
                    JSONObject data = dataProject.getJSONObject(n);
                    Project project = new Project();
                    project.kode_project = data.getString("kode_project");
                    project.nama_project = data.getString("project_name");

                    projectList.add(project);
                }
                ListView listView = (ListView) findViewById(R.id.listview);

                projectTeamAdapter = new ProjectTeamAdapter(this, projectList);
                listView.setAdapter(projectTeamAdapter);

                listView.setOnItemClickListener(projectTeamAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    GetProjectAction getProjectAct;

    protected void onDestroy(){
        super.onDestroy();
        if(getProjectAct != null && getProjectAct.getStatus() == AsyncTask.Status.RUNNING) getProjectAct.cancel(true);
    }

    private void initializeComponents(){

    }

    private class GetProjectAction extends AsyncTask<String, String, String> {
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
                String respond= sHandler.makeServiceCall(Constants.URL_SERVER+"/api/project/get_project_by_user?key=" + Constants.User.USER_KEY,ServiceHandler.GET);

                return respond;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
