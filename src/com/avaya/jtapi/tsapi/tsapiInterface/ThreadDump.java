package com.avaya.jtapi.tsapi.tsapiInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class ThreadDump extends Thread {
	private static int counter = 0;

	private static Logger log = Logger.getLogger(ThreadDump.class);

	public static void dumpJavaThreadsByAPI() {
		Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
		log.info(Integer.valueOf(map.size()));

		Set<Thread> keySet = map.keySet();
		Thread[] threads = new Thread[keySet.size()];
		keySet.toArray(threads);

		for (int i = 0; i < threads.length; ++i) {
			Thread t = threads[i];
			String daemon = (t.isDaemon()) ? "Daemon" : "";
			String alive = (t.isAlive()) ? "Alive" : "Dead";
			String interrupted = (t.isInterrupted()) ? "Interrupted" : "";
			String heading = t.getName() + ": " + daemon + " prio="
					+ t.getPriority() + " tid=" + t.getId() + " state="
					+ t.getState() + " " + alive + " " + interrupted;
			log.info(heading);
			StackTraceElement[] stes = (StackTraceElement[]) map.get(t);
			for (int j = 0; j < stes.length; ++j) {
				StackTraceElement ste = stes[j];
				String line = ste.getClassName() + "." + ste.getMethodName()
						+ "(" + ste.getFileName() + ":" + ste.getLineNumber()
						+ ")";

				log.info("\t at " + line);
			}
		}
	}

	public static void dumpJavaThreadsBySignal() {
		Runtime rt = Runtime.getRuntime();
		try {
			String[] cmd = { "bash", "-c", "echo $PPID" };
			Process p = rt.exec(cmd);

			BufferedReader br = new BufferedReader(new InputStreamReader(p
					.getInputStream()));

			String line = null;
			if ((line = br.readLine()) != null) {
				log.info("The parent PID is " + line);

				p = rt.exec("kill -QUIT " + line);
				br = new BufferedReader(new InputStreamReader(p
						.getInputStream()));
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		dumpJavaThreadsByAPI();
	}

	public ThreadDump() {
		super("JTAPI ThreadDump thread#" + ++counter);
	}

	@Override
	public void run() {
		try {
			dumpJavaThreadsByAPI();
		} catch (Exception ex) {
			log
					.error("Exception when doing thread dump:"
							+ ex.getMessage(), ex);
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.ThreadDump JD-Core Version: 0.5.4
 */