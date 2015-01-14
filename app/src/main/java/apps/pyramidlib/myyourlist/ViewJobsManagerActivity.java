package apps.pyramidlib.myyourlist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ALIE on 14/01/2015.
 */
public class ViewJobsManagerActivity extends Activity {
    Context c;
    String msg = "View Jobs Project Owner:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(msg, "View jobs list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_view_jobs);
    }
}
