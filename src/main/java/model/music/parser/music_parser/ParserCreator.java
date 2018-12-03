package model.music.parser.music_parser;

import org.apache.commons.io.FilenameUtils;
import utility.ReflectionUtility;

import java.lang.reflect.Constructor;

public final class ParserCreator {
    private static final String PACKAGE_NAME = "model.music.parser";
    private static volatile ParserCreator instance = null;

    private ParserCreator() {

    }

    public static ParserCreator getInstance() {
        if (instance == null) {
            synchronized (ParserCreator.class) {
                if (instance == null) {
                    instance = new ParserCreator();
                }
            }
        }
        return instance;
    }

    public MusicParser createParser(String filePath) {
        try {
            Class<?> parserClass = fetchParserClass(filePath);
            Constructor<?> constructor = parserClass.getConstructor(String.class);

            return (MusicParser) constructor.newInstance(filePath);
        } catch (Exception exception) {
            return new MP3Parser(filePath);
        }
    }

    private Class<?> fetchParserClass(String filePath) throws ClassNotFoundException {
        String extension = FilenameUtils.getExtension(filePath).toLowerCase();

        for (Class<?> parserClass : ReflectionUtility.getClassesInPackage(PACKAGE_NAME)) {
            if (parserClass.getSimpleName().toLowerCase().startsWith(extension)) {
                return parserClass;
            }
        }

        throw new ClassNotFoundException();
    }
}
