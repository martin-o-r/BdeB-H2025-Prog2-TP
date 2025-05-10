import eko.EKOConsole;
import eko.EKOCouleur;

public class GameInstructions extends InfoScreen {

    /**
     * Constructeur d'objet de type GameInstruction
     */
    public GameInstructions() {
        super("Instruction de jeu", 0, 0, Etiquette.GAME_INSTRUCTIONS);
    }

    /**
     * Method qui permet d'afficher les instructions du jeu
     */
    @Override
    protected void dessiner() {
        String commandes = "- Utilisez les flèches pour vous deplacer -";
        String cle = "- Récupérez les cerises pour déverouiller les portes -";
        String potion = "- Les beignes remplissent les pillulles -";
        String ennemi = "- Évitez de trop coller les ennemis -";
        String debut = "Que le plaisir commence! :')";
        String instruction = "APPUYER ESPACE POUR COMMENCER";

        int hauteurMilieu = EKOConsole.hauteur() /2;

        EKOConsole.afficher((EKOConsole.largeur() - "Instructions".length()) /2, 1, "Instructions",
                EKOCouleur.ORANGE);
        EKOConsole.afficher((EKOConsole.largeur() - commandes.length()) /2, hauteurMilieu -4, commandes,
                EKOCouleur.ORANGE);
        EKOConsole.afficher((EKOConsole.largeur() - cle.length()) /2, hauteurMilieu -2, cle, EKOCouleur.ORANGE);
        EKOConsole.afficher((EKOConsole.largeur() - potion.length()) /2, hauteurMilieu, potion, EKOCouleur.ORANGE);
        EKOConsole.afficher((EKOConsole.largeur() - ennemi.length()) /2, hauteurMilieu + 2, ennemi, EKOCouleur.ORANGE);
        EKOConsole.afficher((EKOConsole.largeur() - debut.length()) /2, hauteurMilieu + 5, debut, EKOCouleur.ORANGE);
        EKOConsole.afficher((EKOConsole.largeur() - instruction.length()) / 2, EKOConsole.hauteur() - 1, instruction,
                EKOCouleur.GRIS_FONCE);
    }
}
