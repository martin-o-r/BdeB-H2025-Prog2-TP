import eko.*;

public class Player extends ObjetJeu implements Collisionnable{

    //Attributs pour les sons lors des intéractions avec d'autres objets
    private static final EKOSon KEY_CAPTURED = EKOAudio.charger("audio/404359__kagateni__success2_cut.wav");
    private static final EKOSon ENEMY_TOUCHED = EKOAudio.charger("audio/651625__martcraft__fail_cut.wav");
    private static final EKOSon POTION_DRINKED = EKOAudio.charger("audio/41529__jamius__potiondrinklong.wav");
    private static final EKOSon EXIT_DOOR = EKOAudio.charger("audio/404358__kagateni__success_cut.wav");

    //Attributs propre a l'objet
    private static final String ICON = "\uEF0C";

    /**
     * Constructeur de l'objet de type Player
     * @param x Position X du joueur
     * @param y Position Y du joueur
     */
    public Player(int x, int y) {
        super("Player", x, y, Etiquette.PLAYER);
    }

    /**
     * Méthode qui permet de contrôler le déplacement du joeur à l'intérieur du jeu
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {

        int nextMoveX = position.x;
        int nextMoveY = position.y;

        if (EKOTouche.FLECHE_DROITE.estEnfoncee() || EKOTouche.D.estEnfoncee()) {
            nextMoveX++;
        } else if (EKOTouche.FLECHE_GAUCHE.estEnfoncee() || EKOTouche.A.estEnfoncee()) {
            nextMoveX--;
        } else if (EKOTouche.FLECHE_HAUT.estEnfoncee() || EKOTouche.W.estEnfoncee()) {
            nextMoveY--;
        } else if (EKOTouche.FLECHE_BAS.estEnfoncee() || EKOTouche.S.estEnfoncee()) {
            nextMoveY++;
        }

        ObjetJeu entryDoorTemp = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Entry door");
        int entryDoorX = entryDoorTemp.getX();
        int entryDoorY = entryDoorTemp.getY();

        ObjetJeu exitDoorTemp = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Exit door");
        int exitDoorX = exitDoorTemp.getX();
        int exitDoorY = exitDoorTemp.getY();

        boolean hitSomething = HitSomething.didWeHitAWall(nextMoveX, nextMoveY);

        if ((entryDoorX == nextMoveX && entryDoorY == nextMoveY) || //joueur ne peut pas traverser la porte d'entree
                (ExitDoor.isDoorLocked() && (exitDoorX == nextMoveX && exitDoorY == nextMoveY))) {
            //joueur ne pas traverser la porte de sortie si elle n'est pas deverouillée
            hitSomething = true;
        }

        if (!hitSomething) {
            position.x = nextMoveX;
            position.y = nextMoveY;

            //permettre au Skeleton de connaitre la position du joueur
            Skeleton.getPlayerPosition(position.x, position.y);
        }
    }

    /**
     * Méthode qui permet d'afficher l'icone du joueur
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, ICON, EKOCouleur.RVB(255,158,158));
    }

    /**
     * Méthode qui permet de gérer les collisions
     * @param autre Autre objet de jeu impliqué dans la collision
     */
    @Override
    public void gererCollisionAvec(ObjetJeu autre) {

        switch (autre.etiquette) {
            case Etiquette.KEY :
                autre.desactiver();
                ExitDoor.unlockDoor();
                EKOAudio.jouer(KEY_CAPTURED);
                break;
            case Etiquette.ENEMY :
                LifeIndicator.looseALife();
                EKOAudio.jouer(ENEMY_TOUCHED);
                GameProgressManager.restartLevel();
                break;
            case Etiquette.POTION :
                autre.desactiver();
                EKOAudio.jouer(POTION_DRINKED);
                LifeIndicator.refuelLife();
                break;
            case Etiquette.EXIT_DOOR :
                EKOAudio.jouer(EXIT_DOOR);
                GameProgressManager.next(GameProgressManager.getCurrentScreen().etiquette);
                break;
            default :
                break;
        }
    }
}
