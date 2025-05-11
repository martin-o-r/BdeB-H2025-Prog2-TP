public class GhostVertical extends Ghost{

    /**
     * Constructeur qui permet d'instancier un objet de type ghostVertical
     * @param x Position X de l'objet
     * @param y Position Y de l'objet
     */
    public GhostVertical(int x, int y) {
        super("GhostVertical", x, y);
    }

    /**
     * Méthode qui détermine l'axe déplacement de l'objet ghostVertical
     * @return Boolean qui identifie l'axe Y pour le deplacement de l'objet
     */
    @Override
    protected boolean moveOnXAxis() {
        return false;
    }


}


