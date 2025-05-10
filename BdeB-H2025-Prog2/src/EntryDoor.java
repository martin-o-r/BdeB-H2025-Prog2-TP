import eko.EKOConsole;
import eko.EKOCouleur;

public class EntryDoor extends ObjetJeu {

    //Attributs
    private static String icon = "\uEAD3";

    /**
     * Constructor qui permet d'instancier la porte d'entree
     * @param x Position X de la porte d'entree
     * @param y Position Y de la porte d'entree
     */
    public EntryDoor(int x, int y) {
        super("Entry door", x, y, Etiquette.ENTRY_DOOR);
    }

    /**
     * Méthode vide, car la porte de sortie n'a pas besoin d'actualisation
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Méthode qui permet d'afficher l'icone de la porte
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(getX(), getY(), icon, EKOCouleur.VERT);
    }


}
