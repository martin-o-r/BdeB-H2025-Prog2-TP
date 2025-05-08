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
        -> lorsque colision avec la porte, l'insecte devrait suivre la regle de main droite
     */

    private final String icon = "\uF188";
    private long waitBeforeMoving = 0;
    private InsectDirections direction = InsectDirections.RIGHT; //determine le sens de deplacement

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

        ObjetJeu exitDoor = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Exit door");
        int exitDoorX = exitDoor.getX();
        int exitDoorY = exitDoor.getY();

        //lorsque le prochain mouvement est une colision avec la porte de sortie, l'insecte devrait implementer la
        // regle de la main droite, pour faire demi-tour
        if (nextX == exitDoorX && nextY == exitDoorY) {
            reverseDirection();

            nextX = position.x;
            nextY = position.y;

            switch (direction) {
                case UP -> nextY++;
                case DOWN -> nextY--;
                case LEFT -> nextX++;
                case RIGHT -> nextX--;
            }
        }

        if (isNextMoveValid(nextX, nextY)) { //verification pour ne pas sortir du perimetre du jeu/tableau
            position.x = nextX;
            position.y = nextY;

            //verifier s'il y un mur a gauche pour continuer a longer les mur - "left-hand rule"
            testAndAdjustRoad();
        } else {
            changeDirection(); //s'il y aun mur a gauche et devant, alors on doit tourner a gauche
        }
    }

    /**
     * Method qui verifie si la prochaine position de deplacement est valide
     * versus un mur, la porte de sortie ou la porte d'entree
     * @param x prochaine position x de l'insecte
     * @param y prochaine position y de l'insecte
     * @return boolean qui determine si l'insecte peut avancer ou non
     */
    private boolean isNextMoveValid(int x, int y) {

        if (HitAWall.didWeHitAWall(x, y)) {
            return false;
        }

        ObjetJeu exitDoor = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Exit door");
        int exitDoorX = exitDoor.getX();
        int exitDoorY = exitDoor.getY();

        if (x == exitDoorX && y == exitDoorY) {
            return false;
        }

        ObjetJeu entryDoor = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Entry door");
        int entryDoorX = entryDoor.getX();
        int entryDoorY = entryDoor.getY();

        if (x == entryDoorX && y == entryDoorY) {
            return false;
        }

        return true;
    }

    /**
     * Method qui permet a l'insecte de rebrousser chemin lorsque le prochain deplacement est avec la porte de sortie
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
     * Methode qui Verifie s'il y un mur a gauche par rapport a la direction de l'insecte et ajustee la trajectoire
     * pour continuer a longer le long du mur
     */
    private void testAndAdjustRoad() {
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

        //si mur est a gauche, on continue a avancer
        if (!isNextMoveValid(xLeft, yLeft)) {
            return;
        }

        //si pas de mur a gauche, on tourne a gauche pour continuer a longer un mur
        direction = leftDirection;
    }

    /**
     * Method qui change de direction le deplacement de l'insecte s'il y a un mur a gauche et devant
     */
    private void changeDirection() {
        direction = getRightDirection();
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
