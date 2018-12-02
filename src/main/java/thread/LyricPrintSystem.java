package thread;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import model.music.Lyric;
import model.music.MusicData;
import model.music.MusicPlayer;

import javax.sound.sampled.Clip;
import javax.swing.*;

import static java.lang.Thread.sleep;

public class LyricPrintSystem  extends SwingWorker<Void, Void> {
    private volatile static LyricPrintSystem uniqueInstance;
    private MusicData currentMusicData;
    private MusicPlayer currentMusicPlayer;
    private Lyric currentMusicLyric;
    private long currentMusicTime;
    private String lyricPart;

    private Scene scene;
    private Label lyricLabel;
    public static LyricPrintSystem getInstance(){
        if(uniqueInstance == null){
            synchronized(LyricPrintSystem.class){
                if(uniqueInstance == null){
                    uniqueInstance = new LyricPrintSystem();
                }
            }
        }
        return uniqueInstance;
    }

    public LyricPrintSystem(){
    }

    @Override
    protected Void doInBackground() throws Exception {
        long[][] currentLyricTime;
        while(true){
            currentMusicData = this.currentMusicPlayer.getCurrentPlayedMusic();
            try {
                currentMusicLyric = currentMusicData.getLyric();
            }catch (NullPointerException e){
                continue;
            }
            currentLyricTime = currentMusicLyric.getTime();
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

            Platform.runLater(()->{
                lyricLabel.setText(lyricPart);
            });

            long termEnd,termStart;
            termEnd = currentMusicLyric.getMicroTime(currentLyricTime[start+1]);
            termStart = currentMusicLyric.getMicroTime(currentLyricTime[start]);
//            System.out.println(termStart +"/"+ termEnd+"/start = "+start);
            sleep((termEnd-termStart)/1000);

        }
    }
    public void setCurrentMusicPlayer(MusicPlayer currentMusicPlayer){
        this.currentMusicPlayer = currentMusicPlayer;
    }
    public void setScene(Scene scene) {
        this.scene = scene;
        setLabelOnScene(scene);
    }
    private void setLabelOnScene(Scene scene){
        this.lyricLabel = (Label) scene.lookup("#lyricLabel");
    }
}
