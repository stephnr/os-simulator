/**
* Values of props table
* ----------------------------------------
* | Priority | Arrival Time | Burst Time |
* ----------------------------------------
*/
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;

public class Process {

	public int id;
	public String name;
	public int[] props;
	public char state;
	public Process nextProcess;

	RegisterSet cpu;

	public Process() {
		name = "";
		state = "".charAt(0);
		id = 0;
		cpu = new RegisterSet();
		props = new int[3];
		nextProcess = null;
	}

	public Process(int new_ID, String process) {
		id = new_ID;
		props = new int[3];
		nextProcess = null;	
		List<String> coreValues = new ArrayList<String>();
		List<String> registerValues = new ArrayList<String>();

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

	public String toString() {
		String temp = String.format("ID: %d Name: %-10s State: %-3c Priority: %-2d Arrival Time: %-4d CPU Burst Time: %-5d", 
									 id,name,state,props[0],props[1],props[2]);
		temp += cpu.toString();
		return temp;
	}
}