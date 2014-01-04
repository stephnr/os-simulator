public class Partition implements Comparable<Partition> {
	public int start_loc;
	public int size;
	public Partition next;

	public Partition() {
		start_loc = 0;
		size = 0; 
		next = null;
	}

	public Partition(int start, int siz) {
		start_loc = start;
		size = siz;
		next = null;
	}

	public int compareTo(Partition other) {
		return this.start_loc - other.start_loc;
	}

	public String toString() {
		String temp = String.format("\t\tPartition: Starts@: %-4d, with size: %-4d",start_loc,size);
		return temp;
	}
}