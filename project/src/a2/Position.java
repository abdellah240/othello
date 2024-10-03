package a2;

public abstract class Position {

	protected char piece;
	final static char BLACK = 'B';
	final static char WHITE = 'W';
	final static char EMPTY = '.';
	
	public char getPiece() {
		return piece;
	}

	public void setPiece(char piece) {
		this.piece = piece;
	}

	abstract boolean canPlay();
	
}
