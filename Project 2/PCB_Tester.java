import java.io.*;
import javax.swing.*;

public class PCB_Tester {
	public static void main(String[] args) {
		OS temp = new OS();

		StringBuffer s = new StringBuffer("");
		String file, currentLine = "";
		try(BufferedReader reader = new BufferedReader(new FileReader(file = "processes1.txt"))) {
			while((currentLine = reader.readLine()) != null) {
				temp.add(currentLine);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println(temp);
		
		try {
			FileWriter write = new FileWriter("results.txt");
			PrintWriter out = new PrintWriter(write);
			out.println(temp.toString());
			out.close();
		} catch(IOException e) {
			System.out.println("Error printing contents of the Process Table...");
		}
	}
}