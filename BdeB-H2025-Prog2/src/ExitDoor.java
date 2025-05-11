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
     * Méthode vide, car la porte de sortie n'a pas besoin d'actualisation
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Méthode qui permet de déverouiller la porte de sortie lorsque la clé est capturée
     */
    public static void unlockDoor() {
        doorLocked = false;
        EKOAudio.jouer(unlockingDoor);
    }

    /**
     * Méthode qui permet de vérouiller la porte
     */
    public static void lockDoor() {
        doorLocked = true;
    }

    /**
     * Méthode qui permet de déterminer si la porte de sortie est verouillée ou non
     * @return Boolean qui identifie l'etat de la porte - vérouillée ou déverouillée
     */
    public static boolean isDoorLocked() {
        return doorLocked;
    }

    /**
     * Méthode qui permet l'affichage de la porte de sortie selon l'état vérouillée ou non
     */
    @Override
    protected void dessiner() {
        if (doorLocked) {
            EKOConsole.afficher(getX(), getY(), lockedDoor, EKOCouleur.RVB(255, 192, 0));
        } else {
            EKOConsole.afficher(getX(), getY(), unlockedDoor, EKOCouleur.RVB(255, 20, 147));
        }
    }


}
