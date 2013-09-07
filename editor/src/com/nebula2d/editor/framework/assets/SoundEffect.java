package com.nebula2d.editor.framework.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.File;
import java.io.IOException;

public class SoundEffect extends Asset {

    private  Sound sound;
    private boolean isPlaying;
    private boolean isPaused;
    private boolean loop;
    private float volume;
    
    public SoundEffect(String path) {
        super(path);
        sound = Gdx.audio.newSound(new FileHandle(new File(path)));
        isPlaying = isPaused = loop = false;
    }

    public void play() {
        if (!isPlaying) {
            if (loop)
                sound.loop(volume);
            else
                sound.play(volume);
        }
    }

    public void stop() {
        sound.stop();
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        loop = fr.readBooleanLine();
        volume = fr.readFloatLine();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);
        fw.writeBoolLine(loop);
        fw.writeFloatLine(volume);
    }
}