public class FrogTongue extends Enemy{

    private final String TONGUE1 = "\u2500"; //corps de la langue
    private final String TONGUE2 = "\u257C"; //pointe de la langue

    public FrogTongue(int x, int y) {
        super("Frog", x, y, Etiquette.ENEMY);
    }

    @Override
    protected void dessiner() {

    }
}
