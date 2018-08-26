import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Random;

public class Impostor {
	static int prefixLength;
	static int numWords;
	static ArrayList<String> inArray;
	static Random randomGenerator;
	static ArrayList<String> genTextArray;

	public static void main(String[] args) {
		prefixLength = Integer.parseInt(args[0]);
		numWords = Integer.parseInt(args[1]);
		String inFileName = args[2];
		String outFileName = args[3];
		try{
			inArray = toArrayList(inFileName);
		}
		catch(IOException ex){
			System.out.println("ERROR: Invalid input file name: "+ inFileName);
			return;
		}
		randomGenerator = new Random();
		//System.out.println(inArray);
		genTextArray = getRandomPrefix ();
		LinkedList<String> currentPrefix= new LinkedList<String>(genTextArray);
		//System.out.println("currentPrefix: "+currentPrefix);
		for(int i =0; i<numWords; i++){
			String nextWord=oneWord(currentPrefix);
			currentPrefix.addLast(nextWord);
			currentPrefix.removeFirst();
			genTextArray.add(nextWord);	
		}
		try{
			genOutFile(outFileName);
		}
		catch(IOException ex){
			System.out.println("ERROR: Failed to write in output file name: "+ outFileName);
			System.out.println("this could occur for one of the following reasons:");
			System.out.println("1) File exists but is a directory rather than a regular file");
			System.out.println("2) File does not exist but cannot be created");
			System.out.println("3) File cannot be opened for any other reason");
			return;
		}
		//System.out.println("genTextArray: "+genTextArray);
	}

	/** 
	 * Converts a file to an arraylist of strings
	 * @param aFileName A file to read from.
	 * @return List of individual strings of the specified file. List may be empty but not
	 *         null.
	 * @throws FileNotFoundException 
	 */
	private static ArrayList<String> toArrayList (String aFileName) throws FileNotFoundException{
		ArrayList<String> out = new ArrayList<String>();
		File inFile = new File(aFileName);
		Scanner inScanner = new Scanner(inFile);
		while(inScanner.hasNext()){
			out.add(inScanner.next());
		}
		inScanner.close();
		return out;
	}

	/**
	 * Gets a random prefix or sequence of words
	pre: input.size() > prefixLength
	 */
	private  static ArrayList<String> getRandomPrefix (){
		ArrayList<String> out = new ArrayList<String>();
		int startPos = randomGenerator.nextInt(inArray.size()-prefixLength-1);
		//System.out.println("STARTPOS "+startPos);
		for(int i = 0; i < prefixLength; i++ ){
			out.add(inArray.get(startPos+i));
		}
		return out;
	}


	/**
	 * Generates a random word from the current prefix
	 * @return a random word
	 */
	private static String oneWord (LinkedList<String> prefix){
		ArrayList<String> possibleWords = new ArrayList<String>();
		for (int i=prefixLength+1; i<inArray.size(); i++){
			if (isCandidate(i, prefix))
				possibleWords.add(inArray.get(i));
		}
		//System.out.println("possibleWords: "+possibleWords);
		//System.out.println("randomgen:"+randomGenerator.nextInt());
		//System.out.println("poswordSize: " + possibleWords.size() );

		if (possibleWords.isEmpty())
			return inArray.get(randomGenerator.nextInt(inArray.size()));
		return possibleWords.get(randomGenerator.nextInt(possibleWords.size()));
	}

	/**
	 * Returns true if the word located at index i of inArray is a candidate word to choose next
	 * @return a candidate word
	 */
	private static Boolean isCandidate(int index, LinkedList<String> prefix){
		ListIterator<String> inIter = inArray.listIterator(index);
		ListIterator<String> prefixIter = prefix.listIterator(prefix.size());
		boolean isCandidate=true;
		while(prefixIter.hasPrevious() && isCandidate)
			isCandidate = prefixIter.previous().equals(inIter.previous());
		return isCandidate;
	}

	/**
	 * Generates the formatted output file
	 * @param fileName the name of the output file to be generated
	 * @throws IOException 
	 */
	private static void genOutFile(String fileName) throws IOException{

		FileWriter fstream = new FileWriter(fileName);
		BufferedWriter out = new BufferedWriter(fstream);
		ListIterator<String> genTextIter = genTextArray.listIterator();
		int currentLineSize=0;
		while(genTextIter.hasNext()){
			String nextWord=genTextIter.next();
			if (currentLineSize+ 1 +nextWord.length()>80){
				out.newLine();
				currentLineSize=0;
			}

			else if (currentLineSize>0){
				out.write(" ");
				currentLineSize++;
			}
			out.write(nextWord);
			currentLineSize+=nextWord.length();
		}
		out.close();



	}

}

