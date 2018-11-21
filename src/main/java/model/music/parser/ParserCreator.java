package model.music.parser;

import model.music.Music;

public final class ParserCreator {
    private static volatile ParserCreator instance = null;

    private ParserCreator() {

    }

    public static ParserCreator getInstance(){
        if(instance == null) {
            synchronized (ParserCreator.class) {
                if (instance == null) {
                    instance = new ParserCreator();
                }
            }
        }
        return instance;
    }

    public MusicParser createParser(String filePath) {
        return new MP3Parser(filePath);
    }
}
