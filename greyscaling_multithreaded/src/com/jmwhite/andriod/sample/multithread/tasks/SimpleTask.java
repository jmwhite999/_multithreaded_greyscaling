package com.jmwhite.andriod.sample.multithread.tasks;

import android.os.Handler;
import android.os.Message;

public class SimpleTask implements Runnable {
	private Handler handler;
	private int i = 0;
	private volatile Thread task;
	private volatile boolean threadSuspended = false;
	private int id;
	private String name;

	public SimpleTask(Handler handler, int id, String name) {
		this.handler = handler;
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Start the thread
	 */
	public synchronized void start() {
		if (task != null) {
			this.stopWithInterrupt();
		}
		this.threadSuspended = false;
		task = new Thread(this);// only initial one thread for this object
		task.start();
	}

	/**
	 * Stop the thread if the thread can bring back to runnable state , and the
	 * program will go to next loop. Note: this may not work, in some case. For
	 * example, the thread is block in some reason (Locking, blocking queue...),
	 * the thread is waked up , but it sleep or blocked again, and never reach
	 * the next loop in the while loop.
	 */
	public synchronized void stop() {
		task = null; // set the flag and while loop in the run() will exit
		i = 0;
		notify(); // try to wake up the thread which associate this object
	}

	/**
	 * Stop the thread, even it is blocked by others(Blocking Queue)
	 */
	public synchronized void stopWithInterrupt() {
		Thread tempThread = task;
		i = 0;
		task = null;
		if (tempThread != null) {
			tempThread.interrupt();
		}
	}

	/**
	 * Suspend and resume the thread which associate with this object
	 */
	public synchronized void pauseAndResume() {
		threadSuspended = !threadSuspended;
		if (!threadSuspended) {
			notify();
		}
	}

	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		task = thisThread;
		// the thread keep running until the task is set to "null" 
		try {
			while (task == thisThread) {
				i++;
				if (i > 100) {
					//task is finish now
					task = null;
					break;
				}
				TaskProgressMessage taskMsg = new TaskProgressMessage();
				taskMsg.setId(this.id);
				taskMsg.setName(this.name);
				taskMsg.setProgress(i);
				Message msg = handler.obtainMessage(1, taskMsg);
				handler.sendMessage(msg);
				
				Thread.sleep(250);

				// This is required by the language, and ensures that wait and
				// notify
				// are properly serialized. In practical terms, this eliminates
				// race conditions that could cause the "suspended" thread to
				// miss a notify and remain suspended indefinitely.
				if (threadSuspended) {
					synchronized (this) {
						while (threadSuspended && task == thisThread) {
							// put the thread in wait (suspend/pause the thread)
							wait();
						}
					}
				}
			}
		} catch (InterruptedException e) {
			//this is runnable object, and possible will be use by other services
			//like thread pool, so don't swallow interrupt, and need to call the following
			//and allow the upper level services to handle the interruption
			Thread.currentThread().interrupt();
		}
	}
}
