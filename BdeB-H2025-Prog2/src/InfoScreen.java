import eko.EKOConsole;
import eko.EKOCouleur;
import eko.EKOTouche;

public abstract class InfoScreen extends ObjetJeu {


    /*
    Informations supplémentaires sur KeyListener
    https://stackoverflow.com/questions/10876491/how-to-use-keylistener
        -> il faut suivre l'etat de la touche ESPACE e.i. sur 2 frames differents, "a ete appuyée" et "est relachée"
            -> enseignant recommande d'utiliser boolean pour faire la detection quand la touche est relachée
     https://stackoverflow.com/questions/21969954/how-to-detect-a-key-press-in-java
     */

    /*
    Utilisation de System.exit(0) pour fermer la fenêtre de la console une fois le jeu terminé
    https://stackoverflow.com/questions/30898773/how-do-i-use-system-exit-in-java
    https://stackoverflow.com/questions/12117160/terminate-a-console-application-in-java
    https://stackoverflow.com/questions/2434592/difference-in-system-exit0-system-exit-1-system-exit1-in-java
     */

    //Attribut
    private static boolean spaceWasPressed = false; //detection si ESPACE a ete appuye au "frame antecedent"

    /**
     * Construteur d'objet de type InfoScreen
     * @param name Nom donne a l'objet
     * @param x Position X de l'objet
     * @param y Position Y de l'objet
     * @param etiquette Etiquette qui détermine le type d'objet
     */
    InfoScreen(String name, int x, int y, Etiquette etiquette) {
        super(name, x, y, etiquette);
    }

    /**
     * Méthode qui sert a faire défiler les ecrans d'affichage principaux
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {
        boolean spaceIsPressed = EKOTouche.ESPACE.estEnfoncee(); //detection si ESPACE a ete appuye au "frame actuel"

        /*
        Logique :
        Si la touche ESPACE a été appuyé au dernier frame (spaceWasPressed) et maintenant la touche n'est pas appuyée
         (spaceIsPressed), alors cela implique que la touche ESPACE a été appuye relachée. Donc, l'ensemble permet de
          déterminer que la touche ESPACE a été appuyée rien qu'une fois.
         */
        if (spaceWasPressed && !spaceIsPressed) {
            GameProgressManager.next(this.etiquette);

            if (GameProgressManager.getCurrentScreen().etiquette == Etiquette.GAME_WON ||
                GameProgressManager.getCurrentScreen().etiquette == Etiquette.GAME_OVER) {
                System.exit(0);
            }
        }
        spaceWasPressed = spaceIsPressed; //réinitialise l'etat du frame avant à false
    }

    /**
     * Méthode a redéfinir dans les sous-classes, permet l'affichage des écrans principaux
     */
    @Override
    protected void dessiner() {
        String instruction = "APPUYER SUR ESPACE POUR CONTINUER";

        EKOConsole.afficher((EKOConsole.largeur() - instruction.length()) / 2, EKOConsole.hauteur() - 1, instruction,
                EKOCouleur.GRIS_FONCE);
    }
}
