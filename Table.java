public class Table {
	public final int size;
	public int numOfVacantSeats;

	/*
		Time  : O(1)
		Memory: O(1)
	*/
	public Table(int size) {
		this.size = size;
		this.numOfVacantSeats = size;
		System.out.println(this + " is created (size: " + size + ").");
	}
}