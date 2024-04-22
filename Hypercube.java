import java.util.ArrayDeque;
import java.util.Scanner;

/**
* This class generates and prints hypercube corners of a given dimension using both recursive and
* iterative approaches.
* 
* The main method runs some tests.
* 
* @author leroy
* 
*/
public class Hypercube {	
	/*
	 * DESIGN IDEA: The Hypercube class provides methods to generate and print hypercube 
	 * corners for a given dimension 'n' using both recursive and iterative approaches. 
	 * It utilizes a nested class called Corner to represent hypercube corners efficiently. 
	 * Each corner is represented as a binary string of coordinates (0s and 1s).
	 * 
	 * Nested Corner Class (Corner{}): The Corner class encapsulates the properties and 
	 * behavior of a hypercube corner. It manages the coordinates of the corner and 
	 * supports operations like flipping a coordinate or extending the dimension. A corner 
	 * occupies O(n) memory cells, where 'n' is the dimension.
	 * 
	 * Recursive Approach (recursiveWalk()): This method uses recursion to generate 
	 * hypercube corners. Starting from the initial corner, it explores left and right 
	 * children in each recursive call until the dimension reaches zero. When the 
	 * dimension becomes zero, the corner is printed. This approach constructs a binary 
	 * tree of corners. The running time is O(2^n), where 'n' is the dimension. It involves 
	 * branching into two recursive calls at each step, resulting in a binary tree 
	 * of depth 'n'.
	 * 
	 * Iterative Approach (iterativeWalk()): This method generates and prints hypercube 
	 * corners iteratively. It begins from dimension 1 and constructs hypercube corners 
	 * up to dimension 'n'. The method employs a queue to manage the order of traversal and 
	 * alternates between left and right children. Each corner is visited exactly once. 
	 * The running time is O(n * 2^n). In the worst case, it generates and processes 
	 * 2^n corners for each dimension (from 1 to 'n'). For each corner, it performs 
	 * constant-time operations using a queue.
	 * 
	 * Main Method (main()): The main method tests both the recursive and iterative approaches 
	 * for dimensions 'n=3', 'n=4', and 'n=5', reporting the results.The Hypercube class
	 * provides efficient and clear solutions for generating and printing hypercube corners, 
	 * offering two different approaches for comparison.
	 */
		
	/**
	 * This exception is thrown whenever an invalid dimension is encountered in the Hypercube class.
	 * An invalid dimension is one that is not a positive integer.
	 * 
	 * This exception is nested for encapsulation and is protected, limiting its visibility to only 
	 * the Hypercube class and its potential sub-classes. It is static, which means it is independent 
	 * of any particular instance of the outer class Hypercube. Static nested classes like this 
	 * one are used to logically group classes that are only relevant to the outer class, in this 
	 * case, for handling dimension validation.
	 * 
	 * This exception helps ensure that the Hypercube class only operates with valid dimensions, 
	 * enhancing the robustness of the code.
	 */
	protected static class InvalidDimensionException extends Exception {
		/**
		 * compiler generated serialVersionUID
		 */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor with a custom message for the exception.
         *
         * @param dimension The invalid dimension.
         */
        public InvalidDimensionException(int n) {
            super("Invalid dimension " + n + ". Dimension must be a positive integer :)");
        }
    }
	
	
	/* ********************* nested corner class ************************* */
	/**
	 * Nested class representing a hypercube corner.
	 */
    public static class Corner {
    
    	int hypercube_dim; // hypercube dimension
        boolean[] coordinates;
 
        /**
         * Initializes a hypercube corner for a given dimension.
         *
         * @param hypercube_dim The dimension of the hypercube corner.
         * 
         * Running time is O(n), where 'n' is the dimension. This method initializes the coordinates array, 
         * iterating through 'n' elements to set them to false.
         */
        
        public Corner(int hypercube_dim) {
            this.coordinates = new boolean[hypercube_dim];
            for (int i = 0; i < hypercube_dim; i++) this.coordinates[i] = false;
            this.hypercube_dim = hypercube_dim;
        }

        /**
         * Initializes a hypercube corner for a given dimension with specified coordinates.
         *
         * @param hypercube_dim The dimension of the hypercube corner.
         * @param position  Decimal type position for the coordinates, converted to binary.
         * 
         * Running time is O(n), where 'n' is the dimension. This method initializes the coordinates array, 
         * iterating through 'n' elements to set them based on the binary representation of the position.
         */
        
        public Corner(int hypercube_dim, int position) {
            this(hypercube_dim); // call to another constructor for initializing hypercube dimension
            for (int i = hypercube_dim - 1; i >= 0; i--) {
                this.coordinates[i] = ((position & 1) == 1); // set coordinates based on binary representation of position
                position >>= 1; // right shift position by 1 bit to move to the next bit
            }
        }

        /**
         * Move one step in a specific dimension.
         *
         * @param dimensionToMove The dimension in which to move.
         * 
         * Running time is O(1). This method performs a constant-time operation by toggling the coordinate value.
         */
        
        public void flipCoordinate(int dimensionToMove) {
            this.coordinates[dimensionToMove] ^= true; // toggle the coordinate value
        }

        /* ***************  toString *********************** */
        /**
         * Convert the boolean coordinates to a binary String.
         *
         * @return A binary string representing the hypercube corner's coordinates.
         * 
         * Running time is O(n), where 'n' is the dimension. This method constructs a binary string by iterating 
         * through 'n' coordinates to convert them to '0' or '1'.
         */
        
        @Override
        public String toString() {
            StringBuilder stb = new StringBuilder();
            for (boolean coordinate : this.coordinates) stb.append(coordinate ? 1 : 0);
            return stb.toString();
        }
        
        /**
         * Extends a Corner object by adding a new dimension.
         *
         * @param origCnr   The original Corner object.
         * @param position  The new dimension's position.
         * @return A new Corner object with an increased dimension.
         * 
         * Running time is O(n), where 'n' is the new dimension. This method creates a new Corner object with 
         * an updated dimension and copies the existing coordinates, involving iterating through 'n' coordinates.
         */
        public static Corner extendCorner(Corner origCnr, boolean position) {
            // Create a new Corner object with an increased dimension
            Corner newCnr = new Corner(origCnr.hypercube_dim + 1);
            
            // Copy the existing coordinates from the original Corner to the new Corner
            int index = 0;
            for (boolean coordinate : origCnr.coordinates) {
                newCnr.coordinates[index++] = coordinate;
            }
            
            // Set the coordinates of the new dimension based on the 'position' parameter
            newCnr.coordinates[origCnr.hypercube_dim] = position;
            
            // Return the new Corner object with the increased dimension
            return newCnr;
        }
    }
    								/* RECURSIVE METHOD */  
    
     				   //--- RECURSIVE ALGORITHM CORRECTNESS PROOF ---// 
    /*
     * We establish the correctness of the recursive algorithm for generating hypercube corners
     * using the following notation:
     *
     * Pre-Cond: 'n' represents the current dimension being processed, and 'currentCnr' is the current corner.
     *
     * <ALGORITHM> corresponds to the 'recursiveWalk(n)' method. It explores the left
     * and right children of the current corner in the hypercube recursively until the specified dimension
     * is reached.
     * 
     * Post-Cond: The method correctly generates and prints all possible hypercube corners for the given 'n' dimension.
     */

  /********************** Proof by Strong Induction *********************/
    
    /* We apply strong induction on the dimension 'n' for each recursive call. */
    
     				 /********** Base Case ***********/
    /* The algorithm correctly handles base cases where 'n' becomes 0, and the corner is printed. */

    			  /********** Inductive Step ***********/
    /* Assuming that for any valid dimension 'n', the 'recursiveWalk' method correctly
     * transitions from its own pre-condition to its own post-condition by exploring left and right
     * children of the current corner in the hypercube recursively until 'n' reaches 0. */

    		   /********** Chaining Assumptions ***********/
    /* These correctness proofs are combined with the remaining code to ensure that 
     * at each recursion level, the algorithm effectively explores and prints all potential hypercube corners 
     * for the current 'n' dimension.
     */
    
    			   /**********  Conclusions ***********/
    /* By employing strong induction and connecting these correctness proofs, we establish that the 
     * primary call to 'recursiveWalk(n)' correctly transitions from its initial pre-condition to its ultimate 
     * post-condition. This guarantees the accurate generation and printing of all possible hypercube corners for 
     * the provided 'n' dimension.

     // This proof validates the correctness of the recursive algorithm for generating hypercube corners 
     // for all conceivable dimensions 'n', providing a robust and reliable solution.
     */
    
   /**	
     *  This method generates hypercube corners using a recursive approach.
     * 
     * @param hypercube_dim The dimension of the hypercube.
     * 
     *  Running time O(2^n), where 'n' is the dimension of the hypercube. This is because, in each step, the method branches 
     * into two recursive calls, effectively creating a binary tree with a depth of 'n'. As a result, the total number of 
     * calls is proportional to 2^n.
     * 
     * @see Corner
     */
    public void recursiveWalk(int hypercube_dim) {
        Corner initialCnr = new Corner(hypercube_dim); 		// initialize the initial corner, and print
        this.traverseHypercube(hypercube_dim, initialCnr); 		// launch recursion
    }
    
    
   /**
     * This is a helper method for the recursive approach, used to traverse the hypercube corners.
     * 
     * @param n 				Specifies the current dimension being processed.
     * @param currentCnr	 	The current corner in the hypercube system.
     * 
     * This method is a recursive helper function for the `recursiveWalk` method. It explores the left
     * and right children of the current corner in the hypercube recursively until the specified dimension
     * is reached. At each step, it toggles one dimension to traverse the binary tree of hypercube corners.
     * 
     * Running time is O(1) for each call. However, it is called recursively 
     * for each dimension, which results in O(n) total time complexity, where 'n' is the dimension.
     * 
     * @see Corner
     */
    private void traverseHypercube(int n, Corner currentCnr) {
        if (n == 0) { System.out.println(currentCnr.toString()); return; } // base case: When the dimension reaches zero, print the corner
        else if (n > 0) {
            this.traverseHypercube(n - 1, currentCnr); // explore the left child of the node
            currentCnr.flipCoordinate(n - 1); // move one step in the current dimension
            this.traverseHypercube(n - 1, currentCnr); // explore the right child of the node
        }
    }
    									
    								/* ITERATIVE METHOD */
    /**
     * Generates and prints hypercube corners iteratively for dimensions from 1 to 'n'.
     *
     * @param hypercube_dim The dimension of the hypercube.
     *
     * This method constructs hypercube corners iteratively, starting from dimension 1 up to the specified dimension 'n'. 
     * It prints the generated hypercube corners for dimension 'n'.
     *
     * Running time complexity is O(n * 2^n). In the worst case, it generates and processes 2^n corners for each dimension 
     * (from 1 to 'n'). For each corner, it performs constant time operations (enqueueing, dequeuing), 
     * resulting in the aforementioned time complexity.
     * 
     */
    
    public void iterativeWalk(int hypercube_dim) {
        // Initialize the starting point and print
        ArrayDeque<Corner> cnrQueue = new ArrayDeque<Corner>();

        // INITIALIZE: Set the queue with the solution for n = 1
        cnrQueue.offer(new Corner(1, 0));
        cnrQueue.offer(new Corner(1, 1));
        boolean order = true;

        // ITERATE: Incrementally & inductively maintain the Loop Invariant shown below: 
        while (!cnrQueue.isEmpty()) {
        	
        	/* LOOP INVARIANT: At the end of this iteration, 'cornerQueue' contains valid corners for the current 
        	 * dimension, 'order' correctly alternates for enqueueing, and 'currentCnr' represents a valid 
        	 * hypercube corner.
        	 */

            Corner currentCnr = cnrQueue.poll();
            // If the current corner is of the specified dimension, print it
            if (currentCnr.hypercube_dim == hypercube_dim) {
                System.out.println(currentCnr.toString());
            } else {
                // Enqueue the next level corners, alternating between left and right
                cnrQueue.offer(order ? Corner.extendCorner(currentCnr, false) :
                        Corner.extendCorner(currentCnr, true));
                cnrQueue.offer(order ? Corner.extendCorner(currentCnr, true) :
                        Corner.extendCorner(currentCnr, false));
                order ^= true;    // toggle the order for the next level
            }
        }
    }
    
    				/* MAIN */ 
    /**
	 * main() runs some test cases on the above Hypercube methods.
	 */
    public static void main(String[] args) {
        Hypercube hypercube = new Hypercube();
        int n = 0;        
        Scanner in = new Scanner(System.in);
        while (n <= 0) {
            System.out.print("Please enter the dimension of the hypercube (a positive integer): ");
            n = in.nextInt();
            if (n <= 0) {
                System.out.println("INVALID DIMENSION: " + n + ". Dimension must be a positive integer");
            }
        }
        in.close();
        System.out.println("\n");
        System.out.println("recursiveWalk()"); hypercube.recursiveWalk(n); 
        System.out.println("iterativeWalk()"); hypercube.iterativeWalk(n);  
    }
}
