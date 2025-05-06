import eko.EKOAudio;
import eko.EKOConsole;
import eko.EKOCouleur;
import eko.EKOSon;

public class ExitDoor extends ObjetJeu {

    //Attributs de la porte de sortie
    private static final String lockedDoor = "\uE0A2";
    private static final String unlockedDoor = "\uE258";
    private static boolean doorLocked = true;
    private static final EKOSon unlockingDoor = EKOAudio.charger("audio/553518__newlocknew__pop-down" +
            "-impact_49lrsmltprcssng_cut.wav");

    /**
     * Constructeur qui instancie la porte de sortie
     * @param x Position X de la porte de sortie
     * @param y Position Y de la porte de sortie
     */
    public ExitDoor(int x, int y) {
        super("Exit door", x, y, Etiquette.EXIT_DOOR);
    }

    /**
     * Method qui permet de deverouiller la porte de sortie lorsque la cle est capturee
     */
    public static void unlockDoor() {
        doorLocked = false;
        EKOAudio.jouer(unlockingDoor);
    }

    /**
     * Methode qui permet de verouiller la porte
     */
    public static void lockDoor() {
        doorLocked = true;
    }

    /**
     * Method qui permet de determiner si la porte de sortie est verouillee ou non
     * @return Boolean qui identifie l'etat de la porte - verouillee ou deverouillee
     */
    public static boolean isDoorLocked() {
        return doorLocked;
    }

    /**
     * Method qui permet de mettre a jour, mais l'objet porte de sortie ne change pas au courant du jeu
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Method qui permet l'affichage de la porte de sortie selon l'etat verouillee ou non
     */
    @Override
    protected void dessiner() {
        if (doorLocked) {
            EKOConsole.afficher(getX(), getY(), lockedDoor, EKOCouleur.JAUNE);
        } else {
            EKOConsole.afficher(getX(), getY(), unlockedDoor, EKOCouleur.ROSE);
        }
    }


}
