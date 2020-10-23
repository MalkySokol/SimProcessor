package processContextSwitching;
import java.util.*;

public class Processor {
	public static void main(String[] args) {
		final int QUANTUM = 5;
		
		// ten processes
		SimProcess process1 = new SimProcess(1, "write",298);
		SimProcess process2 = new SimProcess(2, "read", 250);
		SimProcess process3 = new SimProcess(3, "add", 345);
		SimProcess process4 = new SimProcess(4,"and", 350);
		SimProcess process5 = new SimProcess(5, "or", 234);
		SimProcess process6 = new SimProcess(6, "nor" ,321);
		SimProcess process7 = new SimProcess(7, "mod" , 123);
		SimProcess process8 = new SimProcess(8, "div", 199);
		SimProcess process9 = new SimProcess(9, "multiply",244 );
		SimProcess process10 = new SimProcess(10, "subtract", 190);
		
		//ten pcb's
		ProcessControlBlock pcb1  = new ProcessControlBlock(process1);
		ProcessControlBlock pcb2  = new ProcessControlBlock(process2);
		ProcessControlBlock pcb3  = new ProcessControlBlock(process3);
		ProcessControlBlock pcb4  = new ProcessControlBlock(process4);
		ProcessControlBlock pcb5  = new ProcessControlBlock(process5);
		ProcessControlBlock pcb6  = new ProcessControlBlock(process6);
		ProcessControlBlock pcb7  = new ProcessControlBlock(process7);
		ProcessControlBlock pcb8  = new ProcessControlBlock(process8);
		ProcessControlBlock pcb9  = new ProcessControlBlock(process9);
		ProcessControlBlock pcb10  = new ProcessControlBlock(process10);
		
		ArrayList<ProcessControlBlock> ready = new ArrayList<>();
		//add the pcb's to the ArrayList
		ready.add(pcb1);
		ready.add(pcb2);
		ready.add(pcb3);
		ready.add(pcb4);
		ready.add(pcb5);
		ready.add(pcb6);
		ready.add(pcb7);
		ready.add(pcb8);
		ready.add(pcb9);
		ready.add(pcb10);
		
		//SimProcessor
		SimProcessor simProcessor = new SimProcessor(process1);
		
		//ArrayList to hold blocked processes
		ArrayList<ProcessControlBlock> blocked = new ArrayList<>();
	
		int ctr = 0;//counts the instructions and a context switch is performed when ctr = quantum
		for(int i = 0; i < 3000; i++) {
			System.out.println("Step: " + i);
			//start with executing the next instruction of the current process
			System.out.print("Executing Instruction: " + simProcessor.getCurrInstruction() + " ");
			ProcessState retState = simProcessor.executeNextInstruction();
			//System.out.println("retState = " + retState);
			//increment ctr because another instruction was executed
			ctr++;
			
			//check if a context switch is needed
			if(ctr >= QUANTUM || retState == retState.BLOCKED || retState == retState.FINISHED) {
				//display the reason for the context switch
				if(retState == retState.BLOCKED) {
					System.out.println("**Process Blocked**");
				}
				else if(retState == retState.FINISHED) {
					System.out.println("**Process Finished**");
				}
				else {
					System.out.println("**Quantum Expired**");
				}
				//perform a context switch
				System.out.println("context switch");
				// if quantum is reached, or process is blocked, the state of the process has to be saved 
				if (retState != retState.FINISHED) {
					System.out.println("saving process " + simProcessor.getCurrProcess().getPID());
					//set the PCB with the current register values
					//the PCB of the current process in the simProcessor is always the 
					// 0th PCB on the ready list
					ready.get(0).setRegisterValues(simProcessor.getRegister1Value(),
							simProcessor.getRegister2Value(),simProcessor.getRegister3Value(),simProcessor.getRegister4Value());
					//display what the register values are
					System.out.println("R1: " + simProcessor.getRegister1Value()
					+ " R2: " + simProcessor.getRegister2Value() + " R3: " + simProcessor.getRegister3Value() + 
					" R4: " + simProcessor.getRegister4Value());
					//set the current instruction to the pcb - the first in the arraylist
					ready.get(0).setCurrInstruction(simProcessor.getCurrInstruction());
				}
				// remove the first pcb/process from the ready list in all cases
				ProcessControlBlock temp = ready.remove(0);
			
				if (retState == retState.BLOCKED) { // add to blocked list
					blocked.add(temp);
				}	
				else if (retState == retState.READY) {// add to ready list if quantum was reached and process is not finished
					ready.add(temp);
				}
				//check that the ready list is not empty
				while(ready.isEmpty()) {
					if (blocked.isEmpty()) {
						System.out.println("Program terminated due to completion of all processes.");
						System.exit(1);
					}
					System.out.println("Processer is idling.");
					Unblock(blocked,ready);
				}
				//in all cases, we are going to restore the next process on the ready list
				System.out.print("Restoring process ");
				//restart the count
				ctr = 0;
				//now restore the first pcb
				simProcessor.setCurrProcess(ready.get(0).getsimProcess());
				simProcessor.setCurrInstruction(ready.get(0).getCurrInstruction());
				simProcessor.setRegisterValues(ready.get(0).getRegister1Value(), 
						ready.get(0).getRegister2Value(),ready.get(0).getRegister3Value() ,
						ready.get(0).getRegister4Value());
				
				//display the current process and register values
				System.out.println(simProcessor.getCurrProcess().getPID());
				System.out.println("R1: " + ready.get(0).getRegister1Value()
				+ " R2: " + ready.get(0).getRegister2Value() + " R3: " + ready.get(0).getRegister3Value() + 
				" R4: " + ready.get(0).getRegister4Value());
			}
			// else is not necessary bec it means that we are continuing with the current process
			Unblock(blocked, ready);
		}
	}
	/**
	 * this method unblocks the blocked processes with 30 percent probability
	 * @param blocked
	 * @param ready
	 */
	public static void Unblock(ArrayList<ProcessControlBlock> blocked,  ArrayList<ProcessControlBlock> ready) {
		//unblock the blocked processes with 30% probability
		Random rand = new Random();
		for(int i = 0; i < blocked.size(); i++) {
			int random = rand.nextInt(99);
			if(random >= 0 && random<=29) {
				//remove the blocked process from the blocked list and add it to the ready list
				ready.add(blocked.get(i));
				blocked.remove(i);
			}
		}
		
	}
}

