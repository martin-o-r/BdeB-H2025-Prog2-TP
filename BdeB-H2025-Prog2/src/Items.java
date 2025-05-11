public abstract class Items extends ObjetJeu {

    /**
     * Constructeur pour instancier un Item du jeu.
     * @param nom Nom que portera l'objet de type Item
     * @param x Position X de l'objet
     * @param y Position Y de l'objet
     * @param itemType Etiquette de l'objet
     */
    public Items(String nom , int x, int y, Etiquette itemType) {
        super(nom, x, y, itemType);
    }

    /**
     * Méthode heritée de ObjetJeu, mais ne sera implementée dans chaque sous-classe, car les items Key et Potion n'ont
     * pas besoin de se déplacer
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Méthode que chaque sous-classe devra implementer pour leur affichage respectif
     */
    @Override
    protected void dessiner() {}


}
