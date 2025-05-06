import eko.EKOConsole;
import eko.EKOCouleur;

public class Frog extends Enemy{

    //Attributs
    private final String ICON = "\uEDF8";
    private final String TONGUE1 = "\u2500";
    private final String TONGUE2 = "\u257C";

    /**
     * Constructeur
     * @param x
     * @param y
     */
    public Frog(int x, int y) {
        super("Frog", x, y, Etiquette.ENEMY);
    }

    @Override
    protected void mettreAJour(long deltaTemps) {

    }

    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, ICON, EKOCouleur.RVB(34, 139, 34));
    }
}
