package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.Queue;

public class QueueTest {
	@Test
	public void queueTest(){
		Queue<String> q = new Queue<String>();
		//test basic enqueue, dequeue, size
		assertEquals(0, q.size());
		assertEquals(null, q.dequeue());
		
		q.enqueue("hello");
		assertEquals(q.front(), "hello");
		assertEquals(1, q.size());

		q.enqueue("data");
		q.enqueue("structure");
		assertEquals(q.size(), 3);
		assertEquals("hello", q.dequeue());
		
		assertEquals(q.size(), 2);
		assertEquals("data", q.dequeue());
		
		assertEquals(q.size(), 1);
		assertEquals("structure", q.dequeue());
		
		assertEquals(q.size(), 0);
		assertEquals(null, q.dequeue());
		
		//test overall structure
		q.enqueue("Happy");
		q.enqueue("Coding");
		q.enqueue("at");
		q.enqueue("Brandeis");
		q.enqueue("University");
		int i=0;
		StringBuilder sb = new StringBuilder();
		while(i < q.size()) {
			sb.append(q.dequeue());
			sb.append(" ");
		}
		String res = new String(sb);
		assertEquals("Happy Coding at Brandeis University ", res);
	}
}
