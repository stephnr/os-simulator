import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

/*
	Date: 9/16/13
	Author: STEPHEN RODRIGUEZ

	Notes:
	ReadyQ = Pointer to first ready process in Process Table.
		OS.printReadyQ() will print using nextPCB pointers

	TerminatedQ = a table of terminated processes

	CPU = instance of process. Process exists in Process Table in a 'e' state
	*** NORMALLY AT POSITION 0 ***
*/

public class OS_Tester {
	public static void main(String[] args) {
		OS windows = new OS();
		ProcessTable table = windows.process_Table;
		ArrayList<Process> storage = windows.storage;

		//Read in all the processes and stores them in a storage table and ordered by arrival time (Smallest -> Largest order)
		try {
			FileReader reader = new FileReader("processes2.txt");
			Scanner in = new Scanner(reader);

			while(in.hasNextLine()) {
					windows.store(in.nextLine());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			//Print all results to another file
			FileWriter write = new FileWriter("results.txt");
			PrintWriter out = new PrintWriter(write);

			int global_Time = 0;

			//Boolean switches to determine if change occured
			boolean ready,terminated;
			ready = terminated = false;

			//Loop as long as there are more processes
			while(windows.storage.size() > 0 || windows.process_Table.size() > 0 || !windows.isCPUAvailable) {

				//Process are arriving on time and added if space is available 
				//If not, they remain in the storage (delayed)

				//Is there more processes incoming?
				if(storage.size() > 0) {
					//Is it this processes time to arrive?
					while((windows.storage.get(0).getArrivalTime()  <= global_Time) && (windows.process_Table.size() < 99)) {
						
						//Remove the process and add to Process Table
						Process arrival = windows.storage.remove(0);
						arrival.nextPCB = null;
						arrival.state = 'r';
						windows.add(arrival);

						//Ready queue has changed checker
						ready = true;
						// out.println("NEW PROCESS ADDED: " + arrival);

						//Readjust the ReadyQ pointers
						if(table.size() > 1) {
							table.get(table.size() - 2).nextPCB = table.get(table.size() - 1);
						} 

						//If this was the last value, break.
						if(storage.size() == 0) break;
					}
				}

				//Check if the CPU is free
				if(windows.isCPUAvailable) {

					//Timestamp the start of a new process
					out.println("GLOBAL TIME: " + global_Time);
					out.println("CPU TIME: " + windows.clock + "\n");

					//If so, start new execution
					windows.clock = 0;
					//Grab next process from Proc Table
					if(table.size() > 0) {
						//DEQUEUE will grab the next ready process in the Process Table
						windows.CPU = table.deQueue();
						windows.CPU.state = 'e';	
						windows.isCPUAvailable = false;
						out.println("STARTING WORK ON: \n" + windows.CPU);
						out.println();
					} 
				} else {
					/*
						If the CPU has a process already, continue processing.
						Increments the clock and randomizes the values and prints 
						out the contents of the CPU by returning a string
					*/

					//Compute the contents every cycle
					if(windows.CPU != null) {
						String temp = windows.compute();
						//out.println(temp);					
					}
				}

				//Check if the process has finished. If so, terminate and free the CPU
				if(windows.CPU != null && windows.clock >= windows.CPU.getBurstTime()) {
					
					// out.println("Global Time: " + global_Time);
					// out.println("CPU Clock: " + windows.clock);

					// out.println("BURST : " + windows.CPU + "\n");
					out.println("BURST REACHED...now terminating...\n");
					
					//Change its state to terminated
					windows.CPU.state = 't';
					out.println(windows.CPU + "\n");

					//Add it to the Terminated Queue
					windows.terminated_Q.add(windows.CPU);
					windows.isCPUAvailable = true;
					windows.CPU = null;
					
					//Terminated queue has changed checker
					terminated = true;
				}
				
				//If there was a change in the readyQ, print its contents
				// if(ready) {
				// 	out.println(windows.printReadyQ());
				// 	ready = false;
				// }

				// //If there was a change in the terminatedQ, print its contents
				// if(terminated) {
				// 	out.println("\nCONTENTS OF TERMINATEDQ :");
				// 	out.println(windows.terminated_Q);
				// 	terminated = false;
				// 	out.println();
				// }

				global_Time++;
			}

			//windows.printReadyQ();

			// out.println(windows.process_Table);

			// for(Process x : windows.storage) {
			// 	out.println(x);
			// }

			out.close();

			} catch(IOException e) {
			e.printStackTrace();
		}
	}
}