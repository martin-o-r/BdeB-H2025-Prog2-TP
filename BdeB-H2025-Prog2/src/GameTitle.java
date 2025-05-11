import eko.EKOConsole;
import eko.EKOCouleur;

public class GameTitle extends InfoScreen {

    /**
     * Constructeur d'objet de type GameTitle
     */
    public GameTitle() {
        super("Titre du jeu", 0, 0, Etiquette.GAME_TITLE);
    }

    /**
     * Méthode qui permet d'afficher les informations voulu dans la page titre du jeu
     */
    @Override
    protected void dessiner() {
        String nomDuJeu = "Bondage and Masters";

        StringBuilder castle = new StringBuilder();

        //image genere par ChatGPT
        castle.append("            |>>>                   \n");
        castle.append("            |                      \n");
        castle.append("        _  _|_  _                 \n");
        castle.append("       |;|_|;|_|;|                \n");
        castle.append("       \\\\.    .  /                \n");
        castle.append("        \\\\:  .  /                 \n");
        castle.append("         ||:   |                  \n");
        castle.append("         ||:.  |                  \n");
        castle.append("         ||:  .|                  \n");
        castle.append("         ||:   |     \\,/       \n");
        castle.append("         ||: , |           /`\\  \n");
        castle.append("         ||:   |          |---| \n");
        castle.append("     __ ||_ ._|__       __|___|__ \n"); //ligne la plus longue, 34 "caracteres" d'espacement
        castle.append("   (     (    (       (     (    )\n");

        EKOConsole.afficher((EKOConsole.largeur() - 34)/2, 3, castle.toString() ,EKOCouleur.JAUNE);
        EKOConsole.afficher((EKOConsole.largeur() - nomDuJeu.length())/2, 1, nomDuJeu,
                EKOCouleur.ROUGE);
        super.dessiner();
    }


}
