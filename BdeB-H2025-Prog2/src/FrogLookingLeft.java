import eko.EKOConsole;

public class FrogLookingLeft extends Frog {

    /**
     * Constructeur de la grenouille qui regarde vers la gauche
     * @param x Position X de la grenouille qui regarde vers la gauche
     * @param y Position Y de la grenouille qui regarde vers la gauche
     */
    public FrogLookingLeft(int x, int y) {
        super(x, y);

        //On retourne les characteres pour qu'ils regardent vers la gauche
        ICON.retourner();
        TONGUE1.retourner();
        TONGUE2.retourner();
    }

    /**
     * Méthode qui permet de déterminer l'orientation de la grenouille
     * @return Boolean qui détermine l'orientation
     */
    @Override
    protected boolean facingRight() {
        return false;
    }

}
