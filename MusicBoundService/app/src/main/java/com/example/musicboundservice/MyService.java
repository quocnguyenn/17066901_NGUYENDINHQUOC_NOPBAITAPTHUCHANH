package com.example.musicboundservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private MyPlayer myPlayer;
    private IBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "Đã gọi onCreate()");
        binder = new MyBinder(); // do MyBinder được extends Binder

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int mp3 = intent.getIntExtra("mp3", 1);
        System.out.println(mp3);
        myPlayer = new MyPlayer(this, mp3);
        Log.d("ServiceDemo", "Đã gọi onBind()");
        // trả về đối tượng binder cho ActivityMain
        return binder;

    }
    // Kết thúc một Service
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.stop();
        return super.onUnbind(intent);
    }

    // Xây dựng các phương thức thực hiện nhiệm vụ,
    // tua bai hat
    public void fastForward() {
        int currentPosition = myPlayer.getCurrentPosition();
        currentPosition += 10000;
        myPlayer.fastForward(currentPosition); // tua tiep 10s
    }

    // tua nguoc
    public void fastBack(){
        int currentPosition = myPlayer.getCurrentPosition();
        currentPosition -= 10000;
        myPlayer.fastForward(currentPosition); // tua nguoc 10s
    }

    public void fastStart(){

        myPlayer.fastStart();
    }
    public void play() {
        myPlayer.play();
    }
    public void pause() {
        myPlayer.pause();
    }

    public class MyBinder extends Binder {

        // phương thức này trả về đối tượng MyService
        public MyService getService() {

            return MyService.this;
        }
    }

}
// Xây dựng một đối tượng riêng để chơi nhạc
class MyPlayer {
    // đối tượng này giúp phát một bài nhạc
    private MediaPlayer mediaPlayer;

    public MyPlayer(Context context, int mp3) {
        // Nạp bài nhạc vào mediaPlayer
        mediaPlayer = MediaPlayer.create(
                context, mp3);
        // Đặt chế độ phát lặp lại liên tục
        mediaPlayer.setLooping(true);
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }
    public void fastForward(int pos){
        mediaPlayer.seekTo(pos);
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void fastStart(){
        mediaPlayer.start();
    }

    // phát nhạc
    public void play() {
        System.out.println(mediaPlayer + "asc");
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // dừng phát nhạc
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
