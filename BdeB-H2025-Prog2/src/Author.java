import eko.EKOConsole;
import eko.EKOCouleur;

public class Author extends InfoScreen {

    /**
     * Constructeur d'objet pour la page de presentation de type Auhtor
     */
    public Author() {
        super("Page auteur", 0, 0, Etiquette.AUTHOR);
    }

    /**
     * Method qui permet d'afficher les informations voulu dans l'ecran Auhtor
     */
    @Override
    protected void dessiner() {
        String auteur = "Martin Ore Rodriguez";
        String da = "1425594";
        String instruction = "APPUYER ESPACE POUR CONTINUER";
        int hauteurAffichage = EKOConsole.hauteur()/2;

        EKOConsole.afficher((EKOConsole.largeur() - auteur.length()) /2, hauteurAffichage, auteur);
        EKOConsole.afficher((EKOConsole.largeur() - da.length()) /2, hauteurAffichage + 1, da);
        EKOConsole.afficher((EKOConsole.largeur() - instruction.length()) / 2, EKOConsole.hauteur() - 1, instruction,
                EKOCouleur.GRIS_FONCE);

    }
}
