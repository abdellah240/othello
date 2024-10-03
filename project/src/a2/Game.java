package a2;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
	private Player first;
	private Player second;
	private Player current;
	private Board board;

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player getFirst() {
		return first;
	}

	public void setFirst(Player first) {
		this.first = first;
	}

	public Player getSecond() {
		return second;
	}

	public void setSecond(Player second) {
		this.second = second;
	}

	public Player getCurrent() {
		return current;
	}

	public void setCurrent(Player current) {
		this.current = current;
	}

	public Game(Player p1, Player p2, int startingPos) {
		this.first = p1;
		this.second = p2;
		setCurrent(p1);
		this.board = new Board("board_name", startingPos);
	}

	public void takeTurn(Player current) {
		board.updatePlayablePositions(current.getColor());
		Scanner sc = new Scanner(System.in);
		board.drawBoard();

		System.out.println(current.getName() + "'s turn.");
		System.out.println("What do you want to do?\n1. Make a move\t2. Save\t3. Concede");

		int userIn = sc.nextInt();
		switch (userIn) {

		case 1:
			// move
			System.out.print("What row (1 to 8)? ");
			int x = sc.nextInt() - 1;
			System.out.print("What column (1 to 8)? ");
			int y = sc.nextInt() - 1;

			if (board.boardPieces[x][y].canPlay() && board.boardPieces[x][y].getPiece() == Position.EMPTY
					&& board.canConvert(x, y, current.getColor())) {
				board.convertPieces(x, y, current.getColor());
				if (current.equals(first))
					setCurrent(second);
				else
					setCurrent(first);
			} else {
				System.out.println("Invalid move, try again.");
				takeTurn(current);
			}
			break;

		case 2:
			// save
			System.out.print("Saved the game. Exiting...");
			save();
			sc.close();
			System.exit(0);

		case 3:
			// forfeit
			System.out.print(current.getName() + " has conceded the match. Exiting...");
			sc.close();
			System.exit(0);
		}
	}

	public void play() {
		do {
			takeTurn(current);
		} while (!checkWin());
	}

	public void start() {

		// set board fields
		board.setName(readFileName());
		board.startBoard();
		play();

	}

	public static String readFileName() {
		String nb;
		String fileName;
		Scanner sc1 = new Scanner(System.in);

		do {
			System.out.print("Choose a save file (1, 2, or 3): ");
			nb = sc1.next().trim();
			fileName = "save_file" + nb + ".txt";
		} while (!nb.equals("1") && !nb.equals("2") && !nb.equals("3"));

		return fileName;

	}

	public static String loadNames(String fileName) throws IOException {

		FileReader fr = new FileReader(new File(fileName));
		BufferedReader br = new BufferedReader(fr);
		String p1Name = br.readLine().trim();
		String p2Name = br.readLine().trim();
		br.close();
		return p1Name + "," + p2Name;
	}

	public static Board load(String fileName) throws IOException {

		FileReader fr = new FileReader(new File(fileName));
		BufferedReader br = new BufferedReader(fr);
		
		Board board1 = new Board(fileName, 0);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board1.boardPieces[i][j] = new PlayablePosition();
				board1.boardPieces[i][j].setPiece(Position.EMPTY);
			}
		}
		board1.boardPieces[7][3] = new UnplayablePosition();
		board1.boardPieces[7][4] = new UnplayablePosition();

		// read 2 lines to skip player names
		br.readLine();
		br.readLine();

		for (int i = 0; i < 8; i++) {
			String savedBoard = br.readLine().replaceAll("\\s", "");

			for (int j = 0; j < 8; j++) {
				char savedPiece = savedBoard.charAt(j);
				switch (savedPiece) {
				case 'B':
					board1.boardPieces[i][j].setPiece(Position.BLACK);
					break;
				case 'W':
					board1.boardPieces[i][j].setPiece(Position.WHITE);
					break;
				case UnplayablePosition.UNPLAYABLE:
					board1.boardPieces[i][j] = new UnplayablePosition();
					break;
				default:
					break;
				}
			}
		}
		br.close();
		return board1;
	}

	private void save() {
		File f1 = new File(board.name);

		try {
			PrintWriter pr = new PrintWriter(f1);
			pr.println(first.getName());
			pr.println(second.getName());
			pr.print(board.getBoard());
			pr.flush();
			pr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean checkWin() {
		return false;
	}
}