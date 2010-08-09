package com.avaya.jtapi.tsapi.impl.core;

import java.io.File;
import java.io.PrintStream;

import org.apache.log4j.Logger;

public class Tt {
	private static Logger log = Logger.getLogger(Tt.class);
	private static boolean m_enabled;
	private static File m_trigger_file;
	private static String m_trigger_file_name;
	private static final String m_TRIGGER_DEFAULT_FILE_NAME = "/tmp/enable_jtapi_tracing.txt";

	static {
		setTriggerFileName(m_trigger_file_name);

		isTriggered();

		m_enabled = false;

		m_trigger_file = null;

		m_trigger_file_name = "/tmp/enable_jtapi_tracing.txt";
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
		if (!isTriggered()) {
			return;
		}
		log.info(s);
	}

	public static void printlnStatus(PrintStream p) {
		p.println("Avaya JTAPI triggered tracing: "
				+ ((isTriggered()) ? "on" : "off")
				+ " - trigger file: "
				+ ((m_trigger_file_name == null) ? "<null>"
						: m_trigger_file_name));
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
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.Tt JD-Core Version: 0.5.4
 */