import eko.EKOConsole;
import eko.EKOCouleur;

public class Potion extends Items {

    //Pour ne pas confondre la machine, la lettre L est assigne a la potion (pour 'Life')

    //Attributs
    private static String icon = "\uE273";

    /**
     * Constructeur de l'objet de type Potion
     * @param x Position X de la potion
     * @param y Position Y de la potion
     */
    public Potion(int x, int y) {
        super("Love potion", x, y, Etiquette.POTION);
    }

    /**
     * Methode non implementee, car la pation ne se de place pas
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {

    }

    /**
     * Method qui permet l'affichage de l'icone de la potion
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(getX(), getY(), icon, EKOCouleur.MAGENTA);
    }

}
