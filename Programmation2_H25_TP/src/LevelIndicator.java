import eko.EKOConsole;
import eko.EKOCouleur;

public class LevelIndicator extends ObjetJeu{

    //Attributs
    private int levelIndicator;

    /**
     * Constructor qui permet d'instancier l'indicateur de niveau
     * @param x Position X de l'indicateur
     * @param y Position Y de l'indicateur
     * @param level Niveau (type int) qu'il va falloir afficher par apres
     */
    public LevelIndicator(int x, int y, int level) {
        super("Level indicator", x, y, Etiquette.LEVEL_INDICATOR);
        this.levelIndicator = level;
    }

    /**
     * Methode non implementee, car un objet de type LevelIndicator ne se deplace pas
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Methode qui permet l'affichage du LevelIndicator
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, "Salle " + String.format("%02d", levelIndicator), EKOCouleur.CYAN);
    }



}
