/**
* This class provides the functionality of a splay tree node (adding, searching, deleting, etc.). 
* The class starts with three fields: 
* a left child pointer, 
* a right child pointer, 
* and parent pointer that are all public. 
* Known Bugs: None
*
* @author Jingnu An
* jingnuan@brandeis.edu
* Nov 2, 2020
* COSI 21A PA2
*/
package main;

public class WorkEntrySearchNode implements Comparable<WorkEntrySearchNode> {
	
	public WorkEntrySearchNode left; // KEEP THIS PUBLIC 

	public WorkEntrySearchNode right; // KEEP THIS PUBLIC 

	public WorkEntrySearchNode parent; // KEEP THIS PUBLIC
	
	public Queue<WorkEntry> Q;
	
	public String activity;
	
	/**
	 * constructs a node with a specified activity key. 
	 * This WorkEntrySearchNode will store a collection of WorkEntry objects
	 * that have this specified activity key.
	 * @param activity a string representation of the specified activity key
	 * @runtime O(1)
	 */
	public WorkEntrySearchNode(String activity) {
		Q = new Queue<WorkEntry>();
		this.activity = activity;
		parent = null;
		left = null;
		right = null;
	}
	
	/**
	 * compares two nodes based on their activity keys.
	 * @runtime O(1)
	 */
	public int compareTo(WorkEntrySearchNode other) {
		if(other == null) {//QUESTION!!! IF OTHER IS NULL, WHAT SHOULD I RETURN??
			return -5;
		}else if(this.activity.compareTo(other.activity) == 0) {
			return 0;
		}else if(this.activity.compareTo(other.activity) > 0) {
			return 1;
		}else {
			return -1;
		}
	}
	
	/**
	 * search for a node in the splay tree rooted at this node 
	 * and return the node that matches 
	 * or the last node encountered in the search. 
	 * The node that you return should be splayed to the root.
	 * @param node
	 * @return a node already in the tree that has the same activity key as the given node or the node given 
	 * @runtime O(log n)
	 */
	public WorkEntrySearchNode search(WorkEntrySearchNode node) {
		WorkEntrySearchNode res;
		if(node == null)
			return node;
		//node = this, root is the one we want to find.
		if(this.compareTo(node) == 0) {  
			return this;
		}
		//node < this, search left
		else if(this.compareTo(node) == 1){
			if(this.left != null) {
				res = this.left.search(node);
			}else {
				res = this;
			}	
		}
		//node > this, search right
		else{
			if(this.right != null) {
				res = this.right.search(node);
			}else {
				res = this;
			}
		}
		res.splay();
		return res;
	}
	
	/**
	 * insert a node into the splay tree rooted at this node and 
	 * return what gets inserted or a node already in the tree that matches this key. 
	 * The node that you return should be splayed to the root.
	 * @param node
	 * @return a node already in the tree that has the same activity key as the given node or the node given 
	 * @runtime O(log n)
	 */
	public WorkEntrySearchNode insert(WorkEntrySearchNode node) {
		WorkEntrySearchNode cur = this;
		WorkEntrySearchNode res = cur;
		boolean movedDown = true;
		if(node == null) {
			return node;
		}
		//stop when cur.left is null OR cur.right is null OR cur == node
		while(movedDown) {
			//cur == node, done
			if(cur.compareTo(node) == 0) {
				res = cur;
				movedDown = false;
			}//cur > node, go left
			else if(cur.compareTo(node) == 1) {
				if(cur.left != null) {
					cur = cur.left;
				}else {
					cur.left = node;
					node.parent  = cur;
					res = node;
					movedDown = false;
				}
			}//cur < code
			else{
				if(cur.right != null) {
					cur = cur.right;
				}else {
					cur.right = node;
					node.parent  = cur;
					res = node;
					movedDown = false;
				}
			}
		}
		res.splay();
		return res;
	}
	
	/**
	 * return an in-order traversal of the tree rooted at this node
	 * with the nodes' keys on separate lines
	 * @return a string representation fo the in-order traversal of the tree
	 * @runtime O(n), traverse each node exactly once
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		WorkEntrySearchNode cur = this;
		if(cur != null) {
			if(cur.left != null) {
				sb.append(cur.left.toString()) ;
			}
			sb.append(cur.activity);
			sb.append("\n");
			if(cur.right != null) {
				sb.append(cur.right.toString());
			}
		}
		String res = new String(sb);
		return res;
		
	}
	
	/**
	 * return a String representation using parentheses to display 
	 * the structure of the tree as described in the project spec.
	 * @return a string representation of the structure of the splay tree
	 * @runtime O(n)
	 */
	public String getStructure() {
		//level order traversal 
		WorkEntrySearchNode cur = this;
		StringBuilder sb = new StringBuilder();	
		sb.append("(");
		if(cur.left != null) {
			sb.append(cur.left.getStructure());
		}
		sb.append(cur.activity);
		if(cur.right != null) {
			sb.append(cur.right.getStructure());
		}
		sb.append(")");	
		String res = new String(sb);
		return res;
	}
	
	/**
	 * add a WorkEntry to this node. 
	 * @param e the WorkEntry object to be added into a node in this splay tree
	 * @runtime O(1)
	 */
	public void add(WorkEntry e) {
		Q.enqueue(e);
	}
	
	/**
	 * remove the ith WorkEntry stored in this node. 
	 * @param i the index of the WorkEntry object to be deleted from the node's queue
	 * @return root of this splay tree if after deletion there is at least one WorkEntry in the queue, otherwise it returns null
	 * @runtime O(n)
	 */
	public WorkEntrySearchNode del(int i){
		Node<WorkEntry> cur = new Node<WorkEntry>();
		Node<WorkEntry> prev = new Node<WorkEntry>();
		cur = Q.getHead();
		prev = Q.getHead();
		if(Q.size() < i) {//make sure we have a valid node index
			throw new IndexOutOfBoundsException();
		}//move cur to the node needs to be deleted
		for(int j=0; j<i-1; j++) {
			cur = cur.next;
		}
		for(int k=0; k<i-2; k++) {
			prev = prev.next;
		}//cur and prev have not moved along the linkedlist, so we are deleting the head of the list
		if(cur == prev) {
			Q.dequeue();
		}else {
			prev.next = cur.next;
			Q.size--;
		}
		if(Q.isEmpty()) {//need to remove the current node
			if(this.parent == null) {//we are removing the root
				if(this.left != null) {//if it has a left subtree
					WorkEntrySearchNode rightChild = this.right;
					WorkEntrySearchNode max = this.left.findMax();
					max.splay();
					max.right = rightChild;
					rightChild.parent = max;
					return max;
				}else {//if it doesn't have a left subtree
					return this.right;
				}
			}else {//we are deleting a leaf
				return null;
			}
		}else 
			return this;
	}
	
	/**
	 * return the String representations of the WorkEntry objects associated 
	 * with this node on separate lines followed by the total time spent
	 * on these entries rounded to one decimal place: this will always be either .0 or .5. 
	 * If there are no WorkEntry objects associated with this node, return the empty String.
	 * @return a string representation of the WorkEntry objects in this node's queue
	 * @runtime O(n)
	 */
	public String getEntryData() {
		if(Q.isEmpty()) {//If there are no WorkEntry objects associated with this node, return the empty String.
			return "";
		}else {
			StringBuilder sb = new StringBuilder();
			Node<WorkEntry> cur = new Node<WorkEntry>();
			cur = Q.getHead();
			double time = 0.0;
			for(int i=0; i<Q.size(); i++) {
				sb.append(cur.data.toString());
				time += cur.data.getTimeSpent();
				if(i != Q.size()-1) {
					sb.append("\n");
				}
				cur = cur.next;
			}
			String res = new String(sb);
			res += "\n" + "Total: " + time + " h";
			return res;
		}
	}
	
	/**
	 * This method will return a level order traversal of the tree 
	 * where each node’s key is displayed on a separate line. 
	 * @return return a string representation of a level order traversal of the tree 
	 * @runtime O(n)
	 */
	public String getByRecent() {
		StringBuilder sb = new StringBuilder();
		Queue<WorkEntrySearchNode> q = new Queue<WorkEntrySearchNode>();
		WorkEntrySearchNode cur = this;
		q.enqueue(cur);
		while(!q.isEmpty()){
			cur = q.dequeue();
			sb.append(cur.activity);
			if(cur.left != null) {
				q.enqueue(cur.left);
			}
			if(cur.right != null) {
				q.enqueue(cur.right);
			}
			if(!q.isEmpty()) { //avoid having an empty line at the last
				sb.append("\n");
			}
		}
		String res = new String(sb);
		return res;
	}
	
	/**
	 * This method will splay this node to the root of the tree 
	 * in which it is contained. When implementing this method, 
	 * we consider the splaying cases discussed in class (zig-zig, zig-zag).
	 * @param X a WorkEntrySearchNode to be splayed to the root
	 * @runtime O(log n)
	 */
	public void splay() {
		while(this.parent != null) {//while "this" is not the root
			WorkEntrySearchNode curParent = this.parent;
				//CASE 2, X only has parent, no grandparent. In case 2, X is just one step away from the root, we single rotate, either left or right
			if(curParent.parent == null && curParent.left == this) {//left child of the root
				rotateRight();
			}else if(curParent.parent == null && curParent.right == this) {//right child of the root
				rotateLeft();
			}else {//THIS has both parent and grandparent
				//right-left
				if(curParent.parent.left == curParent && this == curParent.right) {
					this.rotateLeft();
					this.rotateRight();
				}//left-right
				else if(curParent.parent.right == curParent && this == curParent.left) {
					this.rotateRight();
					this.rotateLeft();
				}//left-left,first rotate its parent
				else if(curParent.parent.left == curParent && this == curParent.left) {//left-left
					curParent.rotateRight();
					this.rotateRight();
				}else {//right-right,first rotate its parent
					curParent.rotateLeft();
					this.rotateLeft();
				}
			}
		}	
	}
	
	/**
	 * This method performs a single A VL left rotation to this node.
	 * @param root the root node of the tree
	 * @param v the pivot node (subtree's root, or the parent node of X (X is the node to be brought to the subtree root)), to be rotated to the left
	 * @runtime O(1)
	 */
	public void rotateLeft() {
		WorkEntrySearchNode curParent = this.parent;
		WorkEntrySearchNode target = this;
		curParent.right = target.left;
		if(target.left != null) {
			target.left.parent = curParent;
		}
		target.parent = curParent.parent;
		if(curParent.parent != null) {
			if(curParent == curParent.parent.left) {
				curParent.parent.left = target;
			}else {
				curParent.parent.right = target;
			}
		}
		target.left = curParent;
		curParent.parent = target;
	}
	
	/**
	 * This method performs a single AVL right rotation to this node.
	 * @param root the root node of the tree
	 * @param v is the pivot node (subtree's root, or the parent node of X (X is the node to be brought to the subtree root)), to be rotated to the right
	 * @runtime O(1)
	 */
	public void rotateRight() {
		WorkEntrySearchNode curParent = this.parent;
		WorkEntrySearchNode target = this;
		curParent.left = target.right;
		if(target.right != null) {
			target.right.parent = curParent;
		}
		target.parent = curParent.parent;
		if(curParent.parent != null) {
			if(curParent ==curParent.parent.right) {
				curParent.parent.right = target;
			}else {
				curParent.parent.left = target;
			}
		}
		target.right = curParent;
		curParent.parent = target;
	}
	
	/**
	 * this is a helper method for del(int i). It finds the maximum element on the left subtree
	 * @return the max element in the left subtree
	 * @runtime O(n)
	 */
	public WorkEntrySearchNode findMax() {
		WorkEntrySearchNode cur = this;
		while(cur.right != null) {
			cur = cur.right;
		}
		return cur;
	}
}
