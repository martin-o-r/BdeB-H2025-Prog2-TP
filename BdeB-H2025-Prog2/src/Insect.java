import eko.EKOConsole;
import eko.EKOCouleur;

public class Insect extends Enemy{

    /*
    Tentative d'implementer l'algorithme 'left hand rule' -> Insecte se base sur cet "algorithme"
    https://stackoverflow.com/questions/4362657/solving-a-maze-using-the-left-hand-rule
    https://www.instructables.com/Robot-Maze-Solver/
        -->"Left-hand rule" permet de franchir un labyrinthe en choisisant comment tourner aux intersections
            --> toujours tourner à gauche si on peut (valider prochain mouvement!!)
            --> si on ne peut pas tourner à gauche... :
                --> aller tout droit
                --> ou aller tout droit, alors tourner à droite
                --> aller tout droit et tourner à gauche, alors faire demi-tour (methode reverse?)
    https://www.youtube.com/watch?v=PrUjjPVVT6s&ab_channel=RocketsandRobotics
    https://stackoverflow.com/questions/58869848/questions-about-right-hand-rule-maze-solver
        -> Enum pour déterminer un sens de déplacement e.g. RIGHT implique position.x++
    https://www.reddit.com/r/explainlikeimfive/comments/1g4lji/eli5_left_hand_wall_of_a_maze/
    https://en.wikipedia.org/wiki/Maze-solving_algorithm
    Enseignant :
        -> commencer par la règle de la main gauche, se déplacer dans un sens e.g. RIGHT (sens de déplacement initial)
        -> lorsqu'il y a une colision avec la porte, l'insecte devrait suivre la regle de main droite (changer de
            direction)
     */

    //Attributs
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
     * Méthode qui permet d'actualiser le déplacement de l'insecte, soit longer les murs et rebrousser son chemin
     * lorsqu'il se retrouve devant la porte de sortie verouillée...?
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

        //Insecte commence son déplacement en allant par la droite position.x++

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
     * Méthode qui permet de déterminer quelle "main" utiliser pour longer le mur
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
     * Méthode qui permet à l'insecte de se tourner 180 degrés
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
     * Méthode qui permet de déterminer si l'insecte entre en collision avec la périphérie de la porte de sortie
     * @param x Position X de l'insecte
     * @param y Position Y de l'insecte
     * @return boolean qui determine si l'insecte collision avec la periripherie de la porte de sortie
     */
    private boolean didWeHitAroundExitDoor(int x, int y) {
        ObjetJeu exitDoor = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Exit door");
        int exitDoorX = exitDoor.getX();
        int exitDoorY = exitDoor.getY();

        //détermine si on collisionne avec la "périphérie" de la porte
        if ((x == exitDoorX && y == exitDoorY -1) || //on verifie le haut de la porte
                (x == exitDoorX && y == exitDoorY + 1) || //on verifie le bas
                (x == exitDoorX + 1 && y == exitDoorY) || //on verigie a droite
                (x == exitDoorX -1 && y == exitDoorY)) { // on verifie a gauche
            return true;
        }
        return false;
    }

    /**
     * Méthode qui vérifie s'il y un mur à gauche, par rapport à la direction de l'insecte et ajuste la trajectoire
     * pour continuer a longer le mur
     */
    private void goLeftHandRule() {
        InsectDirections leftDirection = getLeftDirection();
        int xLeft = position.x;
        int yLeft = position.y;

        //caluler la position à gauche de la direction actuelle
        switch (leftDirection) {
            case UP -> yLeft--;
            case DOWN -> yLeft++;
            case LEFT -> xLeft--;
            case RIGHT -> xLeft++;
        }

        //Si un mur est à gauche, on continue à avancer
        if (HitSomething.didWeHitAWall(xLeft, yLeft) ||
            HitSomething.didWeHitADoor(xLeft, yLeft)) {
            return;
        }

        direction = leftDirection;
    }

    /**
     * Méthode qui change de direction le déplacement de l'insecte s'il y a un mur à gauche et devant
     */
    private void changeDirectionLHR() {
        direction = getRightDirection();
    }

    /**
     * Méthode qui vérifie s'il y a un mur à droite, par rapport à la direction de l'insecte et ajuste la trajectoire
     * pour continuer a longer le mur
     */
    private void goRightHandRule() {
        InsectDirections rightDirection = getRightDirection();
        int xRight = position.x;
        int yRight = position.y;

        //caluler la position à droite de la direction actuelle
        switch (rightDirection) {
            case UP -> yRight--;
            case DOWN -> yRight++;
            case LEFT -> xRight--;
            case RIGHT -> xRight++;
        }

        //si un mur est à droite, on continue à avancer
        if (HitSomething.didWeHitAWall(xRight, yRight) ||
            HitSomething.didWeHitADoor(xRight, yRight)) {
            return;
        }


        //si pas de mur a droit, on tourne a droite pour continuer a longer un mur
        direction = rightDirection;
    }

    /**
     * Méthode qui change de direction le déplacement de l'insecte s'il y a un mur à droite et devant
     */
    private void changeDirectionRHR() {
        direction = getLeftDirection();
    }

    /**
     * Méthode qui retourne la position à gauche de la direction actuelle de l'insecte
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
     * Méthode qui retourne la position à droite de la direction actuelle de l'insecte
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
     * Permet l'affichage de l'objet insecte
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.RVB(204,153, 255));
    }
}
