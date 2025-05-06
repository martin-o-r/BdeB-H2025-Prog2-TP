import eko.EKOConsole;
import eko.EKOCouleur;

public class LifeIndicator extends ObjetJeu {

    //Attributs
    private static final String iconFull = "\uE221";
    private static final String iconLose = "\uE231";
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
     * Method qui fait perdre 1 vie lorsque le Player entre en collision avec une ennemi
     */
    public static void looseALife() {
        nbLifeLeft--;
    }

    /**
     * Method qui faire remplir les toutes les vies perdues lorsque le joueur enttre en collision avec une potion
     */
    public static void refuelLife() {
        nbLifeLeft = 5;
    }

    /**
     * Methode non implantee, car l'indicateur de vie ne se deplace pas
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Methode qui permet l'affichage de l'indicateur de vie
     */
    @Override
    protected void dessiner() {
        //implementer du code pour determiner quand perdre une vie et comment le representer
        String life = iconFull.repeat(nbLifeLeft) + iconLose.repeat(MAX_HEALTH - nbLifeLeft);
        EKOConsole.afficher(position.x, position.y, life, EKOCouleur.ROSE);
    }

    //accesseur
    public static int getNbLifeLeft() { return nbLifeLeft; }
}
