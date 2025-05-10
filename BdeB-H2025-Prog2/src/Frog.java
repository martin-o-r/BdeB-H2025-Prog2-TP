import eko.EKOAudio;
import eko.EKOConsole;
import eko.EKOCouleur;
import eko.EKOSon;

import java.util.ArrayList;
import java.util.List;

public class Frog extends Enemy implements Collisionnable{

    /*
    Logique pour creer une animation en java
    https://www.svetprogramiranja.com/animations_in_java_example.html
        -> besoin d'un timer pour controler le deplacement de la langue?
     */

    //Attributs
    private final String ICON = "\uEDF8";
    private final String TONGUE1 = "\u2500"; //corps de la langue
    private final String TONGUE2 = "\u257C"; //pointe de la langue

    private long waitBeforeMoving = 0;

    private boolean extendedTongue = false;
    private int tongueLength = 0;
    private final int MAX_TONGUE_LENGTH = 3;
    private long tongueTimer = 0;

    private List<Position> tonguePositions = new ArrayList<>(); //utilisation de classe Position

    private static EKOSon ENEMY_TOUCHED = EKOAudio.charger("audio/651625__martcraft__fail_cut.wav");

    /**
     * Constructeur de l'objet de type Frog
     * @param x Position X initiale du frog
     * @param y Position Y initiale du frog
     */
    public Frog(int x, int y) {
        super("Frog", x, y, Etiquette.ENEMY);
    }

    @Override
    protected void mettreAJour(long deltaTemps) {
        //Buffer qui permet de ralentir le mettreAJour
        waitBeforeMoving += deltaTemps;
        tongueTimer += deltaTemps;

        if(waitBeforeMoving < 100) {
            return;
        }
        waitBeforeMoving = 0;

        if (tongueTimer < 800) { //extension de la langue
            extendedTongue = true;

            if (tongueLength < MAX_TONGUE_LENGTH) {
                tongueLength++;
            }

        } else if (tongueTimer < 1300) { //retraction de la langue

            if (tongueTimer > 0) {
                tongueLength--;
            }

            if (tongueLength == 0) {
                extendedTongue = false;
            }

        } else {
            tongueTimer = 0;
        }

        tonguePositions.clear();
        if (extendedTongue) {
            for (int i = 1; i <= tongueLength; i++) {
                tonguePositions.add(new Position(position.x + i, position.y, 0));
            }
        }

    }
    


    @Override
    protected void dessiner() {
        EKOCouleur color = EKOCouleur.RVB(34, 139, 34);

        EKOConsole.afficher(position.x, position.y, ICON, color); //dessin de la grenouille

        if (extendedTongue) {
            for (int i = 1; i <= tongueLength; i++) { //on commence a index 1 pour que la langue apparaisse devant la
                // grenouille
                if (i == MAX_TONGUE_LENGTH) {
                    EKOConsole.afficher(position.x + i, position.y, TONGUE2, color);
                } else {
                    EKOConsole.afficher(position.x + i, position.y, TONGUE1, color);
                }
            }
        }
    }

    @Override
    public void gererCollisionAvec(ObjetJeu autre) {

        for (Position place : tonguePositions) {
            if ((autre.etiquette == Etiquette.PLAYER) &&
                (place.x == autre.getX() && place.y == autre.getY())) {
                LifeIndicator.looseALife();
                EKOAudio.jouer(ENEMY_TOUCHED);
                GameProgressManager.restartLevel();
            }
        }
    }
}
