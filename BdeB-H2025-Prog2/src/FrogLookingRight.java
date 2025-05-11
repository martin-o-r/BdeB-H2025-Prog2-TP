public class FrogLookingRight extends Frog{

    /**
     * Constructeur de la grenouille qui regarde vers la droite
     * @param x Position X de la grenouille qui regarde vers la droite
     * @param y Position Y de la grenouille qui regarde vers la gauche
     */
    public FrogLookingRight(int x, int y) {
        super("Frog looking right",x, y);
    }

    /**
     * Méthode qui permet de déterminer l'orientation de la grenouille
     * @return Boolean qui détermine l'orientation
     */
    @Override
    protected boolean facingRight() {
        return true;
    }


}
