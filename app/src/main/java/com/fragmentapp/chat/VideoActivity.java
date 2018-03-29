package com.fragmentapp.chat;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.fragmentapp.R;

public class VideoActivity extends Activity {

    public static final String VIDEO_PATH = "VIDEO_PATH";

    private VideoView mVideoView;

    private int mSavedCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        String videoPath = getIntent().getStringExtra(VIDEO_PATH);

        mVideoView = findViewById(R.id.videoview_video);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);

        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoPath(videoPath);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.requestLayout();
                if (mSavedCurrentPosition != 0) {
                    mVideoView.seekTo(mSavedCurrentPosition);
                    mSavedCurrentPosition = 0;
                } else {
                    play();
                }
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.setKeepScreenOn(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSavedCurrentPosition = mVideoView.getCurrentPosition();
        mVideoView.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pause();
    }

    private void play() {
        mVideoView.start();
        mVideoView.setKeepScreenOn(true);
    }

    private void pause() {
        mVideoView.pause();
        mVideoView.setKeepScreenOn(false);
    }
}
