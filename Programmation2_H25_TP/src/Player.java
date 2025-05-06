import eko.*;

public class Player extends ObjetJeu implements Collisionnable{

    //Attributs pour les sons lors des interactions avec d'autres objets
    private static EKOSon KEY_CAPTURED = EKOAudio.charger("audio/404359__kagateni__success2_cut.wav");
    private static EKOSon ENEMY_TOUCHED = EKOAudio.charger("audio/651625__martcraft__fail_cut.wav");
    private static EKOSon POTION_DRINKED = EKOAudio.charger("audio/41529__jamius__potiondrinklong.wav");

    //Attributs propre a l'objet
    private static final String icon = "\uEF0C";

    /**
     * Constructeur de l'objet de type Player
     * @param x Position X du joueur
     * @param y Position Y du joueur
     */
    public Player(int x, int y) {
        super("Player", x, y, Etiquette.PLAYER);
    }

    /**
     * Method qui permet de controler le deplacement du joeur a l'interieur du jeu
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

        boolean hitSomething = false;

        ObjetJeu entryDoorTemp = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Entry door");
        int entryDoorX = entryDoorTemp.getX();
        int entryDoorY = entryDoorTemp.getY();

        ObjetJeu exitDoorTemp = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Exit door");
        int exitDoorX = exitDoorTemp.getX();
        int exitDoorY = exitDoorTemp.getY();


        hitSomething = HitAWall.didWeHitAWall(nextMoveX, nextMoveY);

        if ((entryDoorX == nextMoveX && entryDoorY == nextMoveY) || //joueur ne peut pas traverser la porte d'entree
                (ExitDoor.isDoorLocked() && (exitDoorX == nextMoveX && exitDoorY == nextMoveY))) {
                //joueur ne pas traverser la porte de sortie si elle n'est pas deverouillee
            hitSomething = true;
        }

        if (!hitSomething) {
            position.x = nextMoveX;
            position.y = nextMoveY;
            Skeleton.getPlayerPosition(position.x, position.y);
            //permettre au Skeleton de connaitre la position du joueur
        }

    }

    /**
     * Method qui permet d'afficher l'icon du joueur
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, icon, EKOCouleur.RVB(255,228,133));
        //couleur mocassin : FFE485 ou (255,228,133)
    }

    /**
     * Method qui permet de gerer les collisions
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
//            case Etiquette.LANGUE :
//                //code detecter perdre vie
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
                GameProgressManager.next(GameProgressManager.getCurrentScreen().etiquette);
                break;
            default :
                break;
        }
    }
}
