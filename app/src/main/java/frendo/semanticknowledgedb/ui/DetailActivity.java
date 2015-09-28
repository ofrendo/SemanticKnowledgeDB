package frendo.semanticknowledgedb.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import frendo.semanticknowledgedb.OverviewActivity;
import frendo.semanticknowledgedb.R;
import frendo.semanticknowledgedb.remote.Callback;
import frendo.semanticknowledgedb.remote.SparqlHandler;

public class DetailActivity extends AppCompatActivity {

    private Context thisContext;
    private String itemID;
    private String itemLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Loading...");

        thisContext = this;

        Intent intent = getIntent();
        itemID = intent.getStringExtra(OverviewActivity.INTENT_MESSAGE_CONTAINER_ID);
        itemLabel = intent.getStringExtra(OverviewActivity.INTENT_MESSAGE_CONTAINER_LABEL);

        YouTubeHandler youTubeHandler = YouTubeHandler.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.youtube_fragment, youTubeHandler).commit();

        SparqlHandler sparqlHandler = new SparqlHandler();
        sparqlHandler.loadItem(itemID, new Callback() {
            public void call(String result) {
                setTitle(itemLabel);
                onItemLoaded(result);
            }
        });

    }

    public void setYouTubeVideoID(String videoID) {
        YouTubeHandler.getInstance().setVideoID(videoID);
    }

    private void onItemLoaded(String jsonResult) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResult);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            if (jsonObject.has("comment")) {
                setCommentText(jsonObject.getString("comment"));
            }

            ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

            String images[] = {};
            String videos[] = {};
            if (jsonObject.has("images")) {
                images = jsonObject.getString("images").split(";");
                Log.i("DetailActivity", "Images: " + jsonObject.getString("images") + ", image[0]: " + images[0]);
            }
            if (jsonObject.has("videos")) {
                videos = jsonObject.getString("videos").split(";");
                Log.i("DetailActivity", "Videos: " + jsonObject.getString("videos") + ", videos[0]: " + videos[0]);
            }
            ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(thisContext, images, videos);
            expandableListView.setAdapter(expandableListAdapter);
            final String[] finalVideos = videos;
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    if (groupPosition == 1) {
                        String videoID = finalVideos[childPosition].split("=")[1];
                        setYouTubeVideoID(videoID);
                    }
                    return true;
                }
            });
            expandableListView.expandGroup(0);
            expandableListView.expandGroup(1);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCommentText(String commentText) {
        TextView commentView = (TextView) findViewById(R.id.textViewComment);
        commentView.setText(commentText);
    }
    private void addImage(String imageSrc) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        ImageView imageView = new ImageView(thisContext);
        int rID = thisContext.getResources().getIdentifier(imageSrc , "drawable", thisContext.getPackageName());
        imageView.setImageResource(rID);

        linearLayout.addView(imageView);
    }
    private void addVideo(String videoSrc) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
}
