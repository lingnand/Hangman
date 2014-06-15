/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Hangman extends ConsoleProgram {
	
	public static void main(String[] args) {
		new Hangman().start(args);
	}
	
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
		canvas.displayWord(wordState);
		add(new JButton("Restart"), SOUTH);
		addActionListeners();
		gameInit();
		restart = false;
	}
	
	private void gameInit() {
		println("Welcome to Hangman Game! @Designed by Dai Studio");
		guessesLeft = 8;
		key = lexicon.getWord(rgen.nextInt(0,lexicon.getWordCount()-1));
		wordState = "";
		for (int i=0; i<key.length(); i++) {
			if (key.charAt(i)==' ') wordState+=' ';
			else wordState+="-";
		}
	}

    public void run() {
    	while (true) {
			while (!gameWin() && !gameLose()) {
				if (restart) {
					restart();
					restart=false;
				}
				gameState();
				readInput();
			}
			printResult();
			if (readLine("/nPlay again? y/n").toLowerCase().startsWith("n")) break;
			else restart();
    	}
	}
    
    private void restart() {
    	gameInit();
    	canvas.reset();
    }
    
    public void actionPerformed (ActionEvent e) {
    	if (e.getActionCommand().equals("Restart")) {
    		restart=true;
    	}
    }
    
    private boolean gameWin() {
    	return wordState.equals(key.toUpperCase());
    }
    
    private boolean gameLose() {
    	return guessesLeft == 0;
    }
    
    private void gameState() {
    	canvas.displayWord(wordState);
    	println ("The word now looks like this: "+wordState);
    	println ("You have "+guessesLeft+" guesses left.");
    }
    
    private void readInput() {
    	String input = readLine ("Your guess: ");
    	while (inputNotValid(input)) {
    		input = readLine ("Invalid input.\nPlease enter your guess again: ");
    	}
    	char guess = Character.toUpperCase(input.charAt(0));
    	boolean guessWrong = true;
    	for (int i=0; i<key.length(); i++) {
    		if (Character.toUpperCase(key.charAt(i))==guess) {
    			wordState = wordState.substring(0,i)+guess+wordState.substring(i+1);
    			guessWrong = false;
    		}
    	}
    	if (guessWrong) {
    		println("There are no "+guess+"'s in the word!");
    		canvas.noteIncorrectGuess(guess);
    		guessesLeft--;	
    	}	
    	else println("That guess is correct.");
    }
    
    private boolean inputNotValid (String input) {
    	input = input.trim();
    	if (input.length()!=1) return true;
    	if (!Character.isLetter(input.charAt(0))) return true;
    	return false;
    }
    
    private void printResult() {
    	if (gameWin()) {
    		println ("The word is: "+wordState+"\n" +
    							"Congratulations! You win!");
    		canvas.displayWord(wordState);
    	}
    	else println ("You are completely hung.\n" +
    				"The word was: "+key.toUpperCase()+"\n" +
    				"You lose.");
    	canvas.displayResult(gameWin());
    }
   
    private String key, wordState;
    private int guessesLeft;
    private HangmanLexicon lexicon = new HangmanLexicon();
    private RandomGenerator rgen = new RandomGenerator();
    private HangmanCanvas canvas;
    private boolean restart;

}
