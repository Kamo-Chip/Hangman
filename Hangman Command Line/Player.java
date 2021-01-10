import java.util.Scanner;

public class Player{
	private String name;
	private static int numPlayers = 0;
	private Scanner sc = new Scanner(System.in);

	public Player(){
		// System.out.println("Enter your name:");
		// name = sc.next();
		numPlayers++;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public static int getNumPlayers(){
		return numPlayers;
	}
}