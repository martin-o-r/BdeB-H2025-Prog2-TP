import eko.EKOConsole;
import eko.EKOCouleur;

/**
 * La logique pour suivre le Player est expliqué dans un des commentaires de cet affichage reddit :
 *     https://www.reddit.com/r/javahelp/comments/bqlko1/how_to_make_object_follow_another_object/
 *
 * Le concept de "return" dans une methode void (en voulant utiliser un path finder A*)
 *     https://stackoverflow.com/questions/744676/what-does-the-return-keyword-do-in-a-void-method-in-java
 */

public class Skeleton extends Enemy {

    private static String icon = "\uEE15";
    private long waitBeforeMoving = 0;
    private final long MAX_WAIT = 350;
    private static int playerX;
    private static int playerY;

    /**
     * Constructeur de l'objet de type Skeleton
     * @param x Position X initiale de du Skeleton
     * @param y Position Y initiale du Skeleton
     */
    public Skeleton(int x, int y) {
        super("Skeleton", x, y, Etiquette.ENEMY);
    }

    /**
     * Méthode qui actualise la position actuelle du joueur
     * @param x Position X actuelle du joueur
     * @param y Position Y actuelle du joueur
     */
    public static void getPlayerPosition(int x, int y) {
        playerX = x;
        playerY = y;
    }

    /**
     * Méthode qui permet à l'objet de type Skeleton de se déplacer vers la position du Player
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {

        waitBeforeMoving += deltaTemps;
        if (waitBeforeMoving < MAX_WAIT) {
            return;
        }
        waitBeforeMoving = 0;

        int nextMoveX = position.x;
        int nextMoveY = position.y;

        if (playerX < position.x) {
            nextMoveX--;
        } else if (playerX > position.x) {
            nextMoveX++;
        }

        if (playerY < position.y) {
            nextMoveY--;
        } else if (playerY > position.y) {
            nextMoveY++;
        }

        if (!HitSomething.didWeHitAWall(nextMoveX, nextMoveY) &&
            !HitSomething.didWeHitADoor(nextMoveX, nextMoveY)) {
            position.x = nextMoveX;
            position.y = nextMoveY;
        }
    }

    /**
     * Méthode qui permet de dessiner l'objet de type Skeleton
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.RVB(243, 233, 208));
    }

}
