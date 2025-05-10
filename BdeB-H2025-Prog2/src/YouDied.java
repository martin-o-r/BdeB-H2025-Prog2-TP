import eko.EKOConsole;
import eko.EKOCouleur;

public class YouDied extends InfoScreen{

    /**
     * Constructeur pour l'écran lorsque le jouer perd toutes ses vies
     */
    public YouDied() {
        super("Death scene", 0, 0, Etiquette.GAME_OVER);
    }

    /**
     * Méthode qui redéfinie l'affichage
     */
    @Override
    public void dessiner() {
        String instruction = "APPUYER ESPACE POUR SORTIR";
        StringBuilder lose = new StringBuilder();

        //Image generee par ChatGPT
        lose.append("       _____\n");
        lose.append("      /     \\\n");
        lose.append("     | () () |\n");
        lose.append("      \\  ^  /\n");
        lose.append("       |||||\n");
        lose.append("       |||||\n\n");
        lose.append("D: VOUS ÊTES MORT! X.X"); //24 char

        EKOConsole.afficher((EKOConsole.largeur() -  24)/2, 6, lose.toString(), EKOCouleur.BLANC);
        EKOConsole.afficher((EKOConsole.largeur() - instruction.length()) / 2, EKOConsole.hauteur() - 1, instruction,
                EKOCouleur.GRIS_FONCE);
    }
}
