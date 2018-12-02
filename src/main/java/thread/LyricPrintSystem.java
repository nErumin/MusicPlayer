package thread;

import model.music.Lyric;
import model.music.MusicData;
import model.music.MusicPlayer;

import javax.sound.sampled.Clip;

public class LyricPrintSystem extends Thread{
    MusicData currentMusicData;
    MusicPlayer currentMusicPlayer;
    Lyric currentMusicLyric;
    long currentMusicTime;
    String lyricPart;
    public LyricPrintSystem(MusicPlayer currentMusicPlayer){
        this.currentMusicPlayer = currentMusicPlayer;
        start();
    }

    public void run(){
        currentMusicData= currentMusicPlayer.getCurrentPlayedMusic();
        currentMusicLyric = currentMusicData.getLyric();
        long[][] currentLyricTime = currentMusicLyric.getTime();
        String[] currentLyricString = currentMusicLyric.getLrc();
        Clip currentClip = currentMusicPlayer.getPlayingClip();
        currentMusicTime = currentClip.getMicrosecondPosition();
        while(true){
            int start = 0,j = 1;
            if(currentMusicData.getLyric() == null || currentMusicPlayer == null) continue;
            while (currentLyricString[j] != null) {    //텍스트 파일의 총 줄수
                j++;
            }
            for (int i = 0; i < j; i++) {
                if (currentMusicTime >= currentMusicLyric.getMicroTime(currentLyricTime[i])) {
                    start = i;
                }
            }

            for (int k = 0; k < start; k++) {
                if (currentMusicLyric.getMicroTime(currentLyricTime[start]) == currentMusicLyric.getMicroTime(currentLyricTime[k]))
                    start = k;
                break;
            }
            lyricPart = currentLyricString[start];

            System.out.println(lyricPart);
        }


    }
}
