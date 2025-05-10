import eko.EKOConsole;

public class FrogLookingRight extends Frog{

    public FrogLookingRight(int x, int y) {
        super(x, y);
    }

    @Override
    protected boolean facingRight() {
        return true;
    }


}
