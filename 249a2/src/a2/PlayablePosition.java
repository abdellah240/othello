package a2;

public class PlayablePosition extends Position {

    public PlayablePosition() {
        this.piece = Position.EMPTY;
    }

    @Override
    public boolean canPlay() {
        return true;
    }
}