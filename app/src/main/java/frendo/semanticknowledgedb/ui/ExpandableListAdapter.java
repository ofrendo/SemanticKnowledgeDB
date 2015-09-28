package frendo.semanticknowledgedb.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import frendo.semanticknowledgedb.R;

/**
 * Created by Oliver on 27.09.2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private String[] groups = {"Bilder", "Videos"};
    private String[] images;
    private String[] videos;

    public ExpandableListAdapter(Context context, String[] images, String[] videos) {
        this.context = context;
        this.images = images;
        this.videos = videos;
        Log.i("ExpandableListAdapter", "init with images=" + images.length + " videos=" + videos.length);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return (groupPosition == 0)
                ? images[childPosition]
                : videos[childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {



        //Log.i("ExpandableListAdapter", "In getChildView, gP=" + groupPosition + ", cP=" + childPosition);

        if (groupPosition == 0) {
            if (convertView == null || true) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.expandable_child_item_image, null);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            //Log.i("ExpandableListAdapter", "image=" + images[childPosition] + ", " + "convertView=" + convertView + ", imageView=" + imageView);
            int rID = context.getResources().getIdentifier(images[childPosition] , "drawable", context.getPackageName());
            imageView.setImageResource(rID);

            //Log.i("ExpandableListAdpater", "groupPosition: " + groupPosition + ", imageSrc=" + images[childPosition]);
        }
        else {
            if (convertView == null || true) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                //Log.i("ExpandableListAdpater", "groupPosition: " + groupPosition + ", videoSrc=" + videos[childPosition]);
                convertView = infalInflater.inflate(R.layout.expandable_child_item_video, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.video_label);
            //Log.i("ExpandableListAdapter", "convertView=" + convertView + ", textView=" + textView);
            textView.setText("Video " + childPosition);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (groupPosition == 0)
                ? images.length
                : videos.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.expandable_group_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.title);
        tv.setText(groups[groupPosition]);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
