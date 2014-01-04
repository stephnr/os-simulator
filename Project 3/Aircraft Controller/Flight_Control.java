import java.util.ArrayList;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Random;


/*
	The threads here are the planes. The flight controller determines the time, selected runway, and direction. 

	Once these values have been decided upon, the planes then "run" (start the engines), and wait on the runway 
	until it is empty. Once empty, they fly off. Each plane takes up roughly 5 mins on the runway. 

	Before takeoff/landing, the aircraft announces its flight plan from the runway.
*/


class Flight_Control {

	private static Runway[] runways;
	private static ArrayList<Aircraft> landingQueue, takeoffQueue;

	public static void main(String[] args)
	{
		try {
			FileReader reader = new FileReader("FlightSchedule.txt");
			Scanner in = new Scanner(reader);

			//Initialize the queues
			runways = new Runway[2];
			landingQueue = new ArrayList<Aircraft>();
			takeoffQueue = new ArrayList<Aircraft>();

			while(in.hasNextLine()) {
					
					//Create the aircraft and add it to the queue				
					Aircraft temp = new Aircraft(in.nextLine());

					//Determine what queue it belongs in and add it to that queue
					if(temp.traffic == 'D') takeoffQueue.add(temp);
					else landingQueue.add(temp);

			}

			in.close();

			//Create the two runways with lane numbers
			runways[0] = new Runway(1,18,36);
			runways[1] = new Runway(2,9,27);

			Random rn = new Random();

			//Begin flight control as long as there are more planes to fly
			while(landingQueue.size() > 0 || takeoffQueue.size() > 0) {
				
				//Get a random number between 1 - 360
				int selectedRunway;
				int wind = rn.nextInt(360)+1;

				//Randomplane determines if flight control is taking from the takeoff or landing queue.
				Boolean direction = true;
				Boolean randomPlane = rn.nextBoolean();

				//Determine the runway to use based on the wind
				if(wind > 45) {
					if(wind > 135) {
						if(wind > 225) {
							if(wind > 315) {
								direction = false;
								selectedRunway = 0;
							}
							else {
								direction = false;
								selectedRunway = 1;
							}
						}
						else {
							direction = true;
							selectedRunway = 0;
						}
					}
					else {
						direction = true;
						selectedRunway = 1;
					}
				} else {
					direction = false;
					selectedRunway = 0;
				}

				//Assign a plane at random (takeoff or landing)
				//Once assigned, start the thread. Each plane takes 5secs (equivalent to 15mins).
				Aircraft temp = null;
				if(randomPlane && takeoffQueue.size() > 0) {
					temp = takeoffQueue.remove(0);

					temp.runway = runways[selectedRunway];
					temp.direction = direction;

					temp.wind = wind++;
					temp.start();
				} else {
					if(landingQueue.size() > 0) {
						temp = landingQueue.remove(0);

						temp.runway = runways[selectedRunway];
						temp.direction = direction;

						temp.wind = wind++;
						temp.start();
					}
				}
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}