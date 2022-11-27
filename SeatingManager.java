import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class SeatingManager {

	/* Data Structure: Binary Search Tree(BST) with duplicate values.
		Make BST with tables which have vacant seats. 
		If vacant seats become 0, delete a node from this BST.
		If vacant seats change (ex. 4 -> 2), delete the node(4) and insert new node(2) in the BST.
	*/
	public BSTWithDuplication.Node vacantTables = null;

	/* Data Structure: Queue(LinkedList)
		To deal with the CustomerGroup wait list.
		- Queue: To deal with first comes first will be seated.
		- LinkedList: LinkedList is better than array when I think about shifting elements after deletion.
	*/
	public LinkedList<CustomerGroup> waitGroupList = new LinkedList<CustomerGroup>();

	/*
		Time  : O(n) [n = number of tables]
		Memory: O(n) [n = number of tables]
	*/
	public SeatingManager(List<Table> tables) {
		for (Table table : tables) {
			this.vacantTables = BSTWithDuplication.insert(this.vacantTables, table);
		}
		System.out.println("SeatingManager is created.");
	}

	/*
		Time  : O(2h) [h = height of vacantTables(bst)]
		Memory: O(1)
	*/
	public void assignSeats(CustomerGroup group, Table table) {
		group.seatedTable = table;
		BSTWithDuplication.deleteNode(this.vacantTables, table);
		table.numOfVacantSeats -= group.size;
		if (table.numOfVacantSeats != 0) {
			BSTWithDuplication.insert(this.vacantTables, table);
		}
		System.out.println(group + " is seated at " + table
							+ " (group size: " + group.size
							+ ", table size: " + table.size 
							+ " -> vacant seats: " + table.numOfVacantSeats + ").");
	}

	/*
		Time  : O(g * 2h) [g = number of wait group, h = height of vacantTables(bst)]
		Memory: O(1)
	*/
	public void assignSeatsTriggeredByLeaves(Table availableTable) {
		if (this.waitGroupList == null)
			return;
		int beginningWaitListSize = this.waitGroupList.size();
		for (int i = 0; i < beginningWaitListSize; i++) {
			CustomerGroup waitGroup = this.waitGroupList.get(i);
			if (waitGroup.size <= availableTable.numOfVacantSeats) {
				assignSeats(waitGroup, availableTable);
				waitGroupList.remove((int)i);
				System.out.println(waitGroup + " is deleted from waitlist.");
				i--;
				beginningWaitListSize--;
			}
		}
	}

	/*
		Time  : O(1)
		Memory: O(1)
	*/
	public void addToWaitGroupList(CustomerGroup group) {
		this.waitGroupList.add(group);
		System.out.println(group + " was put in the wait list."
							+ " (group size: " + group.size + ").");
	}

	/*
		Time  : O(1)
		Memory: O(1)
	*/
	public Table locate(CustomerGroup group) {
		Table seatedTable = group.seatedTable;
		return seatedTable;
	}

	/*
		Time  : O(h) [h = height of vacantTables(bst)]
		Memory: O(1)
	*/
	public void arrives(CustomerGroup group) {
		BSTWithDuplication.Node availableTable = BSTWithDuplication.search(this.vacantTables, group.size);
		if (availableTable == null) {
			addToWaitGroupList(group);
		} else {
			assignSeats(group, availableTable.table);
		}
	}

	/*
		Time  : O(2h + (g * 2h)) [g = number of wait group, h = height of vacantTables(bst)]
		Memory: O(1)
	*/
	public void leaves(CustomerGroup group) {
		Table availableTable = group.seatedTable;
		group.seatedTable = null;
		if (availableTable == null)
			return;
		BSTWithDuplication.deleteNode(this.vacantTables, availableTable);
		availableTable.numOfVacantSeats += group.size;
		BSTWithDuplication.insert(this.vacantTables, availableTable);
		System.out.println(group + " left from " + availableTable
							+ " (group: size: " + group.size
							+ ", table size: " + availableTable.size
							+ " -> vacant seats: " + availableTable.numOfVacantSeats + ").");
		assignSeatsTriggeredByLeaves(availableTable);
	}
}