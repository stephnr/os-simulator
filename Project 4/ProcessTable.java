import java.util.ArrayList;

public class ProcessTable {
	int pid_Counter;
	public ArrayList<Process> processes;

	public ProcessTable() {
		pid_Counter = 0;
		processes = new ArrayList<Process>();
	}

	public boolean add(Process newProc) {
		if(processes.size() <= 100) {
			newProc.setID(pid_Counter);
			int pos = binarySearch(processes,newProc);
			if(pos < 0) processes.add(-pos-1, newProc);
			else processes.add(pos, newProc);
			// processes.add(newProc);
			pid_Counter = (pid_Counter+1)%100;
			// System.out.println("PID_COUNTER: " + pid_Counter);
			// System.out.println(processes.size()-1);
		} else {
			// System.out.println("Process Table is Full...Incrementing Arrival Time.");
			return false;
		}
		return true;
	}

	public Process deQueueReadyProc() {
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
			return temp;
		} else {
			return null;
		}
	}


	public Process get(int index) {
		return processes.get(index);
	}

	public Process remove(int id) {
		Process x = processes.remove(id);
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