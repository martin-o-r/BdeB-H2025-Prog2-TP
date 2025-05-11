import eko.EKOConsole;
import eko.EKOCouleur;

/**
 * Aide pour développer la logique pour le déplacement sur l'axe X et Y, qui est défini dans chaque sous-class de Ghost
 *  https://stackoverflow.com/questions/50091790/how-do-i-make-an-object-move-horizontally
 */

public abstract class Ghost extends Enemy {

    //Attributs
    private static final String icon = "\uEEFE";
    private long waitBeforeMoving = 0;
    private final long MAX_WAITING = 50;
    private int moveIncrementation = 1;
    /*
    moveIncrementation permet de contrôler l'incrémentation du déplacement du fantome (+1 == go down, -1 == go up).
    J'ai essayé de déclarer et d'initialiser à l'interieur de la méthode mettreAJour, mais il n'y avait aucun effet
    sur le déplacement du fantôme
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

    /**
     * Méthode qui met à jour le déplacement de l'objet de type Ghost
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {
        //Buffer qui permet de ralentir le mettreAJour
        waitBeforeMoving += deltaTemps;
        if(waitBeforeMoving < MAX_WAITING) {
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

        if (!HitSomething.didWeHitAWall(nextMoveX, nextMoveY) &&
            !HitSomething.didWeHitADoor(nextMoveX, nextMoveY)) {
            position.x = nextMoveX;
            position.y = nextMoveY;
        } else {
            moveIncrementation *= -1;
        }
    }

    /**
     * Méthode qui permet de déterminer si l'objet de type Ghost se déplace sur l'axe des X ou sur l'axe des Y
     * @return Boolean qui permet de determiner l'axe de deplacement
     */
    protected abstract boolean moveOnXAxis();

    /**
     * Méthode qui permet de dessiner l'objet de type Ghost lors de l'actualisation du niveau de jeu
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.BLANC);
    }
}
