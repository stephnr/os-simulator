import java.util.StringTokenizer;

public class Aircraft extends Thread{

	public char    traffic;
	public boolean direction;
	public int    flight_No, gate, wind;
	public String airline,   from, to;
	public Runway runway;

	public Aircraft() {
		traffic = ' ';
		flight_No = gate = -1;
		airline = from = to = null;
		runway = null;
	}

	public Aircraft(String line) {
		String[] info = line.split(" ");

		airline = info[0];
		flight_No = Integer.parseInt(info[1]);
		traffic = info[2].charAt(0);
		gate = Integer.parseInt(info[3]);
		from = info[4];
		to = info[5];

		runway = null;
	}

	public void run() 
	{
		try
		{
			runway.push_Plane(this);
			sleep(500);
		} catch (InterruptedException e) {
			System.out.print("THIS IS SO INTERRUPTING!");
		}
	}

	public String toString() 
	{
		return new String(airline + " Flight_No: " + flight_No + traffic + " from Gate: " + gate + " at " + from + " going to " + to);
	}
}