/**
* Core Register Positions:
* -----------------------------------------------
* | XAR | XDI | XDO | PC | IR | EMIT | RR | PSW |
* -----------------------------------------------
*/
import java.util.List;
import java.util.ArrayList;

public class RegisterSet {
	public List<String> core;
	public List<String> registers;

	public String XAR ;
	public String XDI ;
	public String XDO ;
	public String PC  ;
	public String IR  ;
	public String EMIT;
	public String RR  ;
	public String PSW ;

	public RegisterSet() {
		core 	  = new ArrayList<String>();
		registers = new ArrayList<String>();
		initRegisters();
	}

	public RegisterSet(List<String> new_Core, List<String> new_Registers) {
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

	public String toString() {
		String temp = String.format("\tXAR: %-8.6s XDI: %-8.6s XDO: %-8.6s PC: %-8.6s IR: %-8.6s EMIT: %-8.6s RR: %-8.6s PSW: %-4.6s Register Values:",
									   XAR,XDI,XDO,PC,IR,EMIT,RR,PSW);
		for(String x : registers) {
			temp += x + " ";
		}
		return temp;
	}
}