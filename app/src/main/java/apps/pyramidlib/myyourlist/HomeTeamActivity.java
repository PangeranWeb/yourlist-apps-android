package apps.pyramidlib.myyourlist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ALIE on 13/01/2015.
 */
public class HomeTeamActivity extends Activity {

    String msg = "Home:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(msg, "Muncul tampilan home manager");
    }
}
