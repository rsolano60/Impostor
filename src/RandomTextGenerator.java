/**
Student name: Ricardo Solano Pacheco
Login ID: solanor
Course: CSCI455x
Programming Assignment #4
USC Student ID: 5620566399
e-mail: rsolano60@hotmail.com
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
This class is a k Word-level Markov text generation algorithm.
@param input text from a file
@param randomNumGen used to choose from successors
@param debugMode indicates a fixed Random seed (1) and debug statements
 */

public class RandomTextGenerator {
	private ArrayList<String> inText;
	private Random randomNumGen;
	private boolean debugMode;

	/**
	 * @param inReader a reader of the input text
	 */
	RandomTextGenerator(ArrayList<String> someText){
		debugMode=false;
		inText = someText;
		randomNumGen= new Random();
		//System.out.println(inText);
	}
	/**
	 * @param inReader a reader of the input text
	 * @param aSeed some seed to initialize Random for debug purposes 
	 */
	RandomTextGenerator(ArrayList<String> someText, int aSeed){
		debugMode=true;
		inText = someText;
		randomNumGen= new Random(aSeed);
		//System.out.println(inText);
	}
	/**
	 * Generates all the random words
	 * @param prefixLength the prefix length to compute the possible words
	 * @param numWords The number of words to generate
	 * @return a generated word
	 */
	public ArrayList<String> generate(int prefixLength, int numWords){
		ArrayList<String> outText = new ArrayList<String>();
		Prefix currentPrefix= randomPrefix(prefixLength);
		if (debugMode)
			{System.out.println("DEBUG: chose a new initial prefix:");}

		String generatedWord;
		for(int i=0; i<numWords; i++){
			if (debugMode)
				{System.out.println("DEBUG: prefix: "+currentPrefix.toFormattedString());}
			generatedWord= oneWord(currentPrefix);
			if(generatedWord==null){
				currentPrefix= randomPrefix(prefixLength);
				i--;
				if (debugMode)
					{System.out.println("DEBUG: No possible succesors, chose a new prefix: ");}
			}
			else{
			if (debugMode)
				{System.out.println("DEBUG: word generated: "+generatedWord);}
			outText.add(generatedWord);
			currentPrefix.update(generatedWord);
			}
		}
		return outText;
	}

	private Prefix randomPrefix(int prefixLength){
		ListIterator<String> iter= inText.listIterator(randomNumGen.nextInt(inText.size()-prefixLength));
		LinkedList<String> randomPrefix = new LinkedList<String>();
		for (int i= 0; iter.hasNext() && i<prefixLength; i++)
			{randomPrefix.add(iter.next());}
		return new Prefix(randomPrefix);
		
	}
	
	/**
	 * Generates one random word from all the words in the file that are successors to aPrefix
	 * @param aPrefix the prefix to evaluate if a word qualifies
	 * @return a generated word
	 */
	private String oneWord (Prefix aPrefix){
		ArrayList<String> successors= new ArrayList<String>();
		ListIterator<String> iter= inText.listIterator();
		LinkedList<String> filePrefix = new LinkedList<String>();
		for (int i= 0; i<aPrefix.size(); i++)
			{filePrefix.add(iter.next());}
		Prefix possiblePrefix = new Prefix(filePrefix);
		String successor;
		while(iter.hasNext()){
			successor=iter.next();
			if (aPrefix.equals(possiblePrefix))
				successors.add(successor);			
			possiblePrefix.update(successor);
		}
		if (debugMode)
			{System.out.println("DEBUG: successors: "+successors);}
		if (successors.isEmpty())
			{return null;}
		return successors.get(randomNumGen.nextInt(successors.size()));
	}
}