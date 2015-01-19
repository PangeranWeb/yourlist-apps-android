package apps.pyramidlib.myyourlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ALIE on 10/01/2015.
 */
public class TeamManagerAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    Context context;
    List<Team> teamList;
    TeamManagerAdapter teamManagerAdapter;

    String msg = "Team Manager Adapter :";

    public TeamManagerAdapter(Context context, List<Team> teamList) {
        this.context = context;
        this.teamList  = teamList;
    }

    @Override
    public int getCount() {
        return teamList.size();
    }

    @Override
    public Team getItem(int position) {
        return teamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.manager_team_list_row, parent, false);
        TextView textProjectName = (TextView) convertView.findViewById(R.id.projectName);
        textProjectName.setText(getItem(position).nama_tim);
        TextView textProjectPlatform = (TextView) convertView.findViewById(R.id.projectPlatform);
        textProjectPlatform.setText(getItem(position).job_tim);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(context, SingleJobsTeamManagerActivity.class);
        i.putExtra("id_team", getItem(position).id_tim);
        i.putExtra("nama", getItem(position).nama_tim);
        context.startActivity(i);
    }
}
