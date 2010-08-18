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
		Tt.setTriggerFileName(Tt.m_trigger_file_name);

		Tt.isTriggered();

		Tt.m_enabled = false;

		Tt.m_trigger_file = null;

		Tt.m_trigger_file_name = Tt.m_TRIGGER_DEFAULT_FILE_NAME;
	}

	public static boolean isEnabled() {
		return Tt.m_enabled;
	}

	public static boolean isTriggered() {
		if (Tt.m_trigger_file == null)
			Tt.m_enabled = false;
		else if (Tt.m_trigger_file.exists() == true)
			Tt.m_enabled = true;

		return Tt.m_enabled;
	}

	public static void println(final String s) {
		if (!Tt.isTriggered())
			return;
		Tt.log.info(s);
	}

	public static void printlnStatus(final PrintStream p) {
		p.println("Avaya JTAPI triggered tracing: "
				+ (Tt.isTriggered() ? "on" : "off")
				+ " - trigger file: "
				+ (Tt.m_trigger_file_name == null ? "<null>"
						: Tt.m_trigger_file_name));
	}

	public static void setTriggerFileName(final String trigger_path) {
		if (trigger_path == null) {
			Tt.m_trigger_file_name = null;
			Tt.m_trigger_file = null;
		} else {
			Tt.m_trigger_file_name = trigger_path;
			Tt.m_trigger_file = new File(Tt.m_trigger_file_name);
		}
	}
}
