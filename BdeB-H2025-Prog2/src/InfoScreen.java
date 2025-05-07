import eko.EKO;
import eko.EKOTouche;
import org.w3c.dom.ls.LSOutput;

public abstract class InfoScreen extends ObjetJeu {

    private static boolean spaceIsPressed = false;
    private int timesPressed = 0;

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
        /*
        chercher une methode avec un int, compteur qui determine combien de fois le SPACE a ete appuye
        pour continuer
         */

        if (EKOTouche.ESPACE.estEnfoncee()) {
            EKO.attendre(100);
            timesPressed = 1;
        } else {
            timesPressed = 0;
        }

        if (timesPressed == 1) {
            GameProgressManager.next(this.etiquette);
        }



        //tenter de controler la barre espace avec un boolean
        //boolean vraiment necessaire?
        //methode avec un simple delai - mais le prof aime pas ca
//        if (EKOTouche.ESPACE.estEnfoncee()) {
//            if (!spaceIsPressed) {
//                spaceIsPressed = true;
//                GameProgressManager.next(this.etiquette);
//            } else {
//                spaceIsPressed = false; //reinitialise si la touche ESPACE est relachee
//            }
//            EKO.attendre(300);
//        }
    }

    /**
     * Method a redefinir dans les sous-classes qui permet d'afficher un objet
     */
    @Override
    protected void dessiner() {}
}
