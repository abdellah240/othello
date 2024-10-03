package a2;

public final class Player {
	private String name;
	private char color;

    public Player(String name, char piece) {
        this.name = name;
        this.color = piece;
    }

    public String getName() {
        return name;
    }

    public char getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(char piece) {
        this.color = piece;
    }
}

