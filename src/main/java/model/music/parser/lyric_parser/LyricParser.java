package model.music.parser.lyric_parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LyricParser {
    protected String filePath;
    private int[][] time = new int[1000][3];// 0:Min  1:Sec   2:0.Sec
    private String[] lrcTime = new String[1000];
    private String[] lrc = new String[1000];
    private String[] index = new String[10];
    public LyricParser(String filePath) {
        this.filePath = filePath;
        List<String> b = null;
        try {
            b = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < b.size(); i++) {
            index = b.get(i).split("]");
            lrcTime[i] = index[0];
            lrcTime[i] = lrcTime[i].replaceAll("\\[", "");
            try {
                lrc[i] = index[1];
            } catch (Exception ArrayIndexOutOfBoundsException) {

            }
            time[i][0] = (lrcTime[i].charAt(0) - 48) * 10
                + (lrcTime[i].charAt(1) - 48);
            time[i][1] = (lrcTime[i].charAt(3) - 48) * 10
                + (lrcTime[i].charAt(4) - 48);
            time[i][2] = (lrcTime[i].charAt(6) - 48) * 10
                + (lrcTime[i].charAt(7) - 48);
        }
    }

    public String[] getLrc(){
        return lrc;
    }

    public int[][] getTime(){
        return time;
    }


}
