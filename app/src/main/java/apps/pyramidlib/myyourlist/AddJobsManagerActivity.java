package apps.pyramidlib.myyourlist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by ALIE on 14/01/2015.
 */
public class AddJobsManagerActivity extends Activity {
    Context c;
    String msg = "Add Jobs Project Owner:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(msg, "Add jobs view");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_add_jobs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addJobsAction(View view) {
        Log.d(msg, "create jobs actoin");
    }
}
