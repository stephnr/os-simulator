import java.util.ArrayList;

public class ProcessTable {
	int pid_Counter;
	public ArrayList<Process> processes;

	public ProcessTable() {
		pid_Counter = 0;
		processes = new ArrayList<Process>();
	}

	//New Processes enter at the end
	//Process ID is assigned automatically in creation
	public boolean add(String process) {
		/*
		*	Reference pid_Counter against process table. 
		*	if null => create the process + insert
		* 	else => 
		*/
	}

	public boolean add(String process) {
		if(available.size() > 0) {
			Process temp = new Process(available.remove(0),process);
			processes.add(temp);
			return true;
		} else {
			System.out.println("Process Table is Full...");			
			return false;
		}
	}

	public Process get(int index) {
		return processes.get(index);
	}

	public boolean add(Process process) {
		if(available.size() > 0) {
			process.setID(available.remove(0));
			processes.add(process);
		} else {
			System.out.println("Process Table is Full...");			
			return false;
		}
		return true;
	}

	public Process deQueue() {
		Process temp = null;

		if(processes.size() > 0) {
			int i = 0;
			boolean found = false;
			while(!found && i < processes.size()) {
				if(processes.get(i).state == 'r') {
					found = true;
					temp = processes.remove(i);
				} else {
					i++;
				}
			}

			if(temp != null) available.add(temp.id);
			return temp;
		} else {
			return null;
		}
	}

	public Process remove(int id) {
		Process x = processes.remove(id);
		available.add(x.id);
		return x;
	}

	public int size() {
		return processes.size();
	}

	public String toString() {
		String temp = "";
		for(Process x : processes) {
			temp += x.toString() + "\n";
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