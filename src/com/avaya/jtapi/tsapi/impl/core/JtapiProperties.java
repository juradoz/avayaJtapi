package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.util.JtapiUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class JtapiProperties extends Properties {
	private static final long serialVersionUID = 1L;
	private String delimiters = "=";

	public JtapiProperties() {
	}

	public JtapiProperties(String delimiters) {
		this.delimiters = delimiters;
	}

	public void load(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		boolean cont = true;
		do {
			String line = bufferedReader.readLine();
			if (line != null) {
				if ((!line.equals("")) && (!line.startsWith("#"))) {
					String[] tokens = JtapiUtils.getTokens(line,
							this.delimiters);
					if (tokens != null) {
						if (tokens[1] == null)
							tokens[1] = "";
						put(tokens[0], tokens[1]);
					}
				}
			} else {
				cont = false;
			}
		}

		while (cont);
	}

	public static void main(String[] args) {
		URL url = ClassLoader.getSystemClassLoader().getResource("TSAPI.PRO");
		System.out.println(url.getFile());

		Properties properties = new Properties();
		try {
			InputStream in = url.openStream();
			properties.load(in);
			System.out.println(properties);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}