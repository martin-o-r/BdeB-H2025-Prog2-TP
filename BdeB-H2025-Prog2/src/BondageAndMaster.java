import eko.*;

/**
 * Collège de Bois-de-Boulogne, session hiver 2025
 * TP de fin de session
 *
 * Développement d'un jeu simple avec le système EKO fourni par l'enseignant.
 *
 * @author Martin Ore Rodriguez - 1425594
 * @date Remise le 11 mai 2025
 */

public class BondageAndMaster {

    private static final int TPS = 30; // trames par seconde (FPS – Frames Per Second)
    private static final long MS_PAR_TRAME = 1000 / TPS; // temps par trame (en millisecondes)

    /**
     * Main qui contient le deroulement du jeu complet
     * @param args
     */
    public static void main(String[] args) {

        GameProgressManager.initialiseEKO();
        GameProgressManager.startGame();

        //Loop du jeu principal -> Ne pas toucher!
        long tempsAttente;
        long maintenant;
        long deltaTemps;
        long dernierTemps = System.nanoTime();

        while (true) {

            maintenant = System.nanoTime();
            deltaTemps = maintenant - dernierTemps;
            dernierTemps = maintenant;
            GestionnaireObjetsJeu.obtenir().mettreAJour(deltaTemps / 1_000_000);
            GestionnaireObjetsJeu.obtenir().dessiner();
            tempsAttente = MS_PAR_TRAME - (System.nanoTime() - dernierTemps) / 1_000_000;

            if (tempsAttente > 0) {
                EKO.attendre(tempsAttente);
            }
        }
    }
}
