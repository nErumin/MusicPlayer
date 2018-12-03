package model.music;

public class Lyric {
    private String[] lrc;
    private long[][] time;
    private Boolean isSetLyric;

    public Lyric(String[] lrc, long[][] time) {
        this.lrc = lrc;
        this.time = time;
        this.isSetLyric = false;
    }

    public String[] getLrc() {
        return lrc;
    }

    public long[][] getTime() {
        return time;
    }

    public long getMicroTime(long[] time){
        long total;
        total = (time[0] * 60 * 1000 * 1000) + (time[1] * 1000 * 1000) + (time[2] * 1000);
        return total;
    }

    public void setIsSetLyric(boolean ipt){
        this.isSetLyric = ipt;
    }

    public boolean getIsSetLyric(){
        return isSetLyric;
    }
}
