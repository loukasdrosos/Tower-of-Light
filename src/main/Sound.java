package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL gameMusicURL[] = new URL [30];
    URL soundEffectURL[] = new URL[30];

    public Sound() {
        gameMusicURL[0] = getClass().getResource("/Music/Twilight of the Gods.wav");

        soundEffectURL[0] = getClass().getResource("/Sound_Effects/Cursor_Movement.wav");
        soundEffectURL[1] = getClass().getResource("/Sound_Effects/Player_Phase.wav");
        soundEffectURL[2] = getClass().getResource("/Sound_Effects/Enemy_Phase.wav");
        soundEffectURL[3] = getClass().getResource("/Sound_Effects/Map_Start.wav");
    }

    public void setSoundEffectFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundEffectURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.0f); // Reduce volume by 12 decibels.
        } catch (Exception e) {
        }
    }

    public void setGameMusicFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(gameMusicURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
