/**********************************************************
 * EECS2101A: Fundamentals of Data Structures, Fall 2023
 * Assignment 3, Problem 3: PrioritySearchTree.java
 * Student Name: Leroy-David Musa
 * Student EECS account: leroy7
 * Student ID number: 219198761
 **********************************************************/

 package A3;

 /**
  * The PrioritySearchTree class represents a data structure for building Priority Search Trees (PST).
  * It provides methods to construct a PST from a set of points, and visualize the resulting complete binary tree
  * and the transformed PST.
  * 
  * @author leroy
  * 
  */
 public class PrioritySearchTree {
     
     private Point[] nonLeafs; 	/* -- stores n - 1 internal nodes -- */
     private int leafCount; 		/* -- stores n external nodes -- */
     
     /** -------- nested class: represents a Point of the PST. ----------- */
      static class Point implements Comparable<Point> {
             private boolean isProcessed; 				// check Point processed status
             private boolean isLeafNode; 			// check if Point is a leaf node
             private int x, y;  						// x and y-coordinates of the Point
             private String name; 					// name associated with the Point
             
             /**
              * Constructor for external nodes.
              * @param name Name associated with the Point.
              * @param x    x-coordinate of the Point.
              * @param y    y-coordinate of the Point.
              */
             public Point(String name, int x, int y) // constructor 
                 { this.isLeafNode = true; this.isProcessed = false; this.x = x; this.y = y; this.name = name; }
 
             /**
              * Copy constructor for internal nodes.
              * @param p Another Point to copy.
              */
             public Point(Point p) // copy constructor
                 { this.isLeafNode = false; this.isProcessed = false; this.x = p.x; this.y = p.y; this.name = p.name; }
             
             @Override
             public String toString() { // ------ toString() method ------
                  return isLeafNode ? String.format("%s", name) : String.format("%s", (name != null) ? name : "n");
             }
             
             /**
              * Set the processed status of the Point.
              * @param processed True if the Point has been processed, false otherwise.
              */
             public void setProcessed(boolean processed) {
                 this.isProcessed = processed;
             }
             
             /**
              * Compare y-coordinates values of two Points.
              * @param p The other Point to compare.
              * @return Integer result of the comparison.
              */
             @Override
             public int compareTo(Point p) throws ClassCastException {
                 boolean A = this != null && !this.isProcessed;
                 boolean B = p != null && !p.isProcessed;
 
                 Integer ya = A ? this.y : null;
                 Integer yb = B ? p.y : null;
 
                 return (ya != null && yb != null) ?
                         Integer.compare(yb, ya) : (ya != null ? -1 : (yb != null ? 1 : 0));
             }
         } // --------- nested Point class definition ends here ------------
 
     /**
      * Constructor for the PrioritySearchTree class.
      * @param external_nodes 	Array of external nodes.
      *                       	Each external node is an instance of the nested Point class,
      *                       	representing a leaf in the Priority Search Tree.
      */
     public PrioritySearchTree(Point[] external_nodes) {
         Point[] N = external_nodes;
         this.nonLeafs = new Point[2*N.length - 1]; 	// space allocation for nodes
         int i = 0;									// initialize index for iteration through external nodes
         while (i < N.length) {
             this.nonLeafs[i + N.length - 1] = N[i]; // copy external nodes to internal nodes
             i++; 									// increment index for next external node
         }
         this.leafCount = N.length; 					// set the count of external node
     }
 
     /**
      * Method to build the Priority Search Tree using bottom-up heap construction.
      */
     public void buildPrioritySearchTree() {
         int i = leafCount - 2;	// start from second to last internal node
         while (i >= 0) {
             heapify(i);			// heapify the subtree rooted at index ‘i’
             i--;				// move to the next internal node
         }
     }
 
     /**
      * Helper method for heapifying the tree.
      * @param t Index of the current node in the heap.
      */
     private void heapify(int t) {
         int lC = 2 * t + 1;										// calculate left child index
         int rC = 2 * t + 2;										// calculate right child index
         int cmp = nonLeafs[lC].compareTo(nonLeafs[rC]);			// compare y-coord of left and right children
         int mci = (cmp == -1) ? lC : ((cmp == 1) ? rC : -1); 	// determine index of child to move (mci) from comparison
         if (mci != -1) {
             nonLeafs[t] = new Point(nonLeafs[mci]);				// copy child node into current node
             if (nonLeafs[mci].isLeafNode) {
                 nonLeafs[mci].setProcessed(true);				// mark external node as processed 
             } else {
                 nonLeafs[mci] = null;							// internal node becomes null
                 heapify(mci);									// recursively heapify the subtree
             }
         }
     }
 
     /**
      * Constructor for TreeNode class.
      * @param data The data (Point) associated with the node.
      */
     static class TreeNode {
         Point data;					// field
         TreeNode lC, rC; 			// left & right child resp.
         public TreeNode(Point data) // constructor
             { this.data = data; }
     }
 
     /**
      * Prints the complete binary tree and the Priority Search Tree for a given set of points.
      */
     public void printTree() {
         PSTvisual print = new PSTvisual();
         TreeNode root = print.buildTreeStructure(nonLeafs);
         PSTvisual.printNode(root);
     }
  
     /**
      * Tester method to create and test Priority Search Trees for different sets of points.
      * @param name The name associated with the set of points e.g p1,p2,p3...
      * @param points  The array of points to be used in constructing the tree.
      */
     public static void testPrioritySearchTree(String name, Point... points) {
         PrioritySearchTree PST = new PrioritySearchTree(points);
         System.out.println("-".repeat(156)+"\n"
                 + "\t(" + name + ") : COMPLETE BINARY TREE\n");
         PST.printTree();
         
         for (Point point : points) {
             System.out.println("\t\t"+ point.name + " = (" + point.x + "," + point.y + ")");
         }
         System.out.println("-".repeat(156)+"\n"
                 + "\t(" + name + ") : TURNED INTO A PRIORITY SEARCH TREE\n");
         PST.buildPrioritySearchTree(); PST.printTree();
     }
 
     /**
      * The main method demonstrates the construction and visualization of Priority Search Trees (PST)
      * for a given set of points.
      */
     public static void main(String[] args) {
         Point[] T = {	new Point("p1", -8, 3), new Point("p2", -7, 1), 
                         new Point("p3", -1, 6), new Point("p4", 2, 4),
                         new Point("p5", 4, 8), new Point("p6", 5, 9), 
                         new Point("p7", 7, 1), new Point("p8", 9, 7)	};
         testPrioritySearchTree("T", T);
     }
 }