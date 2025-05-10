import eko.EKOConsole;

public class FrogLookingRight extends Frog{

    /**
     * Constructeur pour la grenouille qui regarde vers la droite
     * @param x Position X de la grenouille qui regarde vers la droite
     * @param y Position Y de la grenouille qui regarde vers la gauche
     */
    public FrogLookingRight(int x, int y) {
        super(x, y);
    }

    /**
     * Methode qui permet de determiner l'orientation de la grenouille
     * @return Boolean qui determine l'orientation
     */
    @Override
    protected boolean facingRight() {
        return true;
    }


}
