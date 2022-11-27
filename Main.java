import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

/* To test the program */
public class Main {
	public static void main(String[] args) {
		int max = 6;
		int minTable = 2;
		int rangeTable = max - minTable + 1;
		int minGroup = 1;
		int rangeGroup = max - minGroup + 1;

		System.out.println("*** Create Table ***");
		List<Table> tableList = new ArrayList<Table>();
		for (int i = 0; i < 10; i++) {
			tableList.add(new Table((int)(Math.random() * rangeTable) + minTable));
		}
		System.out.println();

		System.out.println("*** Create CustomerGroup ***");
		List<CustomerGroup> customerGroupList = new ArrayList<CustomerGroup>();
		for (int i = 0; i < 10; i++) {
			CustomerGroup group = new CustomerGroup((int)(Math.random() * rangeGroup) + minGroup);
			customerGroupList.add(group);
		}
		System.out.println();

		System.out.println("*** Create SeatingManager ***");
		SeatingManager manager = new SeatingManager(tableList);
		System.out.println();

		System.out.println("*** CustomerGroup 'Arrives' ***");
		int k = 1;
		for (CustomerGroup group : customerGroupList) {
			if (k >= 11) {
				break;
			}
			manager.arrives(group);
			k++;
		}
		System.out.println();

		System.out.println("*** CustomerGroup 'Leaves' ***");
		k = 1;
		for (CustomerGroup group : customerGroupList) {
			if (k >= 5) {
				break;
			}
			manager.leaves(group);
			k++;
		}
		System.out.println();

		System.out.println("*** CustomerGroup 'Locate' ***");
		k = 1;
		for (CustomerGroup group : customerGroupList) {
			if (k >= 10) {
				break;
			}
			System.out.println(group + " is sitting at " + manager.locate(group));
			k++;
		}
		System.out.println();

		System.out.println("*** Create new CosomerGroup & 'Arrives' ***");
		for (int i = 0; i < 3; i++) {
			CustomerGroup group = new CustomerGroup((int)(Math.random() * rangeGroup) + minGroup);
			customerGroupList.add(group);
			manager.arrives(group);
		}
		System.out.println();

		System.out.println("*** Current wait list ***");
		for (CustomerGroup waitGroup : manager.waitGroupList) {
			System.out.println(waitGroup + " (group size: " + waitGroup.size + ").");
		}
		System.out.println();
	}
}
