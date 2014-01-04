import java.util.ArrayList;

public class ProcessTable {
	public int process_Count;
	public ArrayList<Process> processes;
	public ArrayList<Integer> available;
	public int pos;

	public ProcessTable() {
		processes = new ArrayList<Process>();
		available = new ArrayList<Integer>();
	}

	/**
	* Checks the list of available positions. (Filled with values from remove operation).
	*	available = Queue of available PID's from processes that have expired.
	*
	* If there is nothing in the available list, position to insert process will be 
	* the next available position in the processes table. 
	*
	* If the resulting value is > 99, then ERROR, Process Table Overflow.
	*/
	public boolean add(Process process) {
		if(available.size() > 0) {
			pos = available.remove(0);
		} else {
			pos = processes.size();
		}

		// if(pos > 99) {
		// 	System.out.println("Unable to add process, Process Table is full!");
		// 	return false;
		// }

		processes.add(process);
		return true;
	}

	public boolean add(String process) {
		if(available.size() > 0) {
			Process temp = new Process(available.remove(0),process);
			processes.add(temp);
			return true;
		} else if(processes.size() < 99){
			Process temp = new Process(processes.size(),process);
			processes.add(temp);
			return true;
		} else {
			System.out.println("ProcessTable is Full...");
			return false;
		}
	}

	public Process remove(int id) {
		Process x = processes.remove(id);
		available.add(id);
		return x;
	}

	public String toString() {
		String temp = "";
		for(Process x : processes) {
			temp += x.toString() + "\n";
		}
		return temp;
	}
}