package thread;

import model.music.Lyric;
import model.music.MusicData;
import model.music.MusicPlayer;

import javax.sound.sampled.Clip;
import javax.swing.*;

import static java.lang.Thread.sleep;

public class LyricPrintSystem  extends SwingWorker<Void, Void> {
    MusicData currentMusicData;
    MusicPlayer currentMusicPlayer;
    Lyric currentMusicLyric;
    long currentMusicTime;
    String lyricPart;

    public LyricPrintSystem(MusicPlayer currentMusicPlayer){
        this.currentMusicPlayer = currentMusicPlayer;
    }

    @Override
    protected Void doInBackground() throws Exception {

        while(true){
            currentMusicData = currentMusicPlayer.getCurrentPlayedMusic();
            try {
                currentMusicLyric = currentMusicData.getLyric();
            }catch (NullPointerException e){
                continue;
            }
            long[][] currentLyricTime = currentMusicLyric.getTime();
            String[] currentLyricString = currentMusicLyric.getLrc();
            Clip currentClip = currentMusicPlayer.getPlayingClip();
            int start = 0,j = 1;
            currentMusicTime = currentClip.getMicrosecondPosition();
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
//            long termEnd,termStart;
//            termEnd = currentMusicLyric.getMicroTime(currentLyricTime[start+1]);
//            termStart = currentMusicLyric.getMicroTime(currentLyricTime[start]);
//            System.out.println(termStart +"/"+ termEnd);
//            sleep((termEnd-termStart)/1000);

        }
    }
}
