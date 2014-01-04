import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

public class PCB_Tester {
	public static void main(String[] args) {
		ProcessTable temp = new ProcessTable();

		try {

			//Read the file and add each newline as a process to the processTable
			FileReader reader = new FileReader("processes1.txt");
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) temp.add(in.nextLine());

		} catch(Exception e) {
			System.out.println("Error reading contents of the file...");
			e.printStackTrace();
		}

		//Show us whats in the process table.
		System.out.println(temp);
		
		try {
		
			//Print the results to the results.txt output file
			FileWriter write = new FileWriter("results.txt");
			PrintWriter out = new PrintWriter(write);
			out.println(temp.toString());
			out.close();

		} catch(IOException e) {
			System.out.println("Error printing contents of the Process Table...");
			e.printStackTrace();
		}
	}
}