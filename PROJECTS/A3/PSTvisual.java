/**********************************************************
 * EECS2101A: Fundamentals of Data Structures, Fall 2023
 * Assignment 3, Problem 3 Helper: PSTvisual.java
 * Student Name: Leroy-David Musa
 * Student EECS account: leroy7
 * Student ID number: 219198761
 **********************************************************/
package A3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import A3.PrioritySearchTree.Point;
import A3.PrioritySearchTree.TreeNode;

public class PSTvisual {

    /**
     * Builds the tree structure from an array of points.
     * @param internal_nodes The array of points representing the nodes of the tree.
     * @return The root node of the constructed complete binary tree.
     */
	public TreeNode buildTreeStructure(Point[] internal_nodes) {
		List<TreeNode> nodeList = new ArrayList<>();
		for (Point node : internal_nodes) {
			nodeList.add(new TreeNode(node));
		}
		for (int i = 0; i < internal_nodes.length; i++) {
			TreeNode internal = nodeList.get(i);
			int leftIndex = 2*i + 1;
			int rightIndex = 2*i + 2;
			if (leftIndex < internal_nodes.length) {
				internal.lC = nodeList.get(leftIndex);
			}
			if (rightIndex < internal_nodes.length) {
				internal.rC = nodeList.get(rightIndex);
			}
		}
		return nodeList.isEmpty() ? null : nodeList.get(0);
	}
	
	/**
     * Prints the complete binary tree structure with proper formatting.
     * @param root The root node of the binary tree to be printed.
     */
	public static void printNode(TreeNode root) {
		int maxLevel = maxLevel(root);
		printNodeInternal(Collections.singletonList(root), -2, maxLevel);
	}

	/**
	 * Internal method for recursively printing the visual representation of a binary tree.
	 * @param nodes    The list of tree nodes to be printed at the current level.
	 * @param level    The current level of the binary tree.
	 * @param maxLevel The maximum level of the binary tree.
	 */
	private static void printNodeInternal(List<TreeNode> nodes, int level, int maxLevel) {
		if (nodes.isEmpty() || isAllElementsNull(nodes))
			return;
		int floor = maxLevel - level;
		int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
		printWhitespaces(firstSpaces);
		List<TreeNode> newNodes = new ArrayList<>();
		for (TreeNode node : nodes) {
			if (node != null) {
				System.out.print(node.data != null ? node.data : "null"); // Print "n" for null nodes
				newNodes.add(node.lC);
				newNodes.add(node.rC);
			} else {
				newNodes.add(null);
				newNodes.add(null);
				System.out.print(""); // Print "n" for null nodes
			}
			printWhitespaces(betweenSpaces);
		}
		System.out.println("");

		for (int i = 1; i <= edgeLines; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				printWhitespaces(firstSpaces - i);
				if (nodes.get(j) == null) {
					printWhitespaces(edgeLines + edgeLines + i + 1);
					continue;
				}
				if (nodes.get(j).lC != null)
					System.out.print("/");
				else
					printWhitespaces(1);
				printWhitespaces(i + i - 1);
				if (nodes.get(j).rC != null)
					System.out.print("\\");
				else
					printWhitespaces(1);
				printWhitespaces(edgeLines + edgeLines - i);
			}
			System.out.println("");
		}
		printNodeInternal(newNodes, level + 1, maxLevel);
	}

    /**
     * Prints the specified number of whitespaces.
     * @param count The number of whitespaces to be printed.
     */
	private static void printWhitespaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

    /**
     * Calculates the maximum level of the given binary tree.
     * @param node The root node of the binary tree.
     * @return The maximum level of the binary tree.
     */
	private static int maxLevel(TreeNode node) {
		if (node == null)
			return 0;
		return Math.max(maxLevel(node.lC), maxLevel(node.rC)) + 1;
	}

	/**
     * Checks if all elements in the list are null.
     * @param list The list to be checked.
     * @return true if all elements are null, false otherwise.
     */
	private static boolean isAllElementsNull(List<TreeNode> list) {
		for (Object object : list) {
			if (object != null)
				return false;
		}
		return true;
	}
}