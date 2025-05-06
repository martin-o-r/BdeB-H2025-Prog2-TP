import java.util.ArrayList;

public abstract class HitAWall {

    /**
     * Method qui permet de determiner si le prochain deplacement d'un objet est le meme que l'emplacement d'un mur
     * @param x Position X d'un objet
     * @param y Position Y d'un objet
     * @return Boolean qui determine si le prochain deplacement d'un objet est possible ou non
     */
    public static boolean didWeHitAWall(int x, int y) {
        ArrayList<ObjetJeu> walls = GestionnaireObjetsJeu.obtenir().trouverObjetsJeu(Etiquette.WALL);

        for (ObjetJeu wall : walls) {
            if (wall.getX() == x && wall.getY() == y) {
                return true;
            }
        }

        return false;
    }
}
