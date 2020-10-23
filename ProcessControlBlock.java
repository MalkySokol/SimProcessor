package processContextSwitching;

import java.util.Random;

public class ProcessControlBlock {
	private SimProcess simProcess;
	private int register1;
	private int register2;
	private int register3;
	private int register4;
	private int currInstruction = 1;
	
	/**
	 * constructor
	 */
	public ProcessControlBlock(SimProcess simProcess) {
		this.simProcess = simProcess;
		
	}
	/**
	 * gets the current process
	 * @return currProcess
	 */
	public SimProcess getsimProcess() {
		return simProcess;
	}
	/**
	 * sets the values for the 4 registers
	 * @param register1
	 * @param register2
	 * @param register3
	 * @param register4
	 */
	public void setRegisterValues(int register1, int register2, int register3, int register4) {
		this.register1 = register1;
		this.register2 = register2;
		this.register3 = register3;
		this.register4 = register4;
	}
	public int getRegister1Value() {
		return register1;
	}
	public int getRegister2Value() {
		return register2;
	}
	public int getRegister3Value() {
		return register3;
	}
	public int getRegister4Value() {
		return register4;
	}
	public void setCurrInstruction(int currInstruction) {
		if(currInstruction < 0) {
			throw new IllegalArgumentException();
		}
		this.currInstruction = currInstruction;
	}
	public int getCurrInstruction() {
		return currInstruction;
	}

}
