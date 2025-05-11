import eko.EKOConsole;

public class Author extends InfoScreen {

    /**
     * Constructeur d'objet pour la page de présentation de type Auhtor
     */
    public Author() {
        super("Page auteur", 0, 0, Etiquette.AUTHOR);
    }

    /**
     * Méthode qui permet d'afficher les informations voulu dans l'écran Auhtor
     */
    @Override
    protected void dessiner() {
        String auteur = "Martin Oré Rodriguez";
        String da = "1425594";
        int hauteurAffichage = EKOConsole.hauteur()/2;

        EKOConsole.afficher((EKOConsole.largeur() - auteur.length()) /2, hauteurAffichage, auteur);
        EKOConsole.afficher((EKOConsole.largeur() - da.length()) /2, hauteurAffichage + 1, da);
        super.dessiner();
    }
}
