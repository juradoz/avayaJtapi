package com.avaya.jtapi.tsapi.impl.core;

import java.io.File;
import java.io.PrintStream;
import org.apache.log4j.Logger;

public class Tt {
	private static Logger log = Logger.getLogger(Tt.class);

	private static boolean m_enabled = false;

	private static File m_trigger_file = null;

	private static String m_trigger_file_name = "/tmp/enable_jtapi_tracing.txt";
	static final String m_TRIGGER_DEFAULT_FILE_NAME = "/tmp/enable_jtapi_tracing.txt";

	public static void printlnStatus(PrintStream p) {
		p.println(new StringBuilder()
				.append("Avaya JTAPI triggered tracing: ")
				.append(isTriggered() ? "on" : "off")
				.append(" - trigger file: ")
				.append(m_trigger_file_name == null ? "<null>"
						: m_trigger_file_name).toString());
	}

	public static boolean isEnabled() {
		return m_enabled;
	}

	public static boolean isTriggered() {
		if (m_trigger_file == null) {
			m_enabled = false;
		} else if (m_trigger_file.exists() == true) {
			m_enabled = true;
		}

		return m_enabled;
	}

	public static void println(String s) {
		if (isTriggered()) {
			log.info(s);
		}
	}

	public static void setTriggerFileName(String trigger_path) {
		if (trigger_path == null) {
			m_trigger_file_name = null;
			m_trigger_file = null;
		} else {
			m_trigger_file_name = trigger_path;
			m_trigger_file = new File(m_trigger_file_name);
		}
	}

	static {
		setTriggerFileName(m_trigger_file_name);

		isTriggered();
	}
}