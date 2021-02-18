/**
* This class provides the “command line” functionality that parses user inputs 
* and performs operations on the data provided in the CSV file 
* by wrapping the data structures created in WorkEntrySearchNode.java and Queue.java.
* Known Bugs: None
*
* @author Jingnu An
* jingnuan@brandeis.edu
* Nov 2, 2020
* COSI 21A PA2
*/
package main;

public class WorkTimeAnalysisTool {
	
	public WorkEntrySearchNode root;
	public boolean validSearch;
	
	/**
	 * constructs the tool given an array of WorkEntry objects
	 * @param entries an array of WorkEntry objects to be used for constructing a tool object
	 * runtime O(nlog(n))
	 */
	public WorkTimeAnalysisTool(WorkEntry[] entries) {
		validSearch = false;//havent searched yet
		//initialize the tree root, use the first WorkEntry object's activity key in the entires array
		String  firstEntryKey = entries[0].getActivity();
		root = new WorkEntrySearchNode(firstEntryKey);
		//build the splay tree
		for(int i=0; i<entries.length; i++) {
			String curKey = entries[i].getActivity();//grab the key
			WorkEntrySearchNode curNode = new WorkEntrySearchNode(curKey);//use a temp WorkEntrySearchNode node to hold the key, so we can search in the tree
			WorkEntrySearchNode targetNode = root.insert(curNode);//the node to insert this WorkEntry, either found an existing one with the same key, or inserted
			targetNode.add(entries[i]);
			root = targetNode;
		}
	}
	
	/**
	 * parse a given command. 
	 * If the command is a “list” or “search” command, 
	 * then it should return the result of the list or search. 
	 * If the command is “del”, then this should return null.
	 * @param cmd a string representation of a given command.
	 * @return a string representing the search/list result
	 * runtime O(n) the worst case
	 */
	public String parse(String cmd) {
		String res = "";
		String keyWord = cmd.substring(0, cmd.indexOf(" "));
		String detail = cmd.substring(cmd.indexOf(" ")+1);
		
		if(keyWord.compareTo("list") == 0) {
			if(detail.compareTo("l") == 0) {//list l 
				String temp = root.toString();
				res = temp.substring(0, temp.length()-1);
			}else if(detail.compareTo("r") == 0) {//list r
				res = root.getByRecent();
			}
		}else if(keyWord.compareTo("search") == 0) {
			validSearch = false;
			String activity = detail.substring(1, detail.length() - 1);
			WorkEntrySearchNode tempNode = new WorkEntrySearchNode(activity);//wrap the activity key into a node for search
			WorkEntrySearchNode returnedNode = root.search(tempNode);
			root = returnedNode;
			if(returnedNode.activity.compareTo(activity) == 0) {//find the specified node
				res = root.getEntryData();
				validSearch = true;
			}
		}else if(keyWord.compareTo("del") == 0) {
			if(!validSearch) {//no valid search precede
				throw new IllegalStateException();
			}else { //has valid search precede
				int index = stringToInt(detail);
				int Qsize = root.Q.size();
				if(index >= Qsize || index < 0) {//invalid index
					throw new IndexOutOfBoundsException();
				}else {//valid search + valid index, we can del
					root.del(index);
				}
			}
		}
		return res;
	}
	
	/**
	 * This method parse a string of numbers into integer
	 * @param s a string representation of an integer
	 * @return integer unwrapped from the given string
	 * runtime O(n)
	 */
	public int stringToInt(String s) {
		int tens = 1;
		int res = 0;
		for(int i=s.length()-1; i>=0; i-- ) {
			res += ((int) (s.charAt(i) - '0')) * tens;
			tens *= 10;
		}
		return res;
	}
	
	
}
