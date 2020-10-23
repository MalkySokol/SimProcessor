package processContextSwitching;
import java.util.Random;
//Malka Sokol
public class SimProcessor {
	private SimProcess currProcess;
	private int register1;
	private int register2;
	private int register3;
	private int register4;
	private int currInstruction = 1;

	/**
	 * constructor
	 * @param curProcess
	 */
	public SimProcessor(SimProcess currProcess) {
		this.currProcess = currProcess;
	}
	/**
	 * setter for the current process
	 * @param currProcess
	 */
	public void setCurrProcess(SimProcess currProcess) {
		this.currProcess = currProcess;
	}
	/**
	 * gets the current process
	 * @return currProcess
	 */
	public SimProcess getCurrProcess() {
		return currProcess;
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
	public ProcessState executeNextInstruction() {
		//execute the current instruction
		ProcessState now = currProcess.execute(currInstruction);
		//increment the currInstruction
		currInstruction++;
		//set random values to the four registers
		Random rand = new Random();
		int one = rand.nextInt(20000);
		register1 = one;
		int two = rand.nextInt(20000);
		register2 = two;
		int three = rand.nextInt(20000);
		register3 = three;
		int four = rand.nextInt(20000);
		register4 = four;
		return now;
	}
}
