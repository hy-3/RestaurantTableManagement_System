public class CustomerGroup {
	public final int size;
	public Table seatedTable;

	/*
		Time  : O(1)
		Memory: O(1)
	*/
	public CustomerGroup(int size) {
		this.size = size;
		System.out.println(this + " is created (size: " + size + ").");
	}
}