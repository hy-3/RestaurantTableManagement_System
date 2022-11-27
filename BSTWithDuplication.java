public class BSTWithDuplication
{
	public static Node closestSizeTable;

	public static class Node {
		Table table;
		int count;
		Node left, right;
	};

	/*
		Time  : O(1)
		Memory: O(1)
	*/
	public static Node newNode(Table table)
	{
		Node temp = new Node();
		temp.table = table;
		temp.left = temp.right = null;
		temp.count = 1;
		return temp;
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
		if (root.table.numOfVacantSeats == groupSize) {
			closestSizeTable = root;
			return;
		}
		if (((root.table.numOfVacantSeats - groupSize) >= 0 && closestSizeTable == null) || 
			((root.table.numOfVacantSeats - groupSize) >= 0 && 
			  (closestSizeTable.table.numOfVacantSeats - groupSize) >= (root.table.numOfVacantSeats - groupSize))) {
				closestSizeTable = root;
		}
		if (root.table.numOfVacantSeats < groupSize) {
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
		if (table == node.table) {
			(node.count)++;
			return node;
		}
		if (table.numOfVacantSeats < node.table.numOfVacantSeats) {
			node.left = insert(node.left, table);
		} else {
			node.right = insert(node.right, table);
		}
		return node;
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
		Time  : O(h) [h = height of bst]
		Memory: O(1)
	*/
	public static Node deleteNode(Node root, Table table)
	{
		if (root == null) {
			return root;
		}
		if (table.numOfVacantSeats < root.table.numOfVacantSeats) {
			root.left = deleteNode(root.left, table);
		} else if (table.numOfVacantSeats > root.table.numOfVacantSeats) {
			root.right = deleteNode(root.right, table);
		} else {
			if (root.count > 1) {
				(root.count)--;
				return root;
			}
			if (root.left == null) {
				Node temp = root.right;
				root = null;
				return temp;
			} else if (root.right == null) {
				Node temp = root.left;
				root = null;
				return temp;
			}
			Node temp = minValueNode(root.right);
			root.table = temp.table;
			root.count = temp.count;
			root.right = deleteNode(root.right, temp.table);
		}
		return root;
	}
}