public class GhostHorizontal extends Ghost{

    /**
     * Constructeur qui permet d'instancier un objet de type ghostHorizontal
     * @param x Position X de lobjet
     * @param y Position Y de l'objet
     */
    public GhostHorizontal(int x, int y) {
        super("GhostHorizontal", x, y);
    }

    /**
     * Méthode qui determine l'axe de placement de l'objet ghostHorizontal
     * @return Boolean qui identifie l'axe X pour le déplacement de l'objet
     */
    @Override
    protected boolean moveOnXAxis() {
        return true;
    }
}
