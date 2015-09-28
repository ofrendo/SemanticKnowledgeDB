package frendo.semanticknowledgedb.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import frendo.semanticknowledgedb.R;

/**
 * Created by Oliver on 27.09.2015.
 */
public class YouTubeHandler extends YouTubePlayerSupportFragment {

    public static final String API_KEY = "AIzaSyBSgBo2ryzzrSIaBpyKlUQuat54snazMpg";
    private static YouTubeHandler instance;

    private YouTubePlayer youTubePlayer;
    public YouTubeHandler() { }

    public static YouTubeHandler getInstance() {
        if (instance == null) {
            instance = new YouTubeHandler();

            /*Bundle b = new Bundle();
            b.putString("videoID", videoID);
            instance.setArguments(b);*/

            //instance.init();
        }

        return instance;
    }

    public void setVideoID(String videoID) {
        Log.i("YouTubeHandler", "setVideoID videoID=" + videoID);
        youTubePlayer.cueVideo(videoID);
    }

    public void init() {

        initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
            }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                youTubePlayer = player;
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

                //player.setFullscreenControlFlags(0);
                //player.setPlayerStateChangeListener(playerStateChangeListener);

                /*String videoID = getArguments().getString("videoID");
                Log.i("YouTubeHandler", "InitSuccess videoID=" + videoID + ", wasRestored=" + wasRestored);
                if (!wasRestored) {
                    player.cueVideo(videoID);
                }*/
            }
        });
    }


}
