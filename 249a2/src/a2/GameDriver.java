package a2;

import java.io.IOException;
import java.util.Scanner;

public class GameDriver {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.println("1. Quit\t 2. Load a Game\t 3. Start a New Game");
		int choice = sc.nextInt();

		switch (choice) {
		case 1://quit
			System.out.println("Quitting...");
			System.exit(0);
		case 2://load

			String fileName = "";

			fileName = Game.readFileName();
			System.out.println("Loaded " + fileName + " successfully.");
			String[] playerNames = Game.loadNames(fileName).split(",");

			Player p1 = new Player(playerNames[0], Position.BLACK);
			Player p2 = new Player(playerNames[1], Position.WHITE);
			Game game2 = new Game(p1, p2, 0);
			game2.setBoard(Game.load(fileName));
			game2.play();

			break;
		case 3://new
			System.out.println("Enter Player1 Name");
			String p1_name = sc.next();
			System.out.println("Enter Player2 Name");
			String p2_name = sc.next();

			Player p_1 = new Player(p1_name, Position.BLACK);
			Player p_2 = new Player(p2_name, Position.WHITE);
			System.out.println("Pick a Starting Position: \n>Offset: 1,2,3 or 4\t>Four-by-four: 5");
			int sPosInput = sc.nextInt();

			Game game = new Game(p_1, p_2, sPosInput);
			game.start();
			break;
		default:
			System.out.println("Invalid choice. Quitting...");
			System.exit(0);
		}

		sc.close();
	}
}