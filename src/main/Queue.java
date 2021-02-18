/**
* Queue.java
* This class will hold my implementation of a generic FIFO queue 
* that I will use in my implementation of getByRecent() in WorkEntrySearchNode.java.
* Known Bugs: None
*
* @author Jingnu An
* jingnuan@brandeis.edu
* Nov 2, 2020
* COSI 21A PA2
*/
package main;

public class Queue<T> {
	Node<T> head;
	Node<T> tail;
	int size;
	
	/**
	 * constructs an empty queue
	 * runtime O(1)
	 */
	public Queue() {
		head = new Node<T>();
		tail = new Node<T>();
		size = 0;
	}
	
	/**
	 * enqueues a data element at the end of the queue.
	 * @param data a generic type of element to be enqueued
	 * runtime: O(1)
	 */
	public void enqueue(T data) {
		Node<T> cur = new Node<T>(data);
		if(size == 0) {//adding the first node
			head = cur;
			tail = cur;
		}else {
			tail.next = cur;
			tail = cur;
		}
		size++;
	}

	/**
	 * dequeues the data element at the front of the queue and returns it. If
	 * there are no elements in the queue, return null.
	 * @return the data element at the front of the queue
	 * runtime O(1)
	 */
	public T dequeue() {
		if(size == 0) {//empty queue
			return null;
		}else {
			size--;
			Node<T> temp = head; //points to the head node
			head = head.next; //move head one to the right (delete it)
			return temp.data; //return temp pointer's data
		}
	}
	
	/**
	 * gets the data at the front of the queue (i.e. the one that would be
	 * dequeued next). If there are no elements in the queue, return null.
	 * @return the data at the front of the queue
	 * runtime O(1)
	 */
	public T front() {
		if(size == 0) {
			return null;
		}else {
			return head.data;
		}
	}
	
	/**
	 *  return the number of elements in the queue.
	 * @return integer, the number of elements in the queue.
	 * runtime O(1)
	 */
	public int size() {
		return size;
	}
	
	
	/**
	 * Check if the queue is empty
	 * @return true if it's empty, false otherwise
	 * runtime O(1)
	 */
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}else
			return false;
	}
	
	/**
	 * returns the head of this queue
	 * @return a pointer that points to the head of this queue
	 * runtime O(1)
	 */
	public Node<T> getHead() {
		return head;
	}
}
