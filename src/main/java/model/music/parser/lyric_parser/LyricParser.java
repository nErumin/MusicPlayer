package model.music.parser.lyric_parser;

import model.music.Lyric;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class LyricParser {
    protected String filePath;
    private long[][] time = new long[1000][3];// 0:Min  1:Sec   2:0.Sec
    private String[] lrcTime = new String[1000];
    private String[] lrc = new String[1000];
    private String[] index = new String[10];
    ArrayList<String> allLyricString = new ArrayList<>();
    public LyricParser(String filePath) {
        this.filePath = filePath.replace(".mp3",".lrc");
        splitLyric();
    }

    public void splitLyric(){
        File maybeLyricFile = new File(this.filePath);
        if(!maybeLyricFile.exists()||!maybeLyricFile.toString().contains(".lrc")) return;

        try {
            BufferedReader br  =  new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath),"CP949"));
            String line  =  br.readLine();

            while(!(line == null)) {
                allLyricString.add(line);
                line = br.readLine();
            }
        } catch (NoSuchFileException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0,j = 1; i < allLyricString.size(); i++) {
            index = allLyricString.get(i).split("]");
            lrcTime[i] = index[0];
            lrcTime[i] = lrcTime[i].replaceAll("\\[", "");
            try {
                lrc[j] = index[1];
            } catch (Exception ArrayIndexOutOfBoundsException) {

            }
            time[j][0] = (lrcTime[i].charAt(0) - 48) * 10
                + (lrcTime[i].charAt(1) - 48);
            time[j][1] = (lrcTime[i].charAt(3) - 48) * 10
                + (lrcTime[i].charAt(4) - 48);
            time[j][2] = (lrcTime[i].charAt(6) - 48) * 10
                + (lrcTime[i].charAt(7) - 48);

            if(lrc[j]==null&&j!=1){
                j--;
            }
            else if(lrc[j]!=null&&j==1){
                j++;
            }
            else if(lrc[j]!=null&&j!=1){
                j++;
            }
            else
            continue;
//            System.out.println(lrcTime[i]);
//            System.out.println(time[i][0] + "/" + time[i][1] + "/" + time[i][2]);
        }
    }


    public Lyric getLyric(){

        if(this.time[1][2] == 0) {
            return null;
        }
        else{
            return new Lyric(this.lrc,this.time);
        }
    }


}
