package com.accenture.jooyongsung.app;

public class AudioPlayerUtil {
    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress peruration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        long currentSeconds = currentDuration / 1000;
        long totalSeconds = totalDuration / 1000;

        // calculating percentage
        Double percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timerseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        totalDuration = totalDuration / 1000;
        int currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }
}
