/**
* This class is the Node class. It will be used in implementing the Queue class
* Known Bugs: None
*
* @author Jingnu An
* jingnuan@brandeis.edu
* Nov 2, 2020
* COSI 21A PA2
*/
package main;
	
public class Node<T> {
	T data;
	Node<T> next;
	
	public Node(){
		 data = null;
		 next = null;
	}
	
	public Node(T data) {
		this.data = data;
		next = null;
	}
	
	public Node(T data, Node<T> next) {
		this.data = data;
		this.next = next;
	}
}
