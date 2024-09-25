package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    // Separate Clip objects for music and sound effects
    Clip musicClip;
    Clip soundEffectClip;

    // Arrays to hold URLs for music and sound effects
    URL gameMusicURL[] = new URL [30];
    URL soundEffectURL[] = new URL[30];

    public Sound() {
        // Load music into the gameMusicURL array
        gameMusicURL[0] = getClass().getResource("/Music/Twilight of the Gods.wav");
        gameMusicURL[1] = getClass().getResource("/Music/And what if I can't.wav");
        gameMusicURL[2] = getClass().getResource("/Music/Chaos.wav");
        gameMusicURL[3] = getClass().getResource("/Music/Pray for Victory.wav");
        gameMusicURL[4] = getClass().getResource("/Music/Annihilation.wav");
        gameMusicURL[5] = getClass().getResource("/Music/Chaos2.wav");
        gameMusicURL[6] = getClass().getResource("/Music/Divine Decree.wav");
        gameMusicURL[7] = getClass().getResource("/Music/A Dark Fall.wav");
        gameMusicURL[8] = getClass().getResource("/Music/Destiny.wav");
        gameMusicURL[9] = getClass().getResource("/Music/Don't speak her name.wav");
        gameMusicURL[10] = getClass().getResource("/Music/Purpose.wav");


        // Load sound effects into the soundEffectURL array
        soundEffectURL[0] = getClass().getResource("/Sound_Effects/Cursor_Movement.wav");
        soundEffectURL[1] = getClass().getResource("/Sound_Effects/Player_Phase.wav");
        soundEffectURL[2] = getClass().getResource("/Sound_Effects/Enemy_Phase.wav");
        soundEffectURL[3] = getClass().getResource("/Sound_Effects/Game_Start.wav");
        soundEffectURL[4] = getClass().getResource("/Sound_Effects/Unit_Walking.wav");
        soundEffectURL[5] = getClass().getResource("/Sound_Effects/Select_Player.wav");
        soundEffectURL[6] = getClass().getResource("/Sound_Effects/Cancel_Button.wav");
        soundEffectURL[7] = getClass().getResource("/Sound_Effects/Armored_Walking.wav");
        soundEffectURL[8] = getClass().getResource("/Sound_Effects/Mounted_Walking.wav");
        soundEffectURL[9] = getClass().getResource("/Sound_Effects/Opening_Theme.wav");
        soundEffectURL[10] = getClass().getResource("/Sound_Effects/Level_Up.wav");
        soundEffectURL[11] = getClass().getResource("/Sound_Effects/Stat_Point_+1.wav");
        soundEffectURL[12] = getClass().getResource("/Sound_Effects/Potion_Use.wav");
        soundEffectURL[13] = getClass().getResource("/Sound_Effects/Healing_Spell.wav");
        soundEffectURL[14] = getClass().getResource("/Sound_Effects/Attack.wav");
        soundEffectURL[15] = getClass().getResource("/Sound_Effects/Critical_Attack.wav");
        soundEffectURL[16] = getClass().getResource("/Sound_Effects/No_Damage.wav");
        soundEffectURL[17] = getClass().getResource("/Sound_Effects/Miss.wav");
        soundEffectURL[18] = getClass().getResource("/Sound_Effects/Defeated.wav");
        soundEffectURL[19] = getClass().getResource("/Sound_Effects/BeaconOfLight.wav");
        soundEffectURL[20] = getClass().getResource("/Sound_Effects/Item_Equip.wav");
    }


    // Sets the sound effect to play by loading the audio file at the specified index.
    public void setSoundEffectFile(int i) {
        try {
            // Obtain an AudioInputStream for the specified sound effect URL
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundEffectURL[i]);
            soundEffectClip = AudioSystem.getClip(); // Get a sound clip resource
            soundEffectClip.open(ais); // Open the audio clip from the stream

            // Adjust volume for sound effects
            if (i != 1 && i != 2) {
                FloatControl gainControl = (FloatControl) soundEffectClip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-11.0f); // Reduce the volume by 11 decibels
            }
            else if (i == 1 || i == 2) {
                FloatControl gainControl = (FloatControl) soundEffectClip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-8.0f); // Reduce the volume by 8 decibels
            }
        } catch (Exception e) {
        }
    }

    // Sets the background music to play by loading the audio file at the specified index.
    public void setGameMusicFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(gameMusicURL[i]);
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
        } catch (Exception e) {
        }
    }

    // Plays the currently loaded sound or music clip
    public void playMusic() {
        musicClip.start();
    }

    // Loops the currently loaded sound or music clip continuously
    public void loopMusic() {
        musicClip.loop(musicClip.LOOP_CONTINUOUSLY);
    }

    // Stops the currently playing sound or music clip
    public void stopMusic() {
        musicClip.stop();
    }

    // Plays the currently loaded sound effect clip
    public void playSE() {
        soundEffectClip.start();
    }

    // Stops the currently playing sound effect clip
    public void stopSE() {
        soundEffectClip.stop();
    }
}
