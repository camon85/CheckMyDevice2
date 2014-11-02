package com.accenture.jooyongsung.app;

import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class AudioPlayerManager {
    public static final String MEDIA_PATH = new String("/sdcard/music/");
    private final String TAG = "AudioPlayerManager";
    private List<String> songsList = new ArrayList<>();

    public List<String> getPlayList() {
        File home = new File(MEDIA_PATH);

        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                songsList.add(file.getName());
            }
        }

        // return songs list array
        Log.i(TAG, "total mp3 : " + songsList.size());
        return songsList;
    }

    /**
     * Class to filter files which are having .mp3 extension
     */
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}
