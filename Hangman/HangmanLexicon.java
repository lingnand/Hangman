/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class HangmanLexicon {
	
	public HangmanLexicon() {
		while (buffReader==null) {
			try {
				buffReader = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			}
			catch (IOException e) {
				throw new ErrorException(e);
			}
		}
		wordArray = new ArrayList();
		try{
			while (true) {
				String str = buffReader.readLine();
				if (str == null) break;
				wordArray.add(str);
			}
			buffReader.close();
		}
		catch (IOException e) {
			throw new ErrorException(e);
		}
	}

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return wordArray.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return (String) wordArray.get(index);
	}
	
	private BufferedReader buffReader;
	private ArrayList wordArray;
	
}
