package it.unibo.giocooca;

public final class Launcher {
    /**
     * Come da linea guida il costruttore privato impedisce di istanziare la classe erroneamente
     */
    private Launcher(){
    }
    /**
     * @param args
     * Il metodo main avvia l'applicazione tramite la classe App
     */
    public static void main(String[] args){
        App.main(args);
    }
}
