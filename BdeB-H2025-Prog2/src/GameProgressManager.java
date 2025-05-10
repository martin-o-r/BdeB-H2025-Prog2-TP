import eko.*;

public abstract class GameProgressManager{

    private static ObjetJeu currentScreen;

    /**
     * Méthode qui initialise la console EKO
     */
    public static void initialiseEKO() {
        EKO.initialiser("Bondage and Master", 61, 21);
        EKOConsole.rendre();

        playSoundtrack();
    }

    /**
     * Méthode qui débute la musique de fond qui jouera en boucle tout au long du déroulement du jeu
     */
    private static void playSoundtrack() {
        EKOSon backgroundMusic = EKOAudio.charger("audio/685349__zhr__exploration-music-loop_low.wav");
        EKOAudio.jouer(backgroundMusic, true);
    }

    /**
     * Début du déroulement du jeu. Il commence avec l'affichage de l'auteur, du nom du jeu et des instructions
     */
    public static void startGame() {
        currentScreen = new Author();
    }

    /**
     * Méthode qui permet de gérer le sequencage des écrans intro/niveaux/outro
     * @param etiquette Identifie l'étiquette du niveau/page actuel
     */
    public static void next(Etiquette etiquette) {

        switch(etiquette) {
            case AUTHOR :
                updateScreen(new GameTitle());
                break;
            case GAME_TITLE :
                updateScreen(new GameInstructions());
                break;
            case GAME_INSTRUCTIONS :
                updateScreen(new LevelFileReader("Level1","levels/level_01", Etiquette.LEVEL1, 1));
                break;
            case LEVEL1 :
                updateScreen(new LevelFileReader("Level2", "levels/level_02", Etiquette.LEVEL2, 2));
                break;
            case LEVEL2 :
                updateScreen(new LevelFileReader("Level3", "levels/level_03", Etiquette.LEVEL3, 3));
                break;
            case LEVEL3 :
                updateScreen(new LevelFileReader("Level4", "levels/level_04", Etiquette.LEVEL4, 4));
                break;
            case LEVEL4 :
                updateScreen(new YouWon());
                break;
            case GAME_OVER :
                updateScreen(new YouDied());
                break;
            default :
                break;
        }
    }

    /**
     * Méthode qui permet de détruire le currentScreen puis de creer le prochain écrans/niveaux
     * @param newScreen
     */
    private static void updateScreen(ObjetJeu newScreen) {
        //Détruire la page précédente
        currentScreen.detruire(); //les niveaux ont leur propre redéfinition pour .detruire()

        ExitDoor.lockDoor();

        //Réassigner currentScreen à la nouvelle page (intro ou niveau)
        currentScreen = newScreen;
    }

    /**
     * Méthode qui permet de réinitialiser un niveau lorsqu'un jouer entre en contacte avec un ennemie.
     * On efface l'efface l'ecran actuel puis on le 'recharche'
     */
    public static void restartLevel() {
        currentScreen.detruire(); //on efface le niveau qui est affiché

        ExitDoor.lockDoor(); //on s'assure que la porte de sortie soit verouillée à niveau

        if (currentScreen instanceof LevelFileReader) { //on teste si l'ecran actuel est un niveau pour rappeler les
            // memes parametres
            updateScreen(new LevelFileReader(((LevelFileReader) currentScreen).getLevelName(),
                    ((LevelFileReader) currentScreen).getLevelFilePath(),
                    ((LevelFileReader) currentScreen).getEtiquette(),
                    ((LevelFileReader) currentScreen).getLevelCounter()));
        }
    }

    //Accesseur
    public static ObjetJeu getCurrentScreen() { return currentScreen; }

}
