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
import android.widget.ListView;

import apps.pyramidlib.myyourlist.connection.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeOwnerActivity extends Activity {
    String msg = "Home:";
    Context c;
    ServiceHandler sHandler;

    List<Project> projectList;
    ProjectAdapter projectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(msg, "Muncul tampilan home owner");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_home);

        projectList = new ArrayList<Project>();

        String getProjectAct = "";
        try {
            getProjectAct = String.valueOf(new GetProjectAction().execute("").get());

            JSONObject respondProject = new JSONObject(getProjectAct);
            if(respondProject.optInt("status", 0) == 1){

                JSONArray dataProject =  respondProject.getJSONArray("data");
                for (int n = 0; n < dataProject.length(); n++) {
                    JSONObject data = dataProject.getJSONObject(n);
                    Project project = new Project();
                    project.kode_project = data.getString("kode_project");
                    project.nama_project = data.getString("project_name");
                    project.platform_project = data.getString("project_platform");

                    projectList.add(project);
                }
                ListView listView = (ListView) findViewById(R.id.listview);

                projectAdapter = new ProjectAdapter(this, projectList);
                listView.setAdapter(projectAdapter);

                listView.setOnItemClickListener(projectAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d(msg, "Muncul tampilan home owner end");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void creteProjectAction(View view) {
        startActivity(new Intent(getApplicationContext(), AddProjectActivity.class));
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
                Constants.User.USER_KEY = "8895a2e62346fa0aa9fc7f47ba3fd5efd6616a86";
                String respond= sHandler.makeServiceCall(Constants.URL_SERVER+"/api/project/get_projects?key=" + Constants.User.USER_KEY,ServiceHandler.GET);

                return respond;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
