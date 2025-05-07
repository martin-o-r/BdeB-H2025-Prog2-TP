import eko.EKO;
import eko.EKOTouche;
import org.w3c.dom.ls.LSOutput;

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

        //a reessayer avec un boolean
        //la touche doit etre detecte quand elle est relachee!!!!

        //tenter de controler la barre espace avec un boolean
        //boolean vraiment necessaire?
        //methode avec un simple delai - mais le prof aime pas ca
        if (EKOTouche.ESPACE.estEnfoncee()) {
            if (!spaceIsPressed) {
                spaceIsPressed = true;
                GameProgressManager.next(this.etiquette);
            } else {
                spaceIsPressed = false; //reinitialise si la touche ESPACE est relachee
            }
            EKO.attendre(300);
        }
    }

    /**
     * Method a redefinir dans les sous-classes qui permet d'afficher un objet
     */
    @Override
    protected void dessiner() {}
}
