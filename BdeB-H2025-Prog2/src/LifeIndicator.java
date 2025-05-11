import eko.EKOConsole;
import eko.EKOCouleur;

public class LifeIndicator extends ObjetJeu {

    //Attributs
    private static final String ICON_FULL = "\uE221";
    private static final String ICON_LOSE = "\uE231";
    private static final int MAX_HEALTH = 5;
    private static int nbLifeLeft = 5;

    /**
     * Construteur qui permet d'instancier l'indicateur de vie
     * @param x Position X de l'indicateur de vie
     * @param y Position Y de l'indicateur de vie
     */
    public LifeIndicator(int x, int y) {
        super("Life indicator", x, y, Etiquette.LIFE_INDICATOR);
    }

    /**
     * Méthode qui fait perdre 1 vie lorsque le Player entre en collision avec un ennemi
     */
    public static void looseALife() {
        nbLifeLeft--;
    }

    /**
     * Méthode qui faire remplir toutes les vies perdues lorsque le joueur entre en collision avec une potion
     */
    public static void refuelLife() {
        nbLifeLeft = 5;
    }

    /**
     * Méthode non implantée, car l'indicateur de vie ne se deplace pas
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Méthode qui permet l'affichage de l'indicateur de vie
     */
    @Override
    protected void dessiner() {
        //implementer du code pour determiner quand perdre une vie et comment le representer
        String life = ICON_FULL.repeat(nbLifeLeft) + ICON_LOSE.repeat(MAX_HEALTH - nbLifeLeft);
        EKOConsole.afficher(position.x, position.y, life, EKOCouleur.ROSE);
    }

    //Accesseur
    public static int getNbLifeLeft() { return nbLifeLeft; }
}
