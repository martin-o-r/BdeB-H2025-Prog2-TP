import eko.EKO;
import eko.EKOTouche;

public abstract class InfoScreen extends ObjetJeu {

    private static boolean spaceIsPressed = false;

    /**
     * Construteur d'objet de type InfoScreen
     * @param name Nom donne a l'objet
     * @param x Position X de l'objet
     * @param y Position Y de l'objet
     * @param etiquette Etiquette qui determine le type d'objet
     */
    InfoScreen(String name, int x, int y, Etiquette etiquette) {
        super(name, x, y, etiquette);
    }

    /**
     * Method a redefinir dans les sous-classe qui permet d'actualiser l'etat d'un objet
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {

        //boolean vraiment necessaire?

        /*
        chercher une methode avec un int, compteur qui determine combien de fois le SPACE a ete appuye
        pour continuer
         */

        if (EKOTouche.ESPACE.estEnfoncee()) {
            EKO.attendre(300);
            if (!spaceIsPressed) {
                spaceIsPressed = true;
                GameProgressManager.next(this.etiquette);
            } else {
                spaceIsPressed = false; //reinitialise si la touche ESPACE est relachee
            }
        }
    }

    /**
     * Method a redefinir dans les sous-classes qui permet d'afficher un objet
     */
    @Override
    protected void dessiner() {}

}
