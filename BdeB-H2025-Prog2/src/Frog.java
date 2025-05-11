import eko.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Logique pour creer une animation en java
 *     https://www.svetprogramiranja.com/animations_in_java_example.html
 *         -> besoin d'un "Timer" pour controler le déplacement de la langue?
 *             -> peut-etre utiliser un autre attribut pour controler la langue?
 *         -> en plus de celui deja utilisé pour ralentir les mouvements?
 *     https://codingtechroom.com/tutorial/java-implementing-game-timers-java-2d-game-development?utm_source=chatgpt.com
 *         ->Timer est une classe en soi! Non non, pas aller la...
 *         -> principe d'un chronomètre qui marque le début d'une action puis un autre chronomètre qui marque la fin
 *         -> durant ce lapse, on implémente ce qui est désiré
 *     https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html
 *         -> on peut définir un temps d'attente, durant lequel on peut effectuer du code
 */

public abstract class Frog extends Enemy {

    //Attributs
    EKOCouleur color = EKOCouleur.RVB(34, 139, 34);
    protected final EKOChaine ICON = new EKOChaine("\uEDF8", color) ;
    protected final EKOChaine TONGUE1 = new EKOChaine("\u2500", color); //corps de la langue
    protected final EKOChaine TONGUE2 = new EKOChaine("\u257C", color); //pointe de la langue

    private long waitBeforeMoving = 0;
    private final long MAX_WAIT = 30;

    protected boolean extendedTongue = false;
    protected int tongueLength = 0;
    protected final int MAX_TONGUE_LENGTH = 3;
    private long tongueTimer = 0;

    private List<Position> tonguePositions = new ArrayList<>(); //utilisation de la classe Position

    private static EKOSon ENEMY_TOUCHED = EKOAudio.charger("audio/651625__martcraft__fail_cut.wav");

    /**
     * Constructeur de l'objet de type Frog
     * @param x Position X initiale du frog
     * @param y Position Y initiale du frog
     */
    public Frog(int x, int y) {
        super("Frog", x, y, Etiquette.ENEMY);
    }

    /**
     * Méthode qui permet de gérer l'animation de la langue de la grenouille
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {
        //Buffer qui permet de ralentir le mettreAJour
        waitBeforeMoving += deltaTemps;
        if(waitBeforeMoving < MAX_WAIT) {
            return;
        }
        waitBeforeMoving = 0;

        tongueTimer += deltaTemps;

        if (tongueTimer < 800) { //extension de la langue
            extendedTongue = true;

            if (tongueLength < MAX_TONGUE_LENGTH) {
                tongueLength++;
            }

        } else if (tongueTimer < 1300) { //rétraction de la langue

            if (tongueTimer > 0) {
                tongueLength--;
            }

            if (tongueLength == 0) {
                extendedTongue = false;
            }

        } else {
            tongueTimer = 0;
        }

        /*
        On doit effacer les données de la liste, sinon le joueur va entrer en collision avec une case vide
         */
        tonguePositions.clear();

        /*
        Selon l'orientation de la grenouille, les positions de la langue vont être soit à droite de la grenouille
        ou à sa gauche
         */
        for (int i = 1; i <= tongueLength; i++) {
            tonguePositions.add(new Position(facingRight()? position.x + i : position.x - i, position.y, 0));
        }

        /*
        J'ai du implementer une nouvelle méthode qui détecte la collision, car Collisionable ne detecte qu'avec
        l'objet principal, dans ce cas la grenouille
         */
        checkTongueCollision();
    }

    /**
     * Méthode qui permet de déterminer l'orientation de la grenouille
     * @return Boolean qui détermine quelle côté la grenouille regarde
     */
    protected abstract boolean facingRight();

    /**
     * Méthode qui permet de déterminer une collision entre une partie de la langue de la grenouille et le joueur
     */
    private void checkTongueCollision() {
        ObjetJeu player = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Player");
        int playerX = player.getX();
        int playerY = player.getY();

        for (Position place : tonguePositions) {
            if (place.x == playerX && place.y == playerY) {
                LifeIndicator.looseALife();
                EKOAudio.jouer(ENEMY_TOUCHED);
                GameProgressManager.restartLevel();
            }
        }
    }

    /**
     * Méthode qui permet l'affichage de la grenouille et de sa langue
     */
    @Override
    protected void dessiner() {

        EKOConsole.afficher(position.x, position.y, ICON); //dessin de la grenouille

        if (extendedTongue) {
            /*
            On comence a l'index 1 pour que la longue s'affiche devant la grnenouille
             */
            for (int i = 1; i <= tongueLength; i++) {
                if (i == MAX_TONGUE_LENGTH) {
                    EKOConsole.afficher(facingRight()? position.x + i : position.x - i, position.y, TONGUE2);
                } else {
                    EKOConsole.afficher(facingRight()? position.x + i : position.x - i, position.y, TONGUE1);
                }
            }
        }
    }
}
