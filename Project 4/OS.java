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
	public ArrayList<Partition> free_Partitions_List;

	public OS() {
		clock = 0;
		CPU = null;
		isCPUAvailable = true;
		process_Table = new ProcessTable();
		storage = new ArrayList<Process>();
		terminated_Q = new ArrayList<Process>();
		free_Partitions_List = new ArrayList<Partition>();
		
		//init free_partitions list with 1 partition of size N
		//Size of memory is in bytes.
		free_Partitions_List.add(new Partition(0,1000000));
	}

	private void adjustPartitionPointers() {
		for(int i = 0; i < free_Partitions_List.size(); i++) {
			if(i == free_Partitions_List.size()-1) return;
			free_Partitions_List.get(i).next = free_Partitions_List.get(i+1);
		}
	}

	private void create_Free_Partition(int start_loc, int size) {
		Partition new_fp = new Partition(start_loc,size);

		//Binary search input on start loc
		int pos = binarySearch(free_Partitions_List,new_fp);
		if(pos < 0) {
			free_Partitions_List.add(-pos-1, new_fp);
		} else {
			free_Partitions_List.add(pos, new_fp);
		}

		if(free_Partitions_List.size() > 1) fpListMerge();
		adjustPartitionPointers();
	}

	private void fpListMerge() {
		for(int i = 0; i < free_Partitions_List.size(); i++) {
			//look ahead to next partition for if it exists
			if(free_Partitions_List.size() >= 2) {
				if(i+1 == free_Partitions_List.size()) return;
				if(free_Partitions_List.get(i+1) == null) return;
				//Check if they are next to each other
				Partition left = free_Partitions_List.get(i);
				Partition right = free_Partitions_List.get(i+1);

				if((left.start_loc + left.size) == right.start_loc) {
					left.size += right.size;
					free_Partitions_List.remove(right);
					free_Partitions_List.set(i, left);
					// System.out.println("MERGE MADE");
					fpListMerge();
				}

				adjustPartitionPointers();
			}
		}
	}

	public Process deallocate(Process process) {
		create_Free_Partition(process.getStartMemLoc(), process.getProcSize());
		process.setStartMemLoc(-1);

		allocateAvailableResources();
		adjustPartitionPointers();
		return process;
	}

	public void allocateAvailableResources() {
		for(int i = 0; i < process_Table.size(); i++) {
			if(process_Table.get(i).state == 'w') allocate(process_Table.get(i));	
		} 
	}

	private boolean allocate(Process process) {
		int fp_pos = -1;

		// System.out.println("Proc Mem + Size: " + process.getStartMemLoc() + " " + process.getProcSize());
		for(int i = 0; i < free_Partitions_List.size(); i++) {
			if(free_Partitions_List.get(i).size >= process.getProcSize()) {
				fp_pos = i;
			}
		}

		if(fp_pos < 0) {
			process.state = 'w';
			// process_Table.add(process);
			return false;
		}
		else {
			process.state = 'r';
			process.setStartMemLoc(free_Partitions_List.get(fp_pos).start_loc);
			process_Table.add(process);

			int new_start = free_Partitions_List.get(fp_pos).start_loc + process.getProcSize();
			int new_size  = free_Partitions_List.get(fp_pos).size - process.getProcSize();

			free_Partitions_List.remove(fp_pos);
			
			if(new_size != 0) create_Free_Partition(new_start,new_size);
			adjustPartitionPointers();

			return true;
		}
	}

	public boolean add(Process process) {
		if(!allocate(process)) {
			// System.out.println("No available memory to allocate...Process pushed to WaitQ");
			return false;
		}
		return true;
	}

	public String compute() {
		clock++;
		isCPUAvailable = false;

		CPU.randomizeCPU();

		return new String("\t\tCONTENTS OF CPU: " + "\n" + CPU + "\n");
	}

	public String printReadyQ() {
		String temp = "CONTENTS OF THE READYQ:\n";
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

	public String printWaitingQ() {
		String temp = "CONTENTS OF THE WAITINGQ:\n";
		for(int i = 0; i < process_Table.size(); i++) {
			if(process_Table.get(i).state == 'w') temp += process_Table.get(i) + "\n\n";
		}
		return temp;
	}

	public String printTerminatedQ() {
		String temp = "CONTENTS OF THE TERMINATEDQ (size: " + terminated_Q.size() + " ):\n";
		for(Process x : terminated_Q) temp += x + "\n\n";
		return temp;
	}

	public String printPartitionsList() {
		String temp = "CONTENTS OF FREE PARTITIONS LIST Size(" + free_Partitions_List.size() + "):\n";
		for(Partition x : free_Partitions_List) temp += x + "\n";
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