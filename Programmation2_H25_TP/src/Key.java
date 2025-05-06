import eko.EKOConsole;
import eko.EKOCouleur;

public class Key extends Items {

    //Attributs
    private static String icon = "\uE29B";

    /**
     * Constructeur d'un objet de type Key
     * @param x
     * @param y
     */
    public Key(int x, int y) {
        super("Key", x, y, Etiquette.KEY);
    }

    /**
     * Methode non implementee, car la cle ne se deplace pas
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Method qui permet d'afficher l'icone d'objet de type Key
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.ROUGE);
    }

}
