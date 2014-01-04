import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.locks.*;

public class Runway {

	public Aircraft plane;
	public int n, m;
	public boolean direction;

	private static Lock L;
	private static Condition C;

	public Runway() {
		plane = null;
		L = new ReentrantLock();
		C = L.newCondition();
	}

	public Runway(int a, int b, int c) {		
		n = b;
		m = c;
		L = new ReentrantLock();
		C = L.newCondition();
	}

	public void push_Plane(Aircraft new_Plane) {

		L.lock();
		try 
		{	
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			if(new_Plane.traffic == 'D') {
				if(new_Plane.direction) {
					System.out.println("Wind is coming in at " + new_Plane.wind + " degrees.\n" + "Leaving from Runway " + n + " " + m + "\n" + new_Plane.airline + " Flight No. " + new_Plane.flight_No + " at " + dateFormat.format(date) + " from " + new_Plane.from + " to " + new_Plane.to + " at Gate: " + new_Plane.gate + "\n");
				} else {
					System.out.println("Wind is coming in at " + new_Plane.wind + " degrees.\n" + "Leaving from Runway " + m + " " + n + "\n" + new_Plane.airline + " Flight No. " + new_Plane.flight_No + " at " + dateFormat.format(date) + " from " + new_Plane.from + " to " + new_Plane.to + " at Gate: " + new_Plane.gate + "\n");
				}
			} else {
				if(new_Plane.direction) {
					System.out.println("Wind is coming in at " + new_Plane.wind + " degrees.\n" + "Arriving on Runway " + n + " " + m + "\n" + new_Plane.airline + " Flight No. " + new_Plane.flight_No + " at " + dateFormat.format(date) + " from " + new_Plane.from + " to " + new_Plane.to + " at Gate: " + new_Plane.gate + "\n");
				} else {
					System.out.println("Wind is coming in at " + new_Plane.wind + " degrees.\n" + "Arriving on Runway " + m + " " + n + "\n" + new_Plane.airline + " Flight No. " + new_Plane.flight_No + " at " + dateFormat.format(date) + " from " + new_Plane.from + " to " + new_Plane.to + " at Gate: " + new_Plane.gate + "\n");
				}
			}
			Thread.sleep(5000);
			C.signalAll();
		} catch(InterruptedException e) {
			System.out.print("I have been interrupted!!!");
		} finally
		{
			L.unlock();
		}
	}
}