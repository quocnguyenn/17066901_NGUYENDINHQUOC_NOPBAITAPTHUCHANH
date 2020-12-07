package com.example.musicboundservice;

public class Music {
    private String name;
    private int mp3;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMp3() {
        return mp3;
    }

    public void setMp3(int mp3) {
        this.mp3 = mp3;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Music(String name, int mp3, int image) {
        this.name = name;
        this.mp3 = mp3;
        this.image = image;
    }
}
