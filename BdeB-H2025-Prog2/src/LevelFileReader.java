import eko.EKOConsole;
import eko.EKOCouleur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
Plus d'information sur la méthode File.readAllLines()
    https://medium.com/@AlexanderObregon/javas-files-readalllines-method-explained-14312314c1c4
 */

public class LevelFileReader extends ObjetJeu{

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
        loadLevel();
        EKOConsole.couleurFond(EKOCouleur.RVB(28, 28, 28));
    }

    /**
     * Méthode qui permet de remplir la liste everyGameObjects avec les objets du jeu d'un niveau
     * @param levelPathFile Chemin vers les fichiers textes des niveaux
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
     * Méthode qui permet d'instancier les ObjetJeux et les placer dans le niveau
     * niveau
     */
    public void loadLevel() {

        //type static qui suit le joueur au courant de la progression
        everyGameObjects.add(new LifeIndicator(0,0));

        int y = 0; //coordonées y des objets qui seront créés
        for (String s : lines) {
            y++;
            for (int i = 0; i < s.length(); i++) { //index i utilisé pour la coordonée x des objets

                ObjetJeu gameObject = switch (s.charAt(i)) { //tous les objets de jeu héritent de ObjetJeu
                    case '#' -> new Wall(i, y);
                    case 'P' -> new Player(i, y);
                    case '+' -> new EntryDoor(i, y);
                    case '-' -> new ExitDoor(i, y);
                    case 'K' -> new Key(i, y);
                    case 'F' -> new Fire(i, y);
                    case 'L' -> new Potion(i, y);
                    case 'G' -> new GhostVertical(i, y);
                    case 'H' -> new GhostHorizontal(i, y);
                    case 'S' -> new Skeleton(i, y);
                    case 'I' -> new Insect(i, y);
                    case 'C' -> new FrogLookingRight(i, y);
                    case 'V' -> new FrogLookingLeft(i, y);
                    default -> null;
                };

                if (gameObject != null) {
                    everyGameObjects.add(gameObject);
                }
            }
        }
    }

    /**
     * Méthode qui permet de déterminer si le jeu est fini quand le joueur a perdu
     * toutes ses vies
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
     * Méthode qui est redéfinie pour détruire tous les objets inclus dans un niveau ainsi
     * que l'objet niveau lui-meme
     */
    @Override
    public void detruire() {
        for (ObjetJeu object : everyGameObjects) {
            object.detruire();
        }
        super.detruire();
    }

    /**
     * Méthode qui permet d'afficher le niveau actuel
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(EKOConsole.largeur() - 8, 0, "Salle " + String.format("%02d", levelCounter),
                EKOCouleur.CYAN);
    }

    //Accesseurs
    public String getLevelName() { return levelName; }
    public String getLevelFilePath() { return levelFilePath; }
    public Etiquette getEtiquette() { return etiquette; }
    public int getLevelCounter() { return levelCounter; }
}
