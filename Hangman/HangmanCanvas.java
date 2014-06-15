/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import java.awt.*;

public class HangmanCanvas extends GCanvas {
	
	public HangmanCanvas() {
		fig = new GCompound[9];
		scaffold = fig[0] = createScaffold(); 
		head = fig[1] = createHead(); 
		body = fig[2] = createBody();
		leftArm = fig[3] = createLeftArm();
		rightArm = fig[4] = createRightArm();
		leftLeg = fig[5] = createLeftLeg();
		rightLeg = fig[6] = createRightLeg();
		leftFoot = fig[7] = createLeftFoot();
		rightFoot = fig[8] = createRightFoot();
		add(scaffold,(appWidth-BEAM_LENGTH)/2,(appHeight-SCAFFOLD_HEIGHT-LABEL_HEIGHT)/2);
		add(head,scaffold.getX()+BEAM_LENGTH/2-head.getWidth()/2,scaffold.getY()+ROPE_LENGTH);
		add(body,head.getX()+head.getWidth()/2,head.getY()+head.getWidth());
		add(leftArm,body.getX(),body.getY()+ARM_OFFSET_FROM_HEAD);
		add(rightArm,body.getX(),body.getY()+ARM_OFFSET_FROM_HEAD);
		add(leftLeg,body.getX(),body.getY()+body.getHeight());
		add(rightLeg,body.getX(),body.getY()+body.getHeight());
		add(leftFoot,body.getX()-HIP_WIDTH,leftLeg.getY()+LEG_LENGTH);
		add(rightFoot,body.getX()+HIP_WIDTH,leftLeg.getY()+LEG_LENGTH);
		for (int i=1; i<fig.length; i++) {
			fig[i].setVisible(false);
		}
		word = new GLabel("");
		incorrectGuessesList = "Wrong guesses: ";
		incorrectGuesses = new GLabel(incorrectGuessesList);
		word.setFont("Helvetica-"+WORD_FONT_SIZE);
		incorrectGuesses.setFont("Helvetica-"+INCORRECT_GUESSES_FONT_SIZE);
		add(word,LABEL_OFFSET_FROM_LEFT,scaffold.getY()+scaffold.getHeight()+LABEL_HEIGHT*WORD_LABEL_RATIO_OF_LABEL_HEIGHT);
		add(incorrectGuesses,LABEL_OFFSET_FROM_LEFT,scaffold.getY()+scaffold.getHeight()+LABEL_HEIGHT*INCORRECT_GUESSES_LABEL_RATIO_OF_LABEL_HEIGHT);
		numberOfIncorrectGuesses = 0;
	}
		

/** Resets the display so that only the scaffold appears */
	public void reset() {
		if (resultMessage!=null) remove(resultMessage);
		for (int i=1; i<fig.length; i++) {
			fig[i].setVisible(false);
		}
		displayWord("");
		numberOfIncorrectGuesses = 0;
		incorrectGuessesList = "Wrong guesses: ";
		incorrectGuesses.setLabel(incorrectGuessesList);
	}
	
	private GCompound createScaffold() {
		GCompound result = new GCompound();
		result.add(new GLine(0,0,0,SCAFFOLD_HEIGHT));
		result.add(new GLine(0,0,BEAM_LENGTH,0));
		result.add(new GLine(BEAM_LENGTH/2,0,BEAM_LENGTH/2,ROPE_LENGTH));
		return result;
	}
	
	private GCompound createHead() {
		GCompound result = new GCompound();
		result.add(new GOval(HEAD_RADIUS*2,HEAD_RADIUS*2));
		return result;
	}
	
	private GCompound createBody() {
		GCompound result = new GCompound();
		result.add(new GLine(0,0,0,BODY_LENGTH));
		return result;
	}
	
	private GCompound createLeftArm() {
		GCompound result = new GCompound();
		result.add(new GLine(0,0,-UPPER_ARM_LENGTH,0));
		result.add(new GLine(-UPPER_ARM_LENGTH,0,-UPPER_ARM_LENGTH,LOWER_ARM_LENGTH));
		return result;
	}
	
	private GCompound createRightArm() {
		GCompound result = new GCompound();
		result.add(new GLine(0,0,UPPER_ARM_LENGTH,0));
		result.add(new GLine(UPPER_ARM_LENGTH,0,UPPER_ARM_LENGTH,LOWER_ARM_LENGTH));
		return result;
	}
	
	private GCompound createLeftLeg() {
		GCompound result = new GCompound();
		result.add(new GLine(0,0,-HIP_WIDTH,0));
		result.add(new GLine(-HIP_WIDTH,0,-HIP_WIDTH,LEG_LENGTH));
		return result;
	}
	
	private GCompound createRightLeg() {
		GCompound result = new GCompound();
		result.add(new GLine(0,0,HIP_WIDTH,0));
		result.add(new GLine(HIP_WIDTH,0,HIP_WIDTH,LEG_LENGTH));
		return result;
	}
	
	private GCompound createLeftFoot() {
		GCompound result = new GCompound();
		result.add(new GLine(0,0,-FOOT_LENGTH,0));
		return result;
	}
	
	private GCompound createRightFoot() {
		GCompound result = new GCompound();
		result.add(new GLine(0,0,FOOT_LENGTH,0));
		return result;
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		this.word.setLabel(word);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		if (numberOfIncorrectGuesses>=8) return;
		numberOfIncorrectGuesses ++;
		fig[numberOfIncorrectGuesses].setVisible(true);
		if (notInList(letter)) incorrectGuessesList+=letter;
		incorrectGuesses.setLabel(incorrectGuessesList);
	}
	
	private boolean notInList(char letter) {
		for (int i=15; i<incorrectGuessesList.length(); i++) {
			if (incorrectGuessesList.charAt(i)==letter) return false;
		}
		return true;
	}
	
	public void displayResult (boolean win) {
		resultMessage = new GLabel (win?"You win!":"You lose!");
		resultMessage.setColor(Color.RED);
		resultMessage.setFont("Helvetica-"+RESULT_MESSAGE_FONT_SIZE);
		add(resultMessage,(appWidth-resultMessage.getWidth())/2,(appHeight+resultMessage.getAscent())/2);
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final double SCAFFOLD_HEIGHT = 360;
	private static final double BEAM_LENGTH = 170;
	private static final double ROPE_LENGTH = 18;
	private static final double HEAD_RADIUS = 36;
	private static final double BODY_LENGTH = 144;
	private static final double ARM_OFFSET_FROM_HEAD = 28;
	private static final double UPPER_ARM_LENGTH = 72;
	private static final double LOWER_ARM_LENGTH = 44;
	private static final double HIP_WIDTH = 36;
	private static final double LEG_LENGTH = 108;
	private static final double FOOT_LENGTH = 28;
	private static final double LABEL_HEIGHT = SCAFFOLD_HEIGHT*0.2;
	private static final int WORD_FONT_SIZE = 24, INCORRECT_GUESSES_FONT_SIZE=12, RESULT_MESSAGE_FONT_SIZE=45;
	private static final double LABEL_OFFSET_FROM_LEFT = 40;
	private static final double WORD_LABEL_RATIO_OF_LABEL_HEIGHT = 0.7;
	private static final double INCORRECT_GUESSES_LABEL_RATIO_OF_LABEL_HEIGHT = 0.9;
	
	private GCompound[] fig;
	private GCompound scaffold,head,body,leftArm,rightArm,leftLeg,rightLeg,leftFoot,rightFoot;
	private GLabel word,incorrectGuesses,resultMessage;
	private String incorrectGuessesList;
	private int numberOfIncorrectGuesses;
	private double appWidth = 400, appHeight = 460;

}
