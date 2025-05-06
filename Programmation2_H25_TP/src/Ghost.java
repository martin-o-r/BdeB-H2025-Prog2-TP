import eko.EKOConsole;
import eko.EKOCouleur;

import java.util.ArrayList;

public abstract class Ghost extends Enemy {

    //Attributs
    private static final String icon = "\uEEFE";
    private long waitBeforeMoving = 0;
    private int moveIncrementation = 1;
    /*
    moveIncrementation permet de controler l'incrementation du deplacement du fantome (+1 == go down, -1 == go up).
    J'ai essaye de declarer et d'initialiser a l'interieur de la methode mettreAJour, mais il n'y avait aucun effet
    sur le deplacement du fantome
     */

    /**
     * Constructeur de la classe de type Ghost
     * @param name Nom donne a l'objet de type Ghost
     * @param x Position x de l'objet de type Ghost
     * @param y Position y de l'objet de type Ghost
     * @param enemyType Etiquette pour le type Ghost
     */
    public Ghost(String name, int x, int y, Etiquette enemyType) {
        super(name, x, y, enemyType);
    }

    /*
    Ca m'a permis de developper la logique pour le deplacement sur l'axe X et Y, defini par chaque sous-class de Ghost
    https://stackoverflow.com/questions/50091790/how-do-i-make-an-object-move-horizontally
     */

    /**
     * Method qui met a jour le deplacement de l'objet de type Ghost
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {
        //Buffer qui permet de ralentir le mettreAJour
        waitBeforeMoving += deltaTemps;
        if(waitBeforeMoving < 35) {
            return;
        }
        waitBeforeMoving = 0;

        int nextMoveX = position.x;
        int nextMoveY = position.y;

        if(moveOnXAxis()) {
            nextMoveX += moveIncrementation;
        } else {
            nextMoveY += moveIncrementation;
        }

        if (!HitAWall.didWeHitAWall(nextMoveX, nextMoveY)) {
            position.x = nextMoveX;
            position.y = nextMoveY;
        } else {
            moveIncrementation *= -1;
        }
    }

    /**
     * Method qui permet de determiner si l'objet de type Ghost se deplace sur l'axe des X ou sur l'axe des Y
     * @return Boolean qui permet de determiner l'axe de deplacement
     */
    protected abstract boolean moveOnXAxis();

    /**
     * Method qui permet de dessiner l'objet de type Ghost lors de l'actualisation du niveau de jeu
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.BLANC);
    }
}
