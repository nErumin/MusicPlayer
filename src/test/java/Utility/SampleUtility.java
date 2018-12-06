package Utility;

import model.music.Music;
import model.music.MusicData;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class SampleUtility {
    public static List<MusicData> getMusicSamples() {
        List<MusicData> musics = new ArrayList<>();
        Date nowDate = Date.from(ZonedDateTime.now().toInstant());

        for (Integer i = 0; i < 100; ++i) {
            MusicData music = new Music(i.toString(), i.toString(), i.toString(), null, null);

            music.setFavorite(i % 2 == 1);

            if (i % 3 == 2) {
                Date copiedDate = new Date(nowDate.getTime());
                copiedDate.setTime(copiedDate.getTime() - i);
                music.setRecentPlayedDate(copiedDate);
            }

            musics.add(music);
        }

        return musics;
    }

    private SampleUtility() {

    }
}
