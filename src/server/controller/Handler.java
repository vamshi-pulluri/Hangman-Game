package server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import server.common.PrintContents;

public class Handler extends Thread {

	private ArrayList<String> 	wordlist;
	private Socket 				socket;

	private String 	currWord;
	private String 	guessedLetters;
	private int 	Remaining;
	private int 	score;

	public Handler(ArrayList<String> wordlist, Socket socket) {
		this.wordlist = wordlist;
		this.socket = socket;
		this.score = 0;
	}

	@Override
	public void run() {
		super.run();
        PrintContents con= new PrintContents(socket);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String str;
			while ((str = reader.readLine()) != null) {
				//When you press the new game key
				if (str.equals("start")) {
					currWord = getWordFromDictionary();
					Remaining = currWord.length();
					guessedLetters = "";
					StringBuilder sb= new StringBuilder();
					sb.append(ReceiveWordFromServer()).append(',').append("Guess the word!").append(',').append(Remaining).append(',').append(score);
		            con.passdatatogui(sb);
				}
				//When the correct word is found
				else if (str.equals(currWord)) {
					score++;
					StringBuilder sb= new StringBuilder();
					sb.append(currWord).append(',').append("Congratulations! You won!").append(',').append(Remaining).append(',').append(score);
					con.passdatatogui(sb);
				}
				
				else if (str.length() == 1) {
				//When 1character is guessed by the user
					String receivedWord = ReceiveWordFromServer();
					guessedLetters += str;
					String updatedWord = ReceiveWordFromServer();
				
					//When the complete word is found
					if (updatedWord.equals(currWord)) {
						score++;
						StringBuilder sb= new StringBuilder();
						sb.append(currWord).append(',').append("Congratulations! You won!").append(',').append(Remaining).append(',').append(score);
						con.passdatatogui(sb);
					}
					else if (receivedWord.equals(updatedWord)) {
						//If your Guess is wrong
						Remaining--;

						//If no of attempts remaining=0,Sorry, game done!
						if (Remaining == 0) {
							score--;
							StringBuilder sb= new StringBuilder();
							sb.append(currWord).append(',').append("Game Over!").append(',').append(Remaining).append(',').append(score);
							con.passdatatogui(sb);
						}
						else {
							//If your guess was wrong!!
							StringBuilder sb= new StringBuilder();
							sb.append(updatedWord).append(',').append("Guess was wrong!!").append(',').append(Remaining).append(',').append(score);
							con.passdatatogui(sb);
						}
					}
					else {
						//Guess is right!!
						StringBuilder sb= new StringBuilder();
						sb.append(updatedWord).append(',').append("Guess was right!!").append(',').append(Remaining).append(',').append(score);
						con.passdatatogui(sb);
					}
				}
				else {
					Remaining--;
					
					if (Remaining == 0) {
						score--;
						StringBuilder sb= new StringBuilder();
						sb.append(currWord).append(',').append("Game Over!").append(',').append(Remaining).append(',').append(score);
						con.passdatatogui(sb);
					}
					else {
						StringBuilder sb= new StringBuilder();
						sb.append(ReceiveWordFromServer()).append(',').append("Guess was wrong!!").append(',').append(Remaining).append(',').append(score);
						con.passdatatogui(sb);
					}
				}
			}

			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Gets a random word from the dictionary-"words.txt"
	private String getWordFromDictionary() {
		Random rand = new Random();
		return wordlist.get(rand.nextInt(wordlist.size()));
	}

	// Returns a dashed-word based on the guess the user made
	private String ReceiveWordFromServer() {
		if (guessedLetters.length() > 0) {
			return currWord.replaceAll("[^" + guessedLetters + "]", "-");
		}
		else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < currWord.length(); i++) {
				sb.append("-");
			}
			return sb.toString();
		}
	}
}



