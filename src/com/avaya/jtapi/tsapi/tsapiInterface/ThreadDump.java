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
		final Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
		ThreadDump.log.info(Integer.valueOf(map.size()));

		final Set<Thread> keySet = map.keySet();
		final Thread[] threads = new Thread[keySet.size()];
		keySet.toArray(threads);

		for (int i = 0; i < threads.length; ++i) {
			final Thread t = threads[i];
			final String daemon = t.isDaemon() ? "Daemon" : "";
			final String alive = t.isAlive() ? "Alive" : "Dead";
			final String interrupted = t.isInterrupted() ? "Interrupted" : "";
			final String heading = t.getName() + ": " + daemon + " prio="
					+ t.getPriority() + " tid=" + t.getId() + " state="
					+ t.getState() + " " + alive + " " + interrupted;
			ThreadDump.log.info(heading);
			final StackTraceElement[] stes = map.get(t);
			for (int j = 0; j < stes.length; ++j) {
				final StackTraceElement ste = stes[j];
				final String line = ste.getClassName() + "."
						+ ste.getMethodName() + "(" + ste.getFileName() + ":"
						+ ste.getLineNumber() + ")";

				ThreadDump.log.info("\t at " + line);
			}
		}
	}

	public static void dumpJavaThreadsBySignal() {
		final Runtime rt = Runtime.getRuntime();
		try {
			final String[] cmd = { "bash", "-c", "echo $PPID" };
			Process p = rt.exec(cmd);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = null;
			if ((line = br.readLine()) != null) {
				ThreadDump.log.info("The parent PID is " + line);

				p = rt.exec("kill -QUIT " + line);
				br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
			}
		} catch (final IOException e) {
			ThreadDump.log.error(e.getMessage(), e);
		}
	}

	public static void main(final String[] args) {
		ThreadDump.dumpJavaThreadsByAPI();
	}

	public ThreadDump() {
		super("JTAPI ThreadDump thread#" + ++ThreadDump.counter);
	}

	@Override
	public void run() {
		try {
			ThreadDump.dumpJavaThreadsByAPI();
		} catch (final Exception ex) {
			ThreadDump.log.error(
					"Exception when doing thread dump:" + ex.getMessage(), ex);
		}
	}
}
