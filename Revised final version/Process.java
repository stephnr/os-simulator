/**
* Values of props table
* ---------------------------------------------------------------
* | Priority | Arrival Time | Burst Time | Size | Mem Start_Loc |
* ---------------------------------------------------------------
*/
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;

public class Process implements Comparable<Process>{

	public int id;
	public String name;
	public int[] props;
	public char state;
	public Process nextPCB;

	public RegisterSet cpu;

	public Process() {
		name = "";
		state = "".charAt(0);
		id = 0;
		cpu = new RegisterSet();
		props = new int[5];
		nextPCB = null;
	}

	public Process(int value, String process) {
		id = value;
		props = new int[5];
		nextPCB = null;	
		ArrayList<String> coreValues = new ArrayList<String>();
		ArrayList<String> registerValues = new ArrayList<String>();

		try {
			String[] x = process.split(" ");
			name = x[0];
			state = 'n';
	
			for(int i = 1;  i <= 4;  i++) {props[i-1] = Integer.parseInt(x[i]);}
			for(int i = 5;  i <= 13; i++) {coreValues.add(x[i]);}
			for(int i = 14; i < x.length; i++) {registerValues.add(x[i]);}

			cpu = new RegisterSet(coreValues,registerValues);

			// System.out.println(this);
		} catch(Exception e) {
			System.out.println("Error creating the process...");
			e.printStackTrace();
		}
	}

	public Process(String process) {
		id = -1;
		props = new int[5];
		nextPCB = null;	
		ArrayList<String> coreValues = new ArrayList<String>();
		ArrayList<String> registerValues = new ArrayList<String>();

		try {
			String[] x = process.split(" ");
			name = x[0];
			state = 'n';

			for(int i = 1;  i <= 4;  i++) {props[i-1] = Integer.parseInt(x[i]);}
			for(int i = 5;  i <= 13; i++) {coreValues.add(x[i]);}
			for(int i = 14; i < x.length; i++) {registerValues.add(x[i]);}

			cpu = new RegisterSet(coreValues,registerValues);

			// System.out.println(this);
		} catch(Exception e) {
			System.out.println("Error creating the process...");
			e.printStackTrace();
		}
	}

	public boolean randomizeCPU() {
		return cpu.randomize();
	}

	public int getArrivalTime() {
		return props[1];
	}

	public boolean incrArrival(int n) {
		props[1] = n + 1;
		return true;
	}

	public int getBurstTime() {
		return props[2];
	}

	public int getStartMemLoc() {
		return props[4];
	}

	public int getProcSize() {
		return props[3];
	}

	public void setID(int value) {
		id = value;
	}

	public void setStartMemLoc(int n) {
		props[4] = n;
	}

	public int compareTo(Process other) {
		int a = this.props[1];
		int b = other.props[1];

		int c = this.props[2];
		int d = other.props[2];

		return (a-b)+(c-d);
	}

	public String toString() {
		String temp = String.format("\t\tID: %d Name: %-10s State: %-3c Priority: %-3d Arrival Time: %-3d CPU Burst Time: %-4d Size: %-6d Mem_Start_Location: %-6d",id,name,state,props[0],props[1],props[2],props[3],props[4]);
		temp += "\n\t" + cpu.toString();
		return temp;
	}
}