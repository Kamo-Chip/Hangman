import java.util.Scanner;
import java.io.*;


public class Hangman{

	private String wordMasked;
	private String word;
	private int numChances = 6;
	private Player[] players;
	private Scanner sc = new Scanner(System.in);
	private char[] letters;
	private char letter;
	private int indexOfLetter;
	private char[] lettersMasked;
	private int indexOfPlayer;

	public Hangman(){
		this.addPlayers();
		this.start();
	}

	private void addPlayers(){
		System.out.println("How many players are there?");
		int numPlayers = sc.nextInt();
		players = new Player[numPlayers];
		for(int i = 0; i < players.length; i++){
			players[i] = new Player();
			System.out.println("Enter player " + (i + 1) + "'s name:");
			players[i].setName(sc.next());
		}
		System.out.println("Total # of players: " + Player.getNumPlayers());
	}

	private void start(){
		System.out.println("(1) Pick random player to start\n(2) Decide amongst yourselves who will start");
		int choice = sc.nextInt();
		switch(choice){
			case 1:
				this.randomPlayer(players);
				break;
			case 2:
				if(this.playersChoice(players) == 0){
					System.out.println("Player could not be found. Try again");
					this.playersChoice(players);
				}
				break;
			default:
				System.out.println("Incorrect input value. Try again.");
				this.start();
				break;
		}
	}

	private void playAgain(){
		System.out.println("(1) Play again\n(2) Quit");
		int choice = sc.nextInt();
		switch(choice){
			case 1: 
				this.start();
				break;
			case 2:
				System.exit(0);
				break;
			default:
				System.out.println("Incorrect input value. Try again.");
				this.playAgain();
		}
	}

	private void randomPlayer(Player[] players){
		int randomNum = (int)(Math.random() * ((Player.getNumPlayers() - 1) - 0) + 0);
		System.out.println(players[randomNum].getName() + " will start");
		this.setWord(players[randomNum]);
		this.guessWord(players,players[randomNum]);
	}

	private int playersChoice(Player[] players){
		System.out.println("Enter name of the player to start:");
		String name = sc.next();
		int retVal = 0;
		int f = 0;
		for(int i = 0; i < players.length; i++){
			if(checkPlayer(players[i], name)){
				System.out.println(players[i].getName() + " will start");
				f = i;
				retVal = 1;
				this.setWord(players[i]);
			}
		}
		this.guessWord(players,players[f]);
		
		return retVal;
	}

	private void setWord(Player player){
		System.out.println(player.getName() + " enter a word: \n(Ignore first asterisk)");
		word = this.replace();
		wordMasked = word.replaceAll(".","*");
		lettersMasked = wordMasked.toCharArray();
		letters = word.toCharArray();
	}

	private String replace(){
	  EraserThread et = new EraserThread();
      Thread mask = new Thread(et);
      mask.start();

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String wordA = null;

      try {
         wordA = in.readLine();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
      // stop masking
      et.stopMasking();
      //this.word = wordA;
      // return the password entered by the user
      return wordA;
	}

	private boolean checkPlayer(Player playerP, String n){
		boolean contains = false;

		for(Player player : players){
			if(playerP.getName().equalsIgnoreCase(n)){
				contains = true;
				break;
			}
		}
		return contains;
	}

	private boolean checkLetter(char[] arr, char c){
		boolean test = false;

		for(char character:arr){
			if(character == c){
				test = true;
				indexOfLetter = new String(arr).indexOf(c);
				break;
			}
		}
		return test;
	}

	private void guessWord(Player[] players, Player playerSkip){
		
		boolean hasWon = false; 
		String a = String.valueOf(letters);

		while(numChances <= 6 && numChances > 1){

		String b = String.valueOf((lettersMasked));
		if(a.equals(b)){
			hasWon = true;
		}else{
			hasWon = false;
		}
		if(hasWon == true){
			System.out.println("\nYou have won!\n");
			this.playAgain();
		}
			for(Player player : players){
				if(player.equals(playerSkip)){
					continue;
				}
				System.out.println(player.getName() + " enter a letter: ");
				letter = sc.next().charAt(0);

				if(checkLetter(letters, letter)){
					System.out.println("Correct guess!");
					lettersMasked[indexOfLetter] = letter;
					letters[indexOfLetter] = '*';
					String answer = String.valueOf(lettersMasked);
					System.out.println(answer);
					break;
				}else{
					System.out.println("Incorrect guess!");
					numChances--;
					System.out.println("# of attempts remaining: " + numChances);
				}
			}	
		}
		System.out.println("\nYou have lost!\n");
		this.playAgain();
	}

	public static void main(String[] args){
		new Hangman();
	}
}