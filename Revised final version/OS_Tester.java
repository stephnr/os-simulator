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
		List<Process> storage = new ArrayList<Process>();

		//Read in all the processes and stores them in a storage table and ordered by arrival time (Smallest -> Largest order)
		try {
			FileReader reader = new FileReader("processes4.txt");
			Scanner in = new Scanner(reader);

			while(in.hasNextLine()) {
					Process newProc = new Process(in.nextLine());
					storage.add(newProc);
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
			while(storage.size() > 0 || windows.process_Table.size() > 0 || !windows.isCPUAvailable) {
				
				// System.out.println(windows.printWaitingQ());
				// if(global_Time == 2000) break;

				//Is it this processes time to arrive?
				for(int i = 0; i < storage.size(); i++) {
					
					if((storage.get(i).getArrivalTime() == global_Time) && (windows.process_Table.size() < 99)) {
						
						boolean added = windows.add(storage.get(i));
						
						if(!added) {
							//Grab the process and store it in the Memory Waiting Queue
							Process temp = storage.get(i);
							temp.incrArrival(global_Time);
							temp.state = 'w';
							storage.set(i, temp);
							// out.println("Process not added. Waiting for Memory...");
						}
						else {
							
							ready = true;
							storage.remove(i);
							if(windows.process_Table.size() > 1) {
								windows.process_Table.get(windows.process_Table.size() - 2).nextPCB = windows.process_Table.get(windows.process_Table.size() - 1);
							}

						}
					} else if(storage.get(i).getArrivalTime() <= global_Time) {
						
						Process temp = storage.get(i);
						//Set the process to arrive at the next cycle if its original arrival was passed
						temp.incrArrival(global_Time);
						storage.set(i, temp);

					}
				}

				//Check if the CPU is free
				if(windows.isCPUAvailable) {

					//Timestamp the start of a new process
					// out.println("GLOBAL TIME: " + global_Time);
					// out.println("CPU TIME: " + windows.clock + "\n");

					//If so, start new execution
					windows.clock = 0;
					//Grab next process from Proc Table
					if(windows.process_Table.size() > 0) {
						//DEQUEUE will grab the next ready process in the Process Table
						windows.CPU = windows.process_Table.deQueueReadyProc();
						if(windows.CPU == null) {
							windows.allocateAvailableResources();
							continue;
						}
						windows.CPU.state = 'e';
						windows.isCPUAvailable = false;
						out.println("STARTING WORK ON: \n\n" + windows.CPU);
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
					
					out.println("Global Time: " + global_Time);
					out.println("CPU Clock: " + windows.clock);

					out.println("BURST REACHED...now terminating...\n");
					
					//Change its state to terminated
					windows.CPU.state = 't';
					windows.CPU.setID(-1);
					out.println(windows.CPU + "\n");

					//Add it to the Terminated Queue
					Process endProc = windows.deallocate(windows.CPU);
					out.println(windows.printPartitionsList());
					windows.terminated_Q.add(endProc); 
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

				//If there was a change in the terminatedQ, print its contents
				// if(terminated) {
				// 	out.println("\nCONTENTS OF TERMINATEDQ :");
				// 	out.println(windows.terminated_Q);
				// 	terminated = false;
				// 	out.println();
				// }

				global_Time++;
			}
			out.println(windows.printTerminatedQ());
			out.println("\nStorage: " + storage.size());
			out.println("\nProcTable: " + windows.process_Table.size());
			out.close();

			} catch(IOException e) {
			e.printStackTrace();
		}
	}
}