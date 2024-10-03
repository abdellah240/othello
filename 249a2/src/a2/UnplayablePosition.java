package a2;

public class UnplayablePosition extends Position {
    final static char UNPLAYABLE = '*';

    public UnplayablePosition() {
        this.piece = UNPLAYABLE;
    }

    public boolean canPlay() {
        return false;
    }
}