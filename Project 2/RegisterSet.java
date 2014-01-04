/**
* Core Register Positions:
* -----------------------------------------------
* | XAR | XDI | XDO | PC | IR | EMIT | RR | PSW |
* -----------------------------------------------
*/
import java.util.*;

public class RegisterSet {
	private ArrayList<String> core;
	private ArrayList<String> registers;

	private String XAR ;
	private String XDI ;
	private String XDO ;
	private String PC  ;
	private String IR  ;
	private String EMIT;
	private String RR  ;
	private String PSW ;

	public RegisterSet() {
		core 	  = new ArrayList<String>();
		registers = new ArrayList<String>();
		initRegisters();
	}

	public RegisterSet(ArrayList<String> new_Core, ArrayList<String> new_Registers) {
		core 	  = new_Core;
		registers = new_Registers;
		initRegisters();
	}

	private void initRegisters() {
		XAR  = core.get(0);
		XDI  = core.get(1);
		XDO  = core.get(2);
		PC   = core.get(3);
		IR   = core.get(4);
		EMIT = core.get(5);
		RR   = core.get(6);
		PSW  = core.get(7);
	}

	public boolean randomize() {
		//Randomize the cpu core
		//if(CPU == null) return false;

		for(int i = 0; i < core.size(); i++) {
			Random r = new Random();
	        StringBuffer sb = new StringBuffer();
	        while(sb.length() < 6){
	            sb.append(Integer.toHexString(r.nextInt()));
	        }
	        core.set(i,sb.toString().substring(0,6));
		}

		//Randomize the registers
		for(int i = 0; i < registers.size(); i++) {
			Random r = new Random();
	        StringBuffer sb = new StringBuffer();
	        while(sb.length() < 6){
	            sb.append(Integer.toHexString(r.nextInt()));
	        }
	        registers.set(i,sb.toString().substring(0,6));
		}

		initRegisters();

		return true;
	}

	public String toString() {
		String temp = String.format("\tXAR: %-8.6s XDI: %-8.6s XDO: %-8.6s PC: %-8.6s IR: %-8.6s EMIT: %-8.6s RR: %-8.6s PSW: %-4.6s Register Values:",
									   XAR,XDI,XDO,PC,IR,EMIT,RR,PSW);
		for(String x : registers) {
			temp += x + " ";
		}
		return temp;
	}
}