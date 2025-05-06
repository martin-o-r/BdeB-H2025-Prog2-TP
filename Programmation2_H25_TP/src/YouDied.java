import eko.EKOConsole;
import eko.EKOCouleur;

public class YouDied extends InfoScreen{

    /**
     * Constructeur pour l'ecran lorsque le jouer perd toutes ses vies
     */
    public YouDied() {
        super("Death scene", 0, 0, Etiquette.GAME_OVER);
    }

    /**
     * Methode qui redefinie l'affichage
     */
    @Override
    public void dessiner() {
        StringBuilder lose = new StringBuilder();

        lose.append("       _____\n");
        lose.append("      /     \\\n");
        lose.append("     | () () |\n");
        lose.append("      \\  ^  /\n");
        lose.append("       |||||\n");
        lose.append("       |||||\n\n");
        lose.append("D: VOUS ETES MORT! X.X"); //24 char

        EKOConsole.afficher((EKOConsole.largeur() -  24)/2, 6, lose.toString(), EKOCouleur.BLANC);
    }
}
