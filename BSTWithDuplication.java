import java.util.LinkedList;

public class BSTWithDuplication
{
	public static Node closestSizeTable;

	public static class Node {
		LinkedList<Table> table = new LinkedList<>();
		int count;
		int height;
		Node left, right;
	};

	/*
		Time  : O(1)
		Memory: O(1)
	*/
	public static Node newNode(Table table)
	{
		Node temp = new Node();
		temp.table.add(table);
		temp.left = temp.right = null;
		temp.count = 1;
		return temp;
	}

	/* Methods to make BST balanced */
	public static void updateHeight(Node root) {
		root.height = 1 + Math.max(height(root.left), height(root.right));
	}
	public static int height(Node root) {
		return root == null ? -1 : root.height;
	}
	public static int getBalance(Node root) {
		return (root == null) ? 0 : height(root.right) - height(root.left);
	}
	public static Node rotateRight(Node y) {
		Node x = y.left;
		Node z = x.right;
		x.right = y;
		y.left = z;
		updateHeight(y);
		updateHeight(x);
		return x;
	}
	public static Node rotateLeft(Node y) {
		Node x = y.right;
		Node z = x.left;
		x.left = y;
		y.right = z;
		updateHeight(y);
		updateHeight(x);
		return x;
	}
	public static Node rebalance(Node z) {
		updateHeight(z);
		int balance = getBalance(z);
		if (balance > 1) {
			if (height(z.right.right) > height(z.right.left)) {
				z = rotateLeft(z);
			} else {
				z.right = rotateRight(z.right);
				z = rotateLeft(z);
			}
		} else if (balance < -1) {
			if (height(z.left.left) > height(z.left.right))
				z = rotateRight(z);
			else {
				z.left = rotateLeft(z.left);
				z = rotateRight(z);
			}
		}
		return z;
	}

	/*
		Time  : O(h) [h = height of bst]
		Memory: O(1)
	*/
	public static void findClosestSizeTable(Node root, int groupSize)
	{
		if (root == null) {
			return;
		}
		if (root.table.getFirst().numOfVacantSeats == groupSize) {
			closestSizeTable = root;
			return;
		}
		if (((root.table.getFirst().numOfVacantSeats - groupSize) >= 0 && closestSizeTable == null) || 
			((root.table.getFirst().numOfVacantSeats - groupSize) >= 0 && 
			  (closestSizeTable.table.getFirst().numOfVacantSeats - groupSize) >= (root.table.getFirst().numOfVacantSeats - groupSize))) {
				closestSizeTable = root;
		}
		if (root.table.getFirst().numOfVacantSeats < groupSize) {
			findClosestSizeTable(root.right, groupSize);
		} else {
			findClosestSizeTable(root.left, groupSize);
		}
	}

	/*
		Time  : O(h) [h = height of bst]
		Memory: O(1)
	*/
	public static Node search(Node root, int groupSize) {
		closestSizeTable = null;
		findClosestSizeTable(root, groupSize);
		return closestSizeTable;
	}

	/*
		Time  : O(h) [h = height of bst]
		Memory: O(1)
	*/
	public static Node insert(Node node, Table table)
	{
		if (node == null) {
			return newNode(table);
		}
		if (table.numOfVacantSeats == node.table.getFirst().numOfVacantSeats) {
			node.table.add(table);
			(node.count)++;
			return node;
		}
		if (table.numOfVacantSeats < node.table.getFirst().numOfVacantSeats) {
			node.left = insert(node.left, table);
		} else {
			node.right = insert(node.right, table);
		}
		return rebalance(node);
	}

	/*
		Time  : O(h) [h = height of bst]
		Memory: O(1)
	*/
	public static Node minValueNode(Node node)
	{
		Node current = node;

		while (current.left != null) {
			current = current.left;
		}
		return current;
	}

	/*
		Time  : O(h * d) [h = height of bst]
		Memory: O(1)
	*/
	public static Node deleteNode(Node root, Table table)
	{
		if (root == null) {
			return root;
		}
		if (table.numOfVacantSeats < root.table.getFirst().numOfVacantSeats) {
			root.left = deleteNode(root.left, table);
		} else if (table.numOfVacantSeats > root.table.getFirst().numOfVacantSeats) {
			root.right = deleteNode(root.right, table);
		} else {
			if (root.count > 1) { // case 0: declement count when it is duplicated
				root.table.pollFirst();
				(root.count)--;
				return root;
			}
			if (root.left == null) { // case 1: no child or one child(right)
				Node temp = root.right;
				root = null;
				return temp;
			} else if (root.right == null) { // case 2: one child(left)
				Node temp = root.left;
				root = null;
				return temp;
			}
			// case 3: two children
			Node temp = minValueNode(root.right);
			root.table.addAll(temp.table);
			root.count = temp.count;
			root.right = deleteNode(root.right, temp.table.getFirst());
		}
		if (root != null)
			root = rebalance(root);
		return root;
	}
}