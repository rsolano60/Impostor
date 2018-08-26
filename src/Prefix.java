/**
Student name: Ricardo Solano Pacheco
Login ID: solanor
Course: CSCI455x
Programming Assignment #4
USC Student ID: 5620566399
e-mail: rsolano60@hotmail.com
 */

import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

/**
Stores a list of words that represent a prefix. Implements a LinkedList Queue. 
FIFO (First in, first out) logic with overflow
Operations update() and size() take constant time,
while equals() takes O(n) time, worst case, n being the size of the prefixes to compare.
Most of the time it takes a lot less than n time.
@param prefixQueue represents the prefix words
 */

public class Prefix {
	Queue<String> prefixQueue;
	/** 
	 * @param aList a linked list of values to construct the Prefix
	 */
	public Prefix(LinkedList<String> aList){
		prefixQueue = new LinkedList<String>();
		prefixQueue.addAll(aList);

	}
	/** 
	 * Inserts one word into the prefix 'queue', causing overflow (removal) of the last word of the prefix.
	 * FIFO logic with overflow
	 * @param aWord The word to be inserted.
	 */
	public void update(String aWord){
		prefixQueue.poll();
		prefixQueue.add(aWord);
	}
	/** 
	 * Inserts one word into the prefix queue, causing overflow (removal) of the last word of the prefix
	 * @param aWord The word to be inserted.
	 * @return The size of the prefix
	 */
	public int size(){
		return  prefixQueue.size();
	}
	/** 
	 * Compares two Prefix objects efficiently [O(m+n) worst case]
	 * @param b The prefix to compare this with.
	 * @return True if the Prefixes are exactly equal
	 */
	public boolean equals(Prefix b) {
		boolean isEqual=this.size()==b.size();
		Iterator<String> thisPrefixIter = this.prefixQueue.iterator();
		Iterator<String> bPrefixIter = b.prefixQueue.iterator();
		while(thisPrefixIter.hasNext() && isEqual){
			isEqual= thisPrefixIter.next().equals(bPrefixIter.next());
		}
		return isEqual;
	}
	/** 
	 * Converts the prefix to a formatted String
	 * @return a String containing a sequence of all the words in the prefix
	 */
	public String toFormattedString(){
		String out="";
		Iterator<String> iter = prefixQueue.iterator();
		while(iter.hasNext()){
			out+=iter.next();
			if (iter.hasNext())
				out+=" ";
		}
		return out;
	}
}
