public abstract class Items extends ObjetJeu {

    /**
     * Constructeur pour instancier un ObjetJeu
     * @param nom Nom que portera l'objet de type Item
     * @param x Position X de l'objet
     * @param y Position Y de l'objet
     * @param itemType Etiquette de l'objet
     */
    public Items(String nom , int x, int y, Etiquette itemType) {
        super(nom, x, y, itemType);
    }

    /**
     * Method que chaque sous-classes de Items devra implementer pour mettre a jour
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Method qui permet d'afficher l'icone de l'objet de type Items
     */
    @Override
    protected void dessiner() {}


}
