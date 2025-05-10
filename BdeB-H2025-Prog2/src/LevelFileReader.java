import eko.EKO;
import eko.EKOConsole;
import eko.EKOCouleur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LevelFileReader extends ObjetJeu{
    /*
    Plus d'information sur la File.readAllLines() method
    https://medium.com/@AlexanderObregon/javas-files-readalllines-method-explained-14312314c1c4
     */

    private String levelName;
    private Etiquette etiquette;
    private String levelFilePath;
    private ArrayList<String> lines;
    private int levelCounter;
    private List<ObjetJeu> everyGameObjects = new ArrayList<>();

    /**
     * Constructeur qui permet d'instancier un niveau
     * @param levelFilePath le nom/chemin vers le fichier du niveau
     */
    public LevelFileReader(String levelName,String levelFilePath, Etiquette etiquette, int levelCounter) {
        super(levelName, 0, 0, etiquette);
        this.levelName = levelName;
        this.etiquette = etiquette;
        this.levelFilePath = levelFilePath;
        this.levelCounter = levelCounter;
        lines = createArrayList(levelFilePath);
        printLevel();
    }

    /**
     * Method qui permet de remplir la liste everyGameObjects avec les objets du jeu
     * @param levelPathFile levels/level_02
     * @return Une liste contenant tous les objets necessaires d'un niveau
     */
    private ArrayList<String> createArrayList(String levelPathFile) {
        Path filePath = Paths.get(levelPathFile);
        ArrayList<String> lines;

        try {
            lines = new ArrayList<>(Files.readAllLines(filePath));
        } catch (IOException e) {
            throw new RuntimeException(levelPathFile + " : Erreur d'ouverture de fichier.");
        }

        return lines;
    }

    /**
     * Method qui permet d'afficher et de mettre a jour chaque niveaux ainsi que les ObjetsJeu necessaires pour un
     * niveau
     */
    public void printLevel() {

        //type static qui suit le joueur au courant de la progression (a travers les niveaux)
        everyGameObjects.add(new LifeIndicator(0,0));

        int y = 0;
        for (String s : lines) {
            y++;
            for (int i = 0; i < s.length(); i++) {

                switch (s.charAt(i)) {
                    case '#' :
                        Wall wall = new Wall(i, y);
                        everyGameObjects.add(wall);
                        break;
                    case 'P' :
                        Player player = new Player(i, y);
                        everyGameObjects.add(player);
                        break;
                    case '+' :
                        EntryDoor entryDoor = new EntryDoor(i, y);
                        everyGameObjects.add(entryDoor);
                        break;
                    case '-' :
                        ExitDoor exitDoor = new ExitDoor(i, y);
                        everyGameObjects.add(exitDoor);
                        break;
                    case 'K' :
                        Key key = new Key(i, y);
                        everyGameObjects.add(key);
                        break;
                    case 'F' :
                        Fire fire = new Fire(i, y);
                        everyGameObjects.add(fire);
                        break;
                    case 'L' :
                        Potion potion = new Potion(i, y);
                        everyGameObjects.add(potion);
                        break;
                    case 'G' :
                        GhostVertical ghostVertical = new GhostVertical(i, y);
                        everyGameObjects.add(ghostVertical);
                        break;
                    case 'H' :
                        GhostHorizontal ghostHorizontal = new GhostHorizontal(i, y);
                        everyGameObjects.add(ghostHorizontal);
                        break;
                    case 'S' :
                        Skeleton skeleton = new Skeleton(i, y);
                        everyGameObjects.add(skeleton);
                        break;
                    case 'I' :
                        Insect insect = new Insect(i, y);
                        everyGameObjects.add(insect);
                        break;
                    case 'R' :
                        Frog frog = new Frog(i, y);
                        everyGameObjects.add(frog);
                        break;
                    default :
                        break;
                }
            }
        }
    }

    /**
     * Redefinition de la methode mettre a jour qui permet de determiner si le jeu est fini quand le joueur a perdu
     * toutes ses vies disponibles.
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {
        if (LifeIndicator.getNbLifeLeft() == 0) {
            GameProgressManager.getCurrentScreen().detruire();
            GameProgressManager.next(Etiquette.GAME_OVER);
        }
    }

    /**
     * Methode qui est redefinie pour detruire tous les objets inclus dans un niveau ainsi
     * que l'objet niveau lui-meme
     */
    @Override
    public void detruire() {
        for (ObjetJeu objects : everyGameObjects) {
            objects.detruire();
        }
        super.detruire();
    }

    /**
     * Method qui permet de dessiner les informations des niveaux
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(EKOConsole.largeur() - 8, 0, "Salle " + String.format("%02d", levelCounter),
                EKOCouleur.CYAN);
    }

    //accesseurs
    public String getLevelName() { return levelName; }
    public String getLevelFilePath() { return levelFilePath; }
    public Etiquette getEtiquette() { return etiquette; }
    public int getLevelCounter() { return levelCounter; }
}
