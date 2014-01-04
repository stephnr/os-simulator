import java.util.*;

//TerminatedQ is a pointer. Terminated process becomes a zombie (exists in Proc Table) 
//On next clock cycle, slot becomes available

public class OS {
	public int clock;
	
	public Process CPU;
	public boolean isCPUAvailable;

	public ProcessTable process_Table;
	public ArrayList<Process> storage;
	public ArrayList<Process> terminated_Q;

	public OS() {
		clock = 0;
		CPU = null;
		isCPUAvailable = true;
		process_Table = new ProcessTable();
		storage = new ArrayList<Process>();
		terminated_Q = new ArrayList<Process>();
	}

	public boolean add(Process process) {
		boolean added = process_Table.add(process);
		if(!added) {
			System.out.println("Process was not added...");
			return false;
		} else {
			return true;
		}
	}

	public boolean store(String process) {
		Process x = new Process(process);

		int pos = binarySearch(storage,x);
		if(pos < 0) {
			storage.add(-pos-1, x);
		} else {
			storage.add(pos, x);
		}
		return true;
	}

	public String compute() {
		clock++;
		isCPUAvailable = false;

		CPU.randomizeCPU();

		return new String("CONTENTS OF CPU: " + "\n" + CPU + "\n");
	}

	public String printReadyQ() {
		String temp = "CONTENTS OF THE READYQ:" + "\n";
		int i = 0;
		boolean found = false;
		while(!found && i < process_Table.size()) {
			if(process_Table.get(i).state == 'r') {
				found = true;
				Process ready = process_Table.get(i);
				temp += ready + "\n"; 
				while(ready.nextPCB != null) {
					ready = ready.nextPCB;
					temp += ready + "\n"; 
				}
			} else {
				i++;
			}
		}
		return temp;
	}

	//Binary Search for creating ordered list of processes in storage
	public static <T extends Comparable<T> >
	  int binarySearch(ArrayList<T> table, T key)
	  {
	      int low = 0, high = table.size() - 1;
	      while(high >= low)
	      {
	          int mid = (low + high)/2;
	          T midElement = table.get(mid);
	          int result = key.compareTo(midElement);
	          if(result == 0)
	             return mid;
	          if(result < 0)
	               high = mid - 1;
	          else low  = mid + 1;
	      }
	      return -low - 1;
	  }
}