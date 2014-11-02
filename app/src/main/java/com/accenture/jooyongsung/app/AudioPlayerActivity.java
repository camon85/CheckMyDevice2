package com.accenture.jooyongsung.app;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AudioPlayerActivity extends Activity implements MediaPlayer.OnCompletionListener,
        SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private final String TAG = "AudioPlayerActivity";
    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;

    // Media Player
    private MediaPlayer mp;

    // Handler to update UI timer, progress bar etc,.
    private AudioPlayerManager songManager;
    private AudioPlayerUtil audioPlayerUtil;
    private Handler mHandler = new Handler();

    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private List<String> songsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        initPlayer();
    }

    private void initPlayer() {
        // All player buttons
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        btnPlay.setOnClickListener(this);
        btnForward.setOnClickListener(this);
        btnBackward.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

        // Mediaplayer
        mp = new MediaPlayer();
        songManager = new AudioPlayerManager();
        audioPlayerUtil = new AudioPlayerUtil();

        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important

        // Getting all songs list
        songsList = songManager.getPlayList();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        // no repeat or shuffle ON - play next song
        if (currentSongIndex < (songsList.size() - 1)) {
            playSong(currentSongIndex + 1);
            currentSongIndex = currentSongIndex + 1;
        } else {
            // play first song
            playSong(0);
            currentSongIndex = 0;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = audioPlayerUtil.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (R.id.btnPlay == id) {
            // check for already playing
            if (mp.isPlaying()) {
                mp.pause();

                // Changing button image to play button
                btnPlay.setImageResource(R.drawable.btn_play);
            } else {
                // Resume song
                mp.start();

                // Changing button image to pause button
                btnPlay.setImageResource(R.drawable.btn_pause);
            }
        } else if (R.id.btnForward == id) {
            // get current song position
            int currentPosition = mp.getCurrentPosition();

            // check if seekForward time is lesser than song duration
            if (currentPosition + seekForwardTime <= mp.getDuration()) {
                // forward song
                mp.seekTo(currentPosition + seekForwardTime);
            } else {
                // forward to end position
                mp.seekTo(mp.getDuration());
            }
        } else if (R.id.btnBackward == id) {
            // get current song position
            int currentPosition = mp.getCurrentPosition();

            // check if seekBackward time is greater than 0 sec
            if (currentPosition - seekBackwardTime >= 0) {
                // forward song
                mp.seekTo(currentPosition - seekBackwardTime);
            } else {
                // backward to starting position
                mp.seekTo(0);
            }
        } else if (R.id.btnNext == id) {
            // check if next song is there or not
            if (currentSongIndex < (songsList.size() - 1)) {
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        } else if (R.id.btnPrevious == id) {
            if (currentSongIndex > 0) {
                playSong(currentSongIndex - 1);
                currentSongIndex = currentSongIndex - 1;
            } else {
                // play last song
                playSong(songsList.size() - 1);
                currentSongIndex = songsList.size() - 1;
            }
        }
    }

    public void playSong(int songIndex) {
        // Play song
        try {
            mp.reset();
            mp.setDataSource(AudioPlayerManager.MEDIA_PATH + songsList.get(songIndex));
            mp.prepare();
            mp.start();

            // Displaying Song title
            String songTitle = songsList.get(songIndex);
            songTitleLabel.setText(songTitle);

            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText("" + audioPlayerUtil.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText("" + audioPlayerUtil.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = audioPlayerUtil.getProgressPercentage(currentDuration, totalDuration);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
    }
}
