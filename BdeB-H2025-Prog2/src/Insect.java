import eko.EKOConsole;
import eko.EKOCouleur;

public class Insect extends Enemy{

    /*
    Tentative d'implementer l'algorithme 'left hand rule' -> Insecte se base sur cet "algorithme"
    https://stackoverflow.com/questions/4362657/solving-a-maze-using-the-left-hand-rule
    https://www.instructables.com/Robot-Maze-Solver/
        -->"Left-hand rule" permet de franchir un labyrinthe en choisisant comment tourner aux intersections
            --> toujours tourner a gauche si on peut (valider prochain mouvement)
            --> si on ne peut pas tourner a gauche:
                --> aller tout droit
                --> ou aller tout droit, alors tourner a droite
                --> aller tout droit et tourner a gauche, alors faire demi-tour (pas besoin d'implementer cette partie)
    https://www.youtube.com/watch?v=PrUjjPVVT6s&ab_channel=RocketsandRobotics
    https://stackoverflow.com/questions/58869848/questions-about-right-hand-rule-maze-solver
        -> Enum pour determiner un sens de deplacement e.g. RIGHT implique position.x++
    https://www.reddit.com/r/explainlikeimfive/comments/1g4lji/eli5_left_hand_wall_of_a_maze/
    https://en.wikipedia.org/wiki/Maze-solving_algorithm
    Enseignant :
        -> commencer par la regle de la main gauche, se deplacer dans un sens e.g. RIGHT (sens de deplacement initial)
        -> lorsqu'il y a une colision avec la porte, l'insecte devrait suivre la regle de main droite
     */

    private final String icon = "\uF188";
    private long waitBeforeMoving = 0;
    private InsectDirections direction = InsectDirections.RIGHT; //determine le sens de deplacement
    private boolean lefthandRule = true; //determiner "quelle main utiliser"

    /**
     * Constructeur de l'objet de type Insect
     * @param x positon x de depart
     * @param y position y de depart
     */
    public Insect(int x, int y) {
        super("Insect", x, y, Etiquette.ENEMY);
    }

    /**
     * Method qui permet d'actualiser le deplacement de l'insecte, soit longer les murs et rebrousser son chemin
     * lorsqu'il se retrouve devant la porte de sortie verouillee
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {
        //buffer pour le deplacement de l'insecte
        waitBeforeMoving += deltaTemps;
        if(waitBeforeMoving < 50) {
            return;
        }
        waitBeforeMoving = 0;

        int nextX = position.x;
        int nextY = position.y;

        //Insecte commence son deplacement en allant par la droite position.x++

        switch (direction) {
            case UP -> nextY--;
            case DOWN -> nextY++;
            case LEFT -> nextX--;
            case RIGHT -> nextX++;
        }

        handDecisionMaker(nextX, nextY);

        if (lefthandRule) {

            if (!HitSomething.didWeHitAWall(nextX, nextY) &&
                !HitSomething.didWeHitADoor(nextX, nextY)) {

                position.x = nextX;
                position.y = nextY;

                //vérifier s'il y un mur a gauche pour continuer à longer les mur - "left-hand rule"
                goLeftHandRule();
            } else {
                changeDirectionLHR(); //s'il y a un mur à gauche et devant, alors on doit tourner a gauche
            }

        } else {

            if (!HitSomething.didWeHitAWall(nextX, nextY) &&
                !HitSomething.didWeHitADoor(nextX, nextY)) {

                position.x = nextX;
                position.y = nextY;

                //verifier s'il y un mur a groite pour continuer a longer les mur - "left-hand rule"
                goRightHandRule();
            } else {
                changeDirectionRHR(); //s'il y a un mur a gauche et devant, alors on doit tourner a gauche
            }
        }

    }

    /**
     * Methode qui permet de determiner quelle "main" suivre pour longer le mur
     * @param x Position X de l'objet
     * @param y Position Y de lobjet
     */
    private void handDecisionMaker(int x, int y) {
        /*
        Logique pour l'effet "toggle" avec un boolean
        https://stackoverflow.com/questions/224311/cleanest-way-to-toggle-a-boolean-variable-in-java
            ->  un boolean va prendre la valeur inverse du lui-meme
         */

        if (didWeHitAroundExitDoor(x, y)) {
            lefthandRule = !lefthandRule;
            reverseDirection();
        }
    }

    /**
     * Méthode qui permet a l'insecte de se tourner 180 degrés
     */
    private void reverseDirection() {
        switch (direction) {
            case UP -> direction = InsectDirections.DOWN;
            case DOWN -> direction = InsectDirections.UP;
            case RIGHT -> direction = InsectDirections.LEFT;
            case LEFT -> direction = InsectDirections.RIGHT;
        }
    }

    /**
     * Méthode qui permet de déterminer si l'insecte entre en collision avec la péripherie de la porte de sortie
     * @param x Position X de l'insecte
     * @param y Position Y de l'insecte
     * @return boolean qui determine si l'insecte collision avec la periripherie de la porte de sortie
     */
    private boolean didWeHitAroundExitDoor(int x, int y) {
        ObjetJeu exitDoor = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Exit door");
        int exitDoorX = exitDoor.getX();
        int exitDoorY = exitDoor.getY();

        //determine si on collision avec la "peripherie" de la porte
        if ((x == exitDoorX && y == exitDoorY -1) || //on verifie le haut de la porte
                (x == exitDoorX && y == exitDoorY + 1) || //on verifie le bas
                (x == exitDoorX + 1 && y == exitDoorY) || //on verigie a droite
                (x == exitDoorX -1 && y == exitDoorY)) { // on verifie a gauche
            return true;
        }
        return false;
    }

    /**
     * Methode qui verifie s'il y un mur a gauche, par rapport a la direction de l'insecte et ajuste la trajectoire
     * pour continuer a longer le mur
     */
    private void goLeftHandRule() {
        InsectDirections leftDirection = getLeftDirection();
        int xLeft = position.x;
        int yLeft = position.y;

        //caluler la position a gauche de la direction actuelle
        switch (leftDirection) {
            case UP -> yLeft--;
            case DOWN -> yLeft++;
            case LEFT -> xLeft--;
            case RIGHT -> xLeft++;
        }

        /*
        Ici, on teste si le prochain deplacement a gauche est possible.
        Si ce n'est pas possible (false), alors on "ne tourne pas" (true). On sort de la methode.
        Si c'est possible (true), alors on tourne (false - permet de changer la valeur direction).
         */
        if (HitSomething.didWeHitAWall(xLeft, yLeft) ||
            HitSomething.didWeHitADoor(xLeft, yLeft)) {
            return;
        }

        direction = leftDirection;
    }

    /**
     * Method qui change de direction le deplacement de l'insecte s'il y a un mur a droite et devant
     */
    private void changeDirectionLHR() {
        direction = getRightDirection();
    }

    /**
     * Methode qui verifie s'il y un mur a droite, par rapport a la direction de l'insecte et ajuste la trajectoire
     * pour continuer a longer le mur
     */
    private void goRightHandRule() {
        InsectDirections rightDirection = getRightDirection();
        int xRight = position.x;
        int yRight = position.y;

        //caluler la position a droite de la direction actuelle
        switch (rightDirection) {
            case UP -> yRight--;
            case DOWN -> yRight++;
            case LEFT -> xRight--;
            case RIGHT -> xRight++;
        }

        //si mur est a droite, on continue a avancer
        if (HitSomething.didWeHitAWall(xRight, yRight) ||
            HitSomething.didWeHitADoor(xRight, yRight)) {
            return;
        }


        //si pas de mur a droit, on tourne a droite pour continuer a longer un mur
        direction = rightDirection;
    }

    private void changeDirectionRHR() {
        direction = getLeftDirection();
    }

    /**
     * Method qui retourne la position a gauche de la direction actuelle de l'insecte
     * @return la direction a gauche a prendre
     */
    private InsectDirections getLeftDirection() {
        switch (direction) {
            case UP :
                return InsectDirections.LEFT;
            case LEFT :
                return InsectDirections.DOWN;
            case DOWN :
                return InsectDirections.RIGHT;
            case RIGHT :
                return InsectDirections.UP;
        }
        return null;
    }

    /**
     * Method qui retourne la position a droite de la direction actuelle de l'insecte
     * @return la direction a droite a prendre
     */
    private InsectDirections getRightDirection() {
        switch (direction) {
            case UP :
                return InsectDirections.RIGHT;
            case RIGHT :
                return InsectDirections.DOWN;
            case DOWN :
                return InsectDirections.LEFT;
            case LEFT :
                return InsectDirections.UP;
        }
        return null;
    }

    /**
     * Permet l'affichage de l'insecte
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.RVB(204,153, 255));
    }
}
