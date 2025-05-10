public abstract class Enemy extends ObjetJeu {

    /**
     * Constructeur des objets avec Etiquette.ENEMY
     * @param name Nom donne a l'ennemi
     * @param x Positon X de l'objet ennemi
     * @param y Position Y de l'objet ennemi
     * @param enemyType Etiquette de l'enemi
     */
    public Enemy(String name, int x, int y, Etiquette enemyType) {
        super(name, x, y, enemyType);
    }

    /**
     * Méthode qui permet de mettre à jour chaque ennemi crée
     * @param deltaTemps Temps écoulé (en millisecondes) depuis la dernière trame
     */
    @Override
    protected void mettreAJour(long deltaTemps) {}

    /**
     * Méthode qui permet d'afficher les icones de chaque ennemi
     */
    @Override
    protected void dessiner() {}


}
