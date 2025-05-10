import eko.EKOConsole;

public class FrogLookingLeft extends Frog {

    public FrogLookingLeft(int x, int y) {
        super(x, y);
        ICON.retourner();
        TONGUE1.retourner();
        TONGUE2.retourner();
    }

    @Override
    protected boolean facingRight() {
        return false;
    }

}
