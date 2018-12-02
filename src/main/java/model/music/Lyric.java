package model.music;

public class Lyric {
    private String[] lrc;
    private int[][] time;

    public Lyric(String[] lrc, int[][] time) {
        this.lrc = lrc;
        this.time = time;
    }

    public String[] getLrc() {
        return lrc;
    }

    public int[][] getTime() {
        return time;
    }
}
