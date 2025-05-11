import eko.EKOConsole;
import eko.EKOCouleur;

public class Key extends Items {

    //Attributs
    private final String ICON = "\uE29B";

    /**
     * Constructeur d'un objet de type Key
     * @param x Position X de la clé
     * @param y Position Y de la clé
     */
    public Key(int x, int y) {
        super("Key", x, y, Etiquette.KEY);
    }

    /**
     * Méthode qui permet d'afficher l'icone d'objet de type Key
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, ICON, EKOCouleur.ROUGE);
    }

}
