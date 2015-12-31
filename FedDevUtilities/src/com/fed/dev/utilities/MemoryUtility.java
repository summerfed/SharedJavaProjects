package com.fed.dev.utilities;

public class MemoryUtility {
	private int mb = 1024*1024;
	private Runtime runtime;
	
	public MemoryUtility() {
		runtime = Runtime.getRuntime();
	}
	
	
  /*  //Getting the runtime reference from system
    System.out.println("##### Heap utilization statistics [MB] #####");*/
     
    /**
     * @return Used Memory in MB
     */
	public long getUsedMemory() {
		return (runtime.totalMemory() - runtime.freeMemory() / mb);
	}
  
	/**
	 * @return Free Memory in MB
	 */
	public long getFreeMemory() {
		return (runtime.freeMemory() / mb);
	}
    
	/**
	 * @return Available Memory in MB
	 */
	public long getAvailableMemory() {
		return (runtime.totalMemory() / mb);
	}
        
	/**
	 * @return Max Memory in MB
	 */
	public long getMaxMemory() {
		return (runtime.maxMemory() / mb);
	}
}
