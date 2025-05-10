import eko.EKOConsole;
import eko.EKOCouleur;

public class YouWon extends InfoScreen{

    /**
     * Constructeur pour l'ecran lorsque le jouer gagne le jeu
     */
    public YouWon() {
        super("Win", 0,0, Etiquette.GAME_WON);
    }

    /**
     * Redefinition de la methode dessiner pour afficher le message de victoire
     */
    @Override
    protected void dessiner() {
        String instruction = "APPUYER ESPACE POUR SORTIR";
        StringBuilder win = new StringBuilder();

        //image generee par ChatGPT
        win.append("   (•_•) <3\n");
        win.append("  <) )╯   Bravo! Tu as gagne!\n"); //29 caracteres
        win.append("  / \\\n");

        EKOConsole.afficher((EKOConsole.largeur() - 29)/2, EKOConsole.hauteur()/2, win.toString(), EKOCouleur.ROUGE);
        EKOConsole.afficher((EKOConsole.largeur() - instruction.length()) / 2, EKOConsole.hauteur() - 1, instruction,
                EKOCouleur.GRIS_FONCE);
    }
}
