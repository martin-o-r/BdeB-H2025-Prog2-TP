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
    La logique pour suivre le Player est expliqué dans un des commentaires de cet affichage reddit :
    https://www.reddit.com/r/javahelp/comments/bqlko1/how_to_make_object_follow_another_object/

    Le concept de "return" dans une methode void (en voulant utiliser un path finder A*)
    https://stackoverflow.com/questions/744676/what-does-the-return-keyword-do-in-a-void-method-in-java
     */

    /**
     * Méthode qui permet à l'objet de type Skeleton de se déplacer vers la position du Player
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

        //Skeleton suit le joueur, donc pas de risque qu'il entre en contact avec une porte
        if (!HitAWall.didWeHitAWall(nextMoveX, nextMoveY)) {
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
