package it.unibo.giocooca.audio;

public enum SoundEffect {
    DICE_ROLL("/audio/rolling-dice-1.wav"),
    PIECE_MOVE("/audio/piece-soundaction.wav"),
    PRISON("/audio/malus_cell_grand_piano_negative.wav"),
    SPECIAL_CELL("/audio/positive_cell_grand_piano_positive_long.wav"),
    WIN("/audio/win-audio.wav"),
    BACKGROUND_MUSIC("/audio/background-music.wav");


    private final String resourcePath;

    SoundEffect(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
