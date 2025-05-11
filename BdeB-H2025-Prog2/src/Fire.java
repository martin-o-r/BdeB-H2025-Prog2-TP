import eko.EKOChaine;
import eko.EKOConsole;
import eko.EKOCouleur;

import java.util.Random;

public class Fire extends Enemy {

    //Attributs
    private final EKOChaine FIRE_ICON = new EKOChaine("\uE3BF", EKOCouleur.RVB(255, 92, 0));

    /**
     * Constructeur qui permet d'instancier le feu
     * @param x Position X du feu
     * @param y Position Y du feu
     */
    public Fire(int x, int y) {
        super("Fire enemy", x, y, Etiquette.ENEMY);
    }

    /**
     * Méthode qui permet de mettre à jour le feu (effet d'un feu qui brûle)
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {
        Random random = new Random();
        if (random.nextInt(1, 5 + 1) == 1) {
            FIRE_ICON.retourner();
        }
    }

    /**
     * Méthode qui permet d'afficher l'icone du feu
     */
    @Override
    protected void dessiner() {
        EKOConsole.afficher(position.x, position.y, FIRE_ICON);
        FIRE_ICON.retourner();
    }


}
