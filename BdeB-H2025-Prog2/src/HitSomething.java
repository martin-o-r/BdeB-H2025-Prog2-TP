import java.util.ArrayList;

public abstract class HitSomething {

    /**
     * Méthode qui permet de déterminer si le prochain déplacement d'un objet entre en collision avec un mur
     * @param x Position X d'un objet
     * @param y Position Y d'un objet
     * @return Boolean qui détermine si le prochain déplacement est valide
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

    /**
     * Méthode qui permet de déterminer si le prochain déplacement d'un objet entre en collision avec une porte
     * d'entrée et/ou une porte de sortie
     * @param x Position X d'un objet
     * @param y Position Y d'un objet
     * @return Boolean qui determine si le prochain déplacement est valide
     */
    public static boolean didWeHitADoor(int x, int y) {
        ObjetJeu entryDoor = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Entry door");
        ObjetJeu exitDoor = GestionnaireObjetsJeu.obtenir().trouverObjetJeu("Exit door");

        if ((entryDoor.getX() == x && entryDoor.getY() == y)
                || (exitDoor.getX() == x && exitDoor.getY() == y)) {
            return true;
        }
        return false;
    }
}
