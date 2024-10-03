package a2;

public class Board {
	String name;
	Position[][] boardPieces;
	int startingPos;

	final static private int[][] allDirections = { // [# of vector][row/column deplacement]
													// [#: 0 to 7 ] [0]: row deplacement
													// [#: 0 to 7 ] [1]: column deplacement
			{ -1, 0 }, // up
			{ 1, 0 }, // down
			{ 0, -1 }, // left
			{ 0, 1 }, // right

			{ -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } // diagonal directions
	};

	public int getStartingPos() {
		return startingPos;
	}

	public void setStartingPos(int startingPos) {
		this.startingPos = startingPos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Board(String save_file, int startingPos) {
		this.name = save_file;
		this.startingPos = startingPos;
		boardPieces = new Position[8][8];
		startBoard();
	}

	public void startBoard() {// initialize default positions
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boardPieces[i][j] = new PlayablePosition();
				boardPieces[i][j].setPiece(Position.EMPTY);
			}
		}
		boardPieces[7][3] = new UnplayablePosition();
		boardPieces[7][4] = new UnplayablePosition();

		switch (startingPos) {
		case 1:
			boardPieces[2][2].setPiece(Position.WHITE);
			boardPieces[2][3].setPiece(Position.BLACK);
			boardPieces[3][2].setPiece(Position.BLACK);
			boardPieces[3][3].setPiece(Position.WHITE);
			break;
		case 2:
			boardPieces[2][4].setPiece(Position.WHITE);
			boardPieces[2][5].setPiece(Position.BLACK);
			boardPieces[3][4].setPiece(Position.BLACK);
			boardPieces[3][5].setPiece(Position.WHITE);
			break;
		case 3:
			boardPieces[4][2].setPiece(Position.WHITE);
			boardPieces[4][3].setPiece(Position.BLACK);
			boardPieces[5][2].setPiece(Position.BLACK);
			boardPieces[5][3].setPiece(Position.WHITE);
			break;
		case 4:
			boardPieces[4][4].setPiece(Position.WHITE);
			boardPieces[4][5].setPiece(Position.BLACK);
			boardPieces[5][4].setPiece(Position.BLACK);
			boardPieces[5][5].setPiece(Position.WHITE);
			break;
		case 5:
			boardPieces[2][2].setPiece(Position.WHITE);
			boardPieces[2][3].setPiece(Position.WHITE);
			boardPieces[2][4].setPiece(Position.BLACK);
			boardPieces[2][5].setPiece(Position.BLACK);
			boardPieces[3][2].setPiece(Position.WHITE);
			boardPieces[3][3].setPiece(Position.WHITE);
			boardPieces[3][4].setPiece(Position.BLACK);
			boardPieces[3][5].setPiece(Position.BLACK);
			boardPieces[4][2].setPiece(Position.BLACK);
			boardPieces[4][3].setPiece(Position.BLACK);
			boardPieces[4][4].setPiece(Position.WHITE);
			boardPieces[4][5].setPiece(Position.WHITE);
			boardPieces[5][2].setPiece(Position.BLACK);
			boardPieces[5][3].setPiece(Position.BLACK);
			boardPieces[5][4].setPiece(Position.WHITE);
			boardPieces[5][5].setPiece(Position.WHITE);
			break;
		default:
			break;
		}
	}

	public void drawBoard() {
		System.out.println(getBoard());
	}

	public String getBoard() {
		String board_str = "";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board_str += boardPieces[i][j].getPiece()+ " ";
			}
			board_str += "\n";
		}

		return board_str.strip();
	}

	protected boolean canConvert(int x, int y, char current) {
		char opponent=' ';
		
		if (current == Position.BLACK) {
			opponent = Position.WHITE;
		}
		if (current== Position.WHITE) {
	
			opponent = Position.BLACK;
		}

	    for (int i = 0; i < 8; i++) {
	        boolean foundOpponent = false;
	        int x1 = x + allDirections[i][0];
	        int y1 = y + allDirections[i][1];

	        while (isInBoard(x1, y1)) {
	            if (boardPieces[x1][y1].getPiece() == opponent) {
	                foundOpponent = true;
	            } else if (boardPieces[x1][y1].getPiece()==current) {
	            	if (foundOpponent)
	                return true; // current move converts pieces
	            } else {
	                break; // unplayable, exit loop
	            }
	            x1 += allDirections[i][0];
	            y1 += allDirections[i][1];
	        }
	    }
	    return false;
	}

	protected void convertPieces(int x, int y, char current) {

		char opponent;
		if (current == Position.BLACK)
			opponent = Position.WHITE;
		else
			opponent = Position.BLACK;

		boardPieces[x][y].setPiece(current); // places first piece

		for (int i = 0; i < 8; i++) {
			int x1 = x + allDirections[i][0];
			int y1 = y + allDirections[i][1];

			int[][] convertedPieces = new int[100][2]; // opponent pieces to replace in boardPieces
			// [#][row/column]

			int convertedCount = 0; // #
			boolean foundOpponent = false;

			if ((isInBoard(x1, y1))) {
				do {
					if (boardPieces[x1][y1].getPiece() == opponent) {
						convertedPieces[convertedCount][0] = x1; // set new row
						convertedPieces[convertedCount][1] = y1; // set new column
						convertedCount++; // move to next piece
						foundOpponent = true;
					} else if (boardPieces[x1][y1].getPiece() == current && foundOpponent) {

						for (int j = 0; j < convertedCount; j++) {

							int row = convertedPieces[j][0];
							int col = convertedPieces[j][1];

							boardPieces[row][col].setPiece(current);

							// loops through convertedPieces, gets their position and updates it in board

						}

						break; // exit inner loop

					} else {
						break;// exit inner loop if empty/unplayable
					}
					x1 += allDirections[i][0];
					y1 += allDirections[i][1];
					// next piece same direction

				} while ((isInBoard(x1, y1)));
			}
		}
	}

	public boolean isInBoard(int x, int y) {
		if ((x >= 0) && (x < 8) && (y >= 0) && (y < 8))//dimensions
			return true;
		else
			return false;

	}

	public void updatePlayablePositions(char current) {
	  System.out.println("Updating board for "+ current + "...");
	    for (int x = 0; x < 8; x++) {
	        for (int y = 0; y < 8; y++) {
	            if (boardPieces[x][y].getPiece() == Position.EMPTY) {
	                if (canConvert(x, y, current)) {
	                    boardPieces[x][y] = new PlayablePosition();
	                } else {
	                    boardPieces[x][y] = new UnplayablePosition();
	                }
	            }
	        }
	    }
	}

}
