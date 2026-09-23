package it.unibo.giocooca.audio;

/**
 * Effetti sonori disponibili nel gioco, con il percorso della relativa risorsa audio.
 */
public enum SoundEffect {
    DICE_ROLL("/audio/rolling-dice-1.wav"),
    PIECE_MOVE("/audio/piece-soundaction.wav"),
    PRISON("/audio/malus_cell_grand_piano_negative.wav"),
    SPECIAL_CELL("/audio/positive_cell_grand_piano_positive_long.wav"),
    WIN("/audio/win-audio.wav"),
    BACKGROUND_MUSIC("/audio/background-music.wav"),
    PRISON_DOOR("/audio/prison-door.wav"),
    SPRING("/audio/spring.wav");

    private final String resourcePath;

    SoundEffect(final String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * Restituisce il percorso della risorsa audio associata.
     *
     * @return il percorso della risorsa audio
     */
    public String getResourcePath() {
        return resourcePath;
    }
}
