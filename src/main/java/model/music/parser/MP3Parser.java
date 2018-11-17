package model.music.parser;

import model.music.Music;

public class MP3Parser extends MusicParser {
    String filePath;
    public MP3Parser(String filePath){
        this.filePath = filePath;
    }
    public Music build(){ return new Music(null, null, null, null); }
}
