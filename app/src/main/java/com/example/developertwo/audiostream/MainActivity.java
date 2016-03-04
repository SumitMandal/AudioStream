package com.example.developertwo.audiostream;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, OnErrorListener, OnCompletionListener {

    MediaPlayer mediaPlayer;
    ProgressDialog progressDialog;
    String url;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buffering...");
    }

    public void playMusicFromRaw(View view) {
        progressDialog.show();

        mediaPlayer = MediaPlayer.create(this, R.raw.right_answer);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.start();
    }


    public void playMusicFromURI(View view) {
        uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Download/right_answer.mp3");

        progressDialog.show();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        try {
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepareAsync();
            //mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void playMusicFromURL(View view) {
//        url = "http://www.robtowns.com/music/blind_willie.mp3";
        url = "http://192.168.1.3:9000/webservice/sound/right_answer.mp3";

        progressDialog.show();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            //mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //progressDialog.setMessage("Playing...");
        progressDialog.dismiss();
        Toast.makeText(MainActivity.this, "Playing Music", Toast.LENGTH_SHORT).show();
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp != null) {
            mp.release();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(MainActivity.this, "Could not find the resource.", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        return false;
    }

}
