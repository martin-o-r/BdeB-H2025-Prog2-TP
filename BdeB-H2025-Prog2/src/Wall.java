import eko.EKOConsole;
import eko.EKOCouleur;

public class Wall extends ObjetJeu{

    //Attributs
    private static String icon = "\u2588";

    /**
     * Constructeur qui permet d'instancier un objet de type Wall
     * @param x Position X d'un mur
     * @param y Position Y d'un mur
     */
    public Wall(int x, int y) {
        super("Wall", x, y, Etiquette.WALL);
    }

    /**
     * Method non implementee, car le mur ne se deplace pas
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Method qui permet l'affichage d'un objet Wall
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.NOIR);
    }

}
