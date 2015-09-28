package frendo.semanticknowledgedb;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import frendo.semanticknowledgedb.remote.Callback;
import frendo.semanticknowledgedb.remote.SparqlHandler;
import frendo.semanticknowledgedb.remote.SparqlQuery;
import frendo.semanticknowledgedb.ui.DetailActivity;
import frendo.semanticknowledgedb.ui.IconArrayAdapter;

public class OverviewActivity extends AppCompatActivity {

    public static final String INTENT_MESSAGE_CONTAINER_ID = "frendo.semanticknowledgedb.INTENT_MESSAGE_ID";
    public static final String INTENT_MESSAGE_CONTAINER_LABEL = "frendo.semanticknowledgedb.INTENT_MESSAGE_LABEL";

    protected ListView listView;

    // e.g. karate:kategorie oder karate:technik oder karate:schlag
    protected String listContainerID;
    protected String listContainerLabel;
    protected final String rootListContainerID = "http://www.heidelberg-karate.de/karate#kategorie";
    protected final String rootListContainerLabel = "Kategorien";

    protected Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        setTitle("Loading...");

        listView = (ListView)findViewById(R.id.listViewContents);
        thisContext = this;

        Intent intent = getIntent();
        listContainerID = intent.getStringExtra(INTENT_MESSAGE_CONTAINER_ID);
        listContainerLabel = intent.getStringExtra(INTENT_MESSAGE_CONTAINER_LABEL);

        if (listContainerID == null) {
            listContainerID = rootListContainerID;
            listContainerLabel = rootListContainerLabel;
        }

        Log.i("onCreate", "Started new OverviewActivity with containerID: " + listContainerID);

        //final ActionBar actionBar = getActionBar();

        SparqlHandler handler = new SparqlHandler();
        handler.loadList(listContainerID, new Callback() {
            public void call(String result) {
                //createListHeader(listContainerLabel);
                setTitle(listContainerLabel);
                createAdapter(result);
            }
        });


        //SparqlHandler handler = new SparqlHandler();
        //handler.testConnection();
    }

    protected void onCreateHandle() {

    }


    protected void createAdapter(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            String[] ids  = new String[jsonArray.length()];
            String[] labels = new String[jsonArray.length()];
            String[] icons = new String[jsonArray.length()];
            Boolean[] hasAbfolge = new Boolean[jsonArray.length()];

            Log.i("createAdapter", "Creating list adapter with length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject row = jsonArray.getJSONObject(i);
                //Log.i("setAdapter", "row stuff: " + row.getString("id") + ": " + row.getString("label"));
                ids[i] = row.getString("id");
                labels[i] = row.getString("label");
                hasAbfolge[i] = (row.getString("hasAbfolge").startsWith("t")) ? true : false;
                if (row.has("icon")) {
                    icons[i] = row.getString("icon");
                }
            }

            final IconArrayAdapter adapter = new IconArrayAdapter(this, icons, labels);
            listView.setAdapter(adapter);
            createOnItemClickListener(ids, labels, hasAbfolge);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void createOnItemClickListener(final String[] ids, final String[] labels, final Boolean[] hasAbfolge) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //String item = (String) parent.getItemAtPosition(position);
                Log.i("InListView", "Clicked on id: " + ids[position]);

                Intent intent;
                if (hasAbfolge[position]) {
                    intent = new Intent(thisContext, OverviewActivity.class);
                }
                else {
                    intent = new Intent(thisContext, DetailActivity.class);
                }
                intent.putExtra(INTENT_MESSAGE_CONTAINER_ID, ids[position]);
                intent.putExtra(INTENT_MESSAGE_CONTAINER_LABEL, labels[position]);
                startActivity(intent);

            }

        });
    }

    private void createListHeader(String text) {
        TextView headerView = new TextView(thisContext);
        headerView.setPadding(50, 60, 0, 60);
        headerView.setTextSize(15);
        headerView.setText(text);
        listView.addHeaderView(headerView, null, false);
        listView.setHeaderDividersEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };


    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }*/

}
