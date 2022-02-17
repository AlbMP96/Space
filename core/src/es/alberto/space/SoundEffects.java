package es.alberto.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundEffects {

    static Music music;

    public static void loadSFX() {
        music = Gdx.audio.newMusic(Gdx.files.internal("space.mp3"));
    }

    static void dispose() {
        music.dispose();
    }

    public static void play(SFX sfx) {
        loadSFX();
        switch (sfx) {
            case MUSIC:
                music.play();
                break;
            case SHOOT:

                break;
            case DESTROY:

                break;
            case DIE:

                break;
        }
    }

}
