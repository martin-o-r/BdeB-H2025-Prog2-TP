import eko.*;

public abstract class GameProgressManager{

    private static ObjetJeu currentScreen;

    //Attributs pour les effets sonores
    private final static EKOSon GAME_WON = EKOAudio.charger("audio/applause2_x.wav");
    private final static EKOSon GAME_LOST = EKOAudio.charger("audio/scream2.wav");
    private final static EKOSon BACKGROUND_MUSIC = EKOAudio.charger("audio/city-bgm-336601.wav");

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
        EKOAudio.jouer(BACKGROUND_MUSIC, true);
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
                EKOAudio.arreter(BACKGROUND_MUSIC);
                EKOAudio.jouer(GAME_WON);
                break;
            case GAME_OVER :
                EKOAudio.arreter(BACKGROUND_MUSIC);
                EKOAudio.jouer(GAME_LOST);
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
