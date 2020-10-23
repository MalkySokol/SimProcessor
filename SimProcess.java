package processContextSwitching;
import java.util.Random;
//this class simulates a process
public class SimProcess {
	private int pid;
	private String procName;
	private int totalInstructions;
	/*
	 * constructor
	 */
	public SimProcess(int pid, String procName, int totalInstructions) {
		//validation
		if(pid > 0) {
			this.pid = pid;
		}
		else {
			throw new IllegalArgumentException();
		}
		this.procName = procName;
		//validation
		if(totalInstructions > 0) {
			this.totalInstructions = totalInstructions;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	public int getPID() {
		return pid;
	}
	public ProcessState execute(int i) {
		System.out.println("PID: " + pid + " " + "Process Name: " + procName + " " + "Total Instructions: " + totalInstructions);
		if( i >= totalInstructions) {
			return ProcessState.FINISHED;
		}
		//with 15% probability
		Random rand = new Random();
		int random = rand.nextInt(99);
		if(random >= 0 && random<=14){
			return ProcessState.BLOCKED;
		}
		//otherwise it is ready
		return ProcessState.READY;
		
	}

}
