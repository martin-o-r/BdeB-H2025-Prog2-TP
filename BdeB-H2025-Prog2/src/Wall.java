import eko.EKOConsole;
import eko.EKOCouleur;

public class Wall extends ObjetJeu{

    //Attributs
    private final String ICON = "\u2588";

    /**
     * Constructeur qui permet d'instancier un objet de type Wall
     * @param x Position X d'un mur
     * @param y Position Y d'un mur
     */
    public Wall(int x, int y) {
        super("Wall", x, y, Etiquette.WALL);
    }

    /**
     * Méthod non implementée, car le mur ne se déplace pas
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Méthode qui permet l'affichage d'un objet Wall
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, ICON, EKOCouleur.NOIR);
    }

}
