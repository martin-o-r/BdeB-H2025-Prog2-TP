import eko.EKOConsole;
import eko.EKOCouleur;

public class Skeleton extends Enemy {

    private static String icon = "\uEE15";
    private long waitBeforeMoving = 0;
    private static int playerX;
    private static int playerY;

    public Skeleton(int x, int y) {
        super("Skeleton", x, y, Etiquette.ENEMY);
    }

    public static void getPlayerPosition(int x, int y) {
        playerX = x;
        playerY = y;
    }

    /*
    La logic pour suivre le Player est explique dans un des commentaires de cet affichage reddit
    https://www.reddit.com/r/javahelp/comments/bqlko1/how_to_make_object_follow_another_object/

    Le concept de "return" dans une methode void (en voulant utiliser un path finder A*)
    https://stackoverflow.com/questions/744676/what-does-the-return-keyword-do-in-a-void-method-in-java
     */

    /**
     * Method qui permet a l'objet de type Skeleton de se deplacer vers la position du Player
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {

        waitBeforeMoving += deltaTemps;
        if (waitBeforeMoving < 350) {
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

        if (!HitAWall.didWeHitAWall(nextMoveX, nextMoveY)) {
            position.x = nextMoveX;
            position.y = nextMoveY;
        }
    }

    /**
     * Method qui permet de dessinger l'objet de type Skeleton lors du niveau de jeu
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.RVB(243, 233, 208));
    }

}
