package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.InputFileReader;
import main.Queue;
import main.WorkEntry;
import main.WorkEntrySearchNode;
import main.WorkTimeAnalysisTool;

class StudentTreeTest {
	
	@Test
	public void testConstructor() {
		WorkEntrySearchNode node = new WorkEntrySearchNode("Activity Key");
		assertEquals(node.activity, "Activity Key");
		assertEquals(node.Q.size(), 0);
		assertEquals(node.parent, null);
		assertEquals(node.left, null);
		assertEquals(node.left, null);
	}

	@Test
	public void testSplay() {
		//build a tree
		//		t1
		//	   /  \
		//	  t2  t3
		//   /  \    
		//  t4  t5
		//     /  \
		//    t6  t7
		WorkEntrySearchNode t1 = new WorkEntrySearchNode("1t");
		WorkEntrySearchNode t2 = new WorkEntrySearchNode("2t");
		WorkEntrySearchNode t3 = new WorkEntrySearchNode("3t");
		WorkEntrySearchNode t4 = new WorkEntrySearchNode("4t");
		WorkEntrySearchNode t5 = new WorkEntrySearchNode("5t");
		WorkEntrySearchNode t6 = new WorkEntrySearchNode("6t");
		WorkEntrySearchNode t7 = new WorkEntrySearchNode("7t");
		
		t1.parent = null;
		t1.left = t2;
		t1.right = t3;
		t2.parent = t1;
		t3.parent = t1;
		
		t2.left = t4;
		t2.right = t5;
		t4.parent = t2;
		t5.parent = t2;
		
		t5.left = t6;
		t5.right = t7;
		t6.parent = t5;
		t7.parent = t5;
		
		//test splay the root
		t1.splay();
		assertEquals(t1.parent, null);
		assertEquals(t1.left, t2);
		assertEquals(t1.right, t3);
		assertEquals(t2.left, t4);
		assertEquals(t2.right, t5);
		assertEquals(t5.left, t6);
		assertEquals(t5.right, t7);
		assertEquals(t5.parent, t2);
		assertEquals(t4.parent, t2);
		assertEquals(t2.parent, t1);
		assertEquals(t3.parent, t1);
		assertEquals(t6.parent, t5);
		assertEquals(t7.parent, t5);
		
		
		//splay t2, left child of the root
		//test rotate right 
		t2.rotateRight();
		assertEquals(t2.right, t1);
		assertEquals(t2.left, t4);
		assertEquals(t2.parent, null);
		assertEquals(t1.parent, t2);
		assertEquals(t1.left, t5);
		assertEquals(t1.right, t3);
		assertEquals(t5.parent, t1);
		assertEquals(t5.left, t6);
		assertEquals(t5.right, t7);
		
		//splay t1, right child of the root
		//test rotate left
		t1.rotateLeft();
		assertEquals(t1.parent, null);
		assertEquals(t1.right, t3);
		assertEquals(t1.left, t2);
		assertEquals(t2.parent, t1);
		assertEquals(t2.left, t4);
		assertEquals(t2.right, t5);
		assertEquals(t5.parent, t2);
		assertEquals(t5.left, t6);
		assertEquals(t5.right, t7);
		
		//left-right,spaly t5
		//		t1				t1				[t5]
		//	   /  \			   /  \			   /   \
		//	  t2  t3		 [t5]  t3		  t2   t1
		//   /  \      =>    /  \		=>   / \   / \
		//  t4  [t5] 		t2	t7			t4 t6 t7 t3
		//     /  \		   /  \
		//    t6  t7	  t4  t6
		
		t5.splay();
		assertEquals(t5.parent, null);
		assertEquals(t5.left, t2);
		assertEquals(t5.right, t1);
		assertEquals(t2.parent, t5);
		assertEquals(t2.left, t4);
		assertEquals(t2.right, t6);
		assertEquals(t1.parent, t5);
		assertEquals(t1.left, t7);
		assertEquals(t1.right, t3);
		
		//right-left, splay t7	
		//	    t5				   		 t5						 [t7]
		//	  /    \				   /    \					 /   \
		//	 t2    t1		=>  	  t2    [t7]		=>	   t5	 t1
		//  / \   /  \				/   \     \				   /  	   \
		// t4 t6 [t7] t3		   t4   t6	  t1			  t2	   t3
		//										\			 /  \
		//										t3		    t4  t6
		t7.splay();
		assertEquals(t7.parent, null);
		assertEquals(t7.left, t5);
		assertEquals(t7.right, t1);
		assertEquals(t5.left, t2);
		assertEquals(t1.right, t3);
		assertEquals(t2.left, t4);
		assertEquals(t2.right, t6);
		assertEquals(t5.parent, t7);
		assertEquals(t1.parent, t7);
		assertEquals(t2.parent, t5);
		assertEquals(t3.parent, t1);
		assertEquals(t4.parent, t2);
		assertEquals(t6.parent, t2);
		
		//left-left, splay t2
		//	      t7					  t5					  [t2]
		//	    /   \				    /    \					  /  \
		//	  t5	 t1 			  [t2]   t7                  t4   t5
		//    /  	   \		=>    /  \     \		=>		     /  \
		// 	[t2]	   t3            t4  t6    t1			   		t6	t7
		//  /  \								 \						  \
		// t4  t6								 t3					      t1
		//																	\
		//																	t3
		t2.splay();
		assertEquals(t2.parent, null);
		assertEquals(t4.parent, t2);
		assertEquals(t5.parent, t2);
		assertEquals(t6.parent, t5);
		assertEquals(t7.parent, t5);
		assertEquals(t1.parent, t7);
		assertEquals(t3.parent, t1);
		assertEquals(t2.left, t4);
		assertEquals(t2.right, t5);
		assertEquals(t5.left, t6);
		assertEquals(t5.right, t7);
		assertEquals(t7.right, t1);
		assertEquals(t1.right, t3);
		
		//right-right, splay t7
		//	     [t7]					  t5					   t2
		//	    /   \				    /    \					  /  \
		//	  t5	 t1 			   t2    [t7]                t4   t5
		//    /  	   \	   <=     /  \     \		<=		     /  \
		// 	t2	   		t3           t4  t6    t1			   		t6	[t7]
		//  /  \								 \						  \
		// t4  t6								 t3					      t1
		//																	\
		//																	t3
		t7.splay();
		assertEquals(t7.parent, null);
		assertEquals(t7.left, t5);
		assertEquals(t7.right, t1);
		assertEquals(t5.left, t2);
		assertEquals(t1.right, t3);
		assertEquals(t2.left, t4);
		assertEquals(t2.right, t6);
		assertEquals(t5.parent, t7);
		assertEquals(t1.parent, t7);
		assertEquals(t2.parent, t5);
		assertEquals(t3.parent, t1);
		assertEquals(t4.parent, t2);
		assertEquals(t6.parent, t2);
		
	}
	
	@Test
	public void testToString() {
		//build a tree
		//		t1				g7			
		//	   /  \			   /  \			   
		//	  t2  t3		  b2  h9		  
		//   /  \      =     /  \		
		//  t4  t5 			a1	d4			
		//     /  \		   	   /  \
		//    t6  t7	  	  c3  e5
		
		WorkEntrySearchNode t1 = new WorkEntrySearchNode("g7");
		WorkEntrySearchNode t2 = new WorkEntrySearchNode("b2");
		WorkEntrySearchNode t3 = new WorkEntrySearchNode("h9");
		WorkEntrySearchNode t4 = new WorkEntrySearchNode("a1");
		WorkEntrySearchNode t5 = new WorkEntrySearchNode("d4");
		WorkEntrySearchNode t6 = new WorkEntrySearchNode("c3");
		WorkEntrySearchNode t7 = new WorkEntrySearchNode("e5");
		
		t1.parent = null;
		t1.left = t2;
		t1.right = t3;
		t2.parent = t1;
		t3.parent = t1;
		
		t2.left = t4;
		t2.right = t5;
		t4.parent = t2;
		t5.parent = t2;
		
		t5.left = t6;
		t5.right = t7;
		t6.parent = t5;
		t7.parent = t5;
		
		String res = t1.toString();
		assertEquals("a1\n" + 
				"b2\n" + 
				"c3\n" + 
				"d4\n" + 
				"e5\n" + 
				"g7\n" + 
				"h9\n", res);
		res = t2.toString();
		assertEquals("a1\nb2\nc3\nd4\ne5\n", res);

	}
	
	@Test
	public void testGetStructure() {
		//build a tree
		WorkEntrySearchNode t1 = new WorkEntrySearchNode("g");
		WorkEntrySearchNode t2 = new WorkEntrySearchNode("b");
		WorkEntrySearchNode t3 = new WorkEntrySearchNode("h");
		WorkEntrySearchNode t4 = new WorkEntrySearchNode("a");
		WorkEntrySearchNode t5 = new WorkEntrySearchNode("d");
		WorkEntrySearchNode t6 = new WorkEntrySearchNode("c");
		WorkEntrySearchNode t7 = new WorkEntrySearchNode("e");
		
		t1.parent = null;
		t1.left = t2;
		t1.right = t3;
		t2.parent = t1;
		t3.parent = t1;
		
		t2.left = t4;
		t2.right = t5;
		t4.parent = t2;
		t5.parent = t2;
		
		t5.left = t6;
		t5.right = t7;
		t6.parent = t5;
		t7.parent = t5;
		//		t1				g			
		//	   /  \			   / \			   
		//	  t2  t3		  b   h		  
		//   /  \      =     / \		
		//  t4  t5 			a	d			
		//     /  \		   	   / \
		//    t6  t7	  	  c   e
		
		assertEquals(t3.getStructure(), "(h)");
		assertEquals(t5.getStructure(), "((c)d(e))");
		assertEquals(t2.getStructure(), "((a)b((c)d(e)))");
		assertEquals(t1.getStructure(), "(((a)b((c)d(e)))g(h))");
		
	}
	
	@Test
	public void testAdd() {
		//build a tree
		//		t1				g			
		//	   /  \			   / \			   
		//	  t2  t3		  b   h		  
		//   /  \      =     / \		
		//  t4  t5 			a	d			
		//     /  \		   	   / \
		//    t6  t7	  	  c   e
		WorkEntrySearchNode t1 = new WorkEntrySearchNode("g");
		WorkEntrySearchNode t2 = new WorkEntrySearchNode("b");
		WorkEntrySearchNode t3 = new WorkEntrySearchNode("h");
		WorkEntrySearchNode t4 = new WorkEntrySearchNode("a");
		WorkEntrySearchNode t5 = new WorkEntrySearchNode("d");
		WorkEntrySearchNode t6 = new WorkEntrySearchNode("c");
		WorkEntrySearchNode t7 = new WorkEntrySearchNode("e");
		
		t1.parent = null;
		t1.left = t2;
		t1.right = t3;
		t2.parent = t1;
		t3.parent = t1;
		
		t2.left = t4;
		t2.right = t5;
		t4.parent = t2;
		t5.parent = t2;
		
		t5.left = t6;
		t5.right = t7;
		t6.parent = t5;
		t7.parent = t5;
		
		//test insert a WorkEntry with activity "d"
		WorkEntry e = new WorkEntry("11/5/2020", 1.5, "d", "help student with pa2");
		t5.add(e);
		assertEquals(t5.Q.size(), 1);
		String s = t5.Q.front().toString();
		assertEquals("[11/5/2020] d (1.5 h): help student with pa2", s);

	}
	
	@Test
	public void testDel() {
		//build a tree
		//		t1				g			
		//	   /  \			   / \			   
		//	  t2  t3		  b   h		  
		//   /  \      =     / \		
		//  t4  t5 			a	d			
		//     /  \		   	   / \
		//    t6  t7	  	  c   e
		WorkEntrySearchNode t1 = new WorkEntrySearchNode("g");
		WorkEntrySearchNode t2 = new WorkEntrySearchNode("b");
		WorkEntrySearchNode t3 = new WorkEntrySearchNode("h");
		WorkEntrySearchNode t4 = new WorkEntrySearchNode("a");
		WorkEntrySearchNode t5 = new WorkEntrySearchNode("d");
		WorkEntrySearchNode t6 = new WorkEntrySearchNode("c");
		WorkEntrySearchNode t7 = new WorkEntrySearchNode("e");			
		t1.parent = null;
		t1.left = t2;
		t1.right = t3;
		t2.parent = t1;
		t3.parent = t1;	
		t2.left = t4;
		t2.right = t5;
		t4.parent = t2;
		t5.parent = t2;
		t5.left = t6;
		t5.right = t7;
		t6.parent = t5;
		t7.parent = t5;
		
		WorkEntry e1 = new WorkEntry("11/5/2020", 1.5, "g", "help student with pa2");
		WorkEntry e2 = new WorkEntry("11/5/2020", 1.5, "b", "help student with pa2");
		WorkEntry e3 = new WorkEntry("11/5/2020", 1.5, "h", "help student with pa2");
		WorkEntry e4 = new WorkEntry("11/5/2020", 1.5, "a", "help student with pa2");
		WorkEntry e5 = new WorkEntry("11/5/2020", 1.5, "d", "help student with pa2");
		WorkEntry e6 = new WorkEntry("11/5/2020", 1.5, "c", "help student with pa2");
		WorkEntry e7 = new WorkEntry("11/5/2020", 1.5, "e", "help student with pa2");
		t1.add(e1);
		t2.add(e2);
		t3.add(e3);
		t4.add(e4);
		t5.add(e5);
		t6.add(e6);
		t7.add(e7);
		
		//test del on a node that contains only one WorkEntry, after del it should be deleted
		t1.del(1); //node t1 should be deleted
		assertEquals(t7.getStructure(), "((((a)b(c))d)e(h))");
		assertEquals(t7.toString(), "a\nb\nc\nd\ne\nh\n");
		
	}
	
	@Test
	public void testCompareTo() {
		WorkEntrySearchNode node1 = new WorkEntrySearchNode("office hours");
		WorkEntrySearchNode node2 = new WorkEntrySearchNode("met with tas");
		WorkEntrySearchNode node3 = new WorkEntrySearchNode("grading hw");
		WorkEntrySearchNode node4 = new WorkEntrySearchNode("grading exam");
		
		//test compareTo, WorkEntrySearchNode nodes are compared based on their activity key, lexicographically
		assertTrue(node4.compareTo(node3) == -1);
		assertTrue(node1.compareTo(node2) == 1);
		assertTrue(node3.compareTo(node2) == -1);
		assertTrue(node2.compareTo(node4) == 1);
		assertTrue(node1.compareTo(node1) == 0);
		assertTrue(node4.compareTo(null) == -5);
	}
	
	@Test
	public void testSearch() {
		//build a tree
		//		t1				g			
		//	   /  \			   / \			   
		//	  t2  t3		  b   h		  
		//   /  \      =     / \		
		//  t4  t5 			a	d			
		//     /  \		   	   / \
		//    t6  t7	  	  c   e
		WorkEntrySearchNode t1 = new WorkEntrySearchNode("g");
		WorkEntrySearchNode t2 = new WorkEntrySearchNode("b");
		WorkEntrySearchNode t3 = new WorkEntrySearchNode("h");
		WorkEntrySearchNode t4 = new WorkEntrySearchNode("a");
		WorkEntrySearchNode t5 = new WorkEntrySearchNode("d");
		WorkEntrySearchNode t6 = new WorkEntrySearchNode("c");
		WorkEntrySearchNode t7 = new WorkEntrySearchNode("e");			
		t1.parent = null;
		t1.left = t2;
		t1.right = t3;
		t2.parent = t1;
		t3.parent = t1;	
		t2.left = t4;
		t2.right = t5;
		t4.parent = t2;
		t5.parent = t2;
		t5.left = t6;
		t5.right = t7;
		t6.parent = t5;
		t7.parent = t5;
		
		WorkEntry e1 = new WorkEntry("11/5/2020", 1.5, "g", "help student with pa2");
		WorkEntry e2 = new WorkEntry("11/5/2020", 1.5, "b", "help student with pa2");
		WorkEntry e3 = new WorkEntry("11/5/2020", 1.5, "h", "help student with pa2");
		WorkEntry e4 = new WorkEntry("11/5/2020", 1.5, "a", "help student with pa2");
		WorkEntry e5 = new WorkEntry("11/5/2020", 1.5, "d", "help student with pa2");
		WorkEntry e6 = new WorkEntry("11/5/2020", 1.5, "c", "help student with pa2");
		WorkEntry e7 = new WorkEntry("11/5/2020", 1.5, "e", "help student with pa2");
		t1.add(e1);
		t2.add(e2);
		t3.add(e3);
		t4.add(e4);
		t5.add(e5);
		t6.add(e6);
		t7.add(e7);
		
		//search the root
		WorkEntrySearchNode searchNode = new WorkEntrySearchNode("g");
		WorkEntrySearchNode returnedNode = t1.search(searchNode);
		assertTrue(searchNode.activity.compareTo(returnedNode.activity) == 0);
		//assert the tree structure didn't change
		assertEquals(t1.parent, null);
		assertEquals(t1.left, t2);
		assertEquals(t1.right, t3);
		assertEquals(t2.left, t4);
		assertEquals(t2.right, t5);
		assertEquals(t5.left, t6);
		assertEquals(t5.right, t7);
		assertEquals(t5.parent, t2);
		assertEquals(t4.parent, t2);
		assertEquals(t2.parent, t1);
		assertEquals(t3.parent, t1);
		assertEquals(t6.parent, t5);
		assertEquals(t7.parent, t5);
		
		//search for a non-existing node, greater than any of the existing node activity
		searchNode.activity = "z";
		returnedNode = t1.search(searchNode);
		assertEquals(returnedNode.getStructure(), "((((a)b((c)d(e)))g)h)");
		
		//search for a non-existing node, in the middle of the existing node activity 
		searchNode.activity = "f";
		returnedNode = t1.search(searchNode);
		assertEquals(returnedNode.getStructure(), "((((a)b(c))d)e(g(h)))");
		
		//search for an existing node
		searchNode.activity = "d";
		returnedNode = t1.search(searchNode);
		assertEquals(returnedNode.getStructure(), "(((((a)b(c))d)e)g(h))");
	}
	
	@Test
	public void testInsert() {
		//build a tree
		//		t1				g			
		//	   /  \			   / \			   
		//	  t2  t3		  b   h		  
		//   /  \      =     / \		
		//  t4  t5 			a	d			
		//     /  \		   	   / \
		//    t6  t7	  	  c   e
		WorkEntrySearchNode t1 = new WorkEntrySearchNode("g");
		WorkEntrySearchNode t2 = new WorkEntrySearchNode("b");
		WorkEntrySearchNode t3 = new WorkEntrySearchNode("h");
		WorkEntrySearchNode t4 = new WorkEntrySearchNode("a");
		WorkEntrySearchNode t5 = new WorkEntrySearchNode("d");
		WorkEntrySearchNode t6 = new WorkEntrySearchNode("c");
		WorkEntrySearchNode t7 = new WorkEntrySearchNode("e");			
		t1.parent = null;
		t1.left = t2;
		t1.right = t3;
		t2.parent = t1;
		t3.parent = t1;	
		t2.left = t4;
		t2.right = t5;
		t4.parent = t2;
		t5.parent = t2;
		t5.left = t6;
		t5.right = t7;
		t6.parent = t5;
		t7.parent = t5;
		
		//insert a node that is already in the tree
		WorkEntrySearchNode insertNode1 = new WorkEntrySearchNode("d");
		WorkEntrySearchNode returnedNode1 = t1.insert(insertNode1);
		assertEquals(returnedNode1.parent, null);
		assertEquals(returnedNode1.left, t2);
		assertEquals(returnedNode1.right, t1);
		assertEquals(t2.left, t4);
		assertEquals(t2.right, t6);
		assertEquals(t1.left, t7);
		assertEquals(t1.right, t3);
		
		//insert a node that does not exist in the tree yet
		WorkEntrySearchNode insertNode = new WorkEntrySearchNode("f");
		WorkEntrySearchNode returnedNode = t1.insert(insertNode);
		assertEquals(returnedNode.parent, null);
		assertEquals(returnedNode.activity, "f");
		assertEquals(returnedNode.getStructure(), "((((a)b(c))d(e))f(g(h)))");
	}
	
	@Test
	public void testGetByRecent() {
		//create an array of WorkEntry objects
		WorkEntry[] entries = InputFileReader.getWorkEntriesFromCSV("pdf_table_csv.txt");
		
		//test tool constructor, build a splay tree
		WorkTimeAnalysisTool tool = new WorkTimeAnalysisTool(entries);
		WorkEntrySearchNode cur = tool.root;
		assertEquals(cur.parent, null);
		
		//test getByRecent, especially when there is a search call preceding
		assertEquals(cur.getByRecent(), "office hours\nmet with tas\ngrading hw\ngrading exam");
		WorkEntrySearchNode newCur = cur.search(new WorkEntrySearchNode("grading pa"));
		assertEquals(newCur.getByRecent(), "grading hw\ngrading exam\nmet with tas\noffice hours");
		WorkEntrySearchNode newNewCur = newCur.search(new WorkEntrySearchNode("office hours"));
		assertEquals(newNewCur.getByRecent(), "office hours\nmet with tas\ngrading hw\ngrading exam");
	}
	
	@Test
	public void testGetEntryData() {
		//create an array of WorkEntry objects, using the provided CSV file
		WorkEntry[] entries = InputFileReader.getWorkEntriesFromCSV("pdf_table_csv.txt");
		WorkTimeAnalysisTool tool = new WorkTimeAnalysisTool(entries);
		WorkEntrySearchNode cur = tool.root;
		WorkEntrySearchNode returned = cur.search(new WorkEntrySearchNode("office hours"));
		assertEquals(returned.getEntryData(), 
				"[9/18/2019] office hours (1.5 h): helped students with pa1\n" + 
				"[10/1/2019] office hours (1.0 h):\n" + 
				"Total: 2.5 h");
		
		//test calling getEntryData on a WorkEntrySearchNode that has an empty Q
		WorkEntrySearchNode noEntry = new WorkEntrySearchNode("noEntry");
		assertEquals(noEntry.getEntryData(), "");
	}

}
