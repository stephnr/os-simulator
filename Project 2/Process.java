/**
* Values of props table
* ----------------------------------------
* | Priority | Arrival Time | Burst Time |
* ----------------------------------------
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
		props = new int[3];
		nextPCB = null;
	}

	public Process(int value, String process) {
		id = value;
		props = new int[3];
		nextPCB = null;	
		ArrayList<String> coreValues = new ArrayList<String>();
		ArrayList<String> registerValues = new ArrayList<String>();

		try {
			String[] x = process.split(" ");
			name = x[0];
			state = 'n';

			for(int i = 1;  i <= 3;  i++) {props[i-1] = Integer.parseInt(x[i]);}
			for(int i = 4;  i <= 11; i++) {coreValues.add(x[i]);}
			for(int i = 12; i < x.length; i++) {registerValues.add(x[i]);}

			cpu = new RegisterSet(coreValues,registerValues);
		} catch(Exception e) {
			System.out.println("Error creating the process...");
			e.printStackTrace();
		}
	}

	public Process(String process) {
		id = -1;
		props = new int[3];
		nextPCB = null;	
		ArrayList<String> coreValues = new ArrayList<String>();
		ArrayList<String> registerValues = new ArrayList<String>();

		try {
			String[] x = process.split(" ");
			name = x[0];
			state = 'n';

			for(int i = 1;  i <= 3;  i++) {props[i-1] = Integer.parseInt(x[i]);}
			for(int i = 4;  i <= 11; i++) {coreValues.add(x[i]);}
			for(int i = 12; i < x.length; i++) {registerValues.add(x[i]);}

			cpu = new RegisterSet(coreValues,registerValues);
		} catch(Exception e) {
			System.out.println("Error creating the process...");
			e.printStackTrace();
		}
	}

	public boolean randomizeCPU() {
		return cpu.randomize();
	}

	public int getBurstTime() {
		return props[2];
	}

	public void setID(int value) {
		id = value;
	}

	public int getArrivalTime() {
		return props[1];
	}

	public int compareTo(Process other) {
		int a = this.props[1];
		int b = other.props[1];

		int c = this.props[0];
		int d = other.props[0];

		return a - b == 0 ? c - d : a - b;
	}

	public String toString() {
		String temp = String.format("ID: %d Name: %-10s State: %-3c Priority: %-2d Arrival Time: %-4d CPU Burst Time: %-5d", 
									 id,name,state,props[0],props[1],props[2]);
		temp += cpu.toString();
		return temp;
	}
}