package com.avaya.jtapi.tsapi.tsapiInterface.streams;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public final class IntelSocketInputStream {
	InputStream in;

	public IntelSocketInputStream(InputStream _in) {
		this.in = _in;
	}

	public void readFully(byte[] buf) throws IOException {
		int bytesRead;
		for (int offset = 0; offset < buf.length; offset += bytesRead) {
			if ((bytesRead = this.in.read(buf, offset, buf.length - offset)) < 0)
				throw new EOFException();
		}
	}

	public int readInt() throws IOException {
		byte[] buf = new byte[4];
		readFully(buf);
		return ((buf[3] & 0xFF) << 24) + ((buf[2] & 0xFF) << 16)
				+ ((buf[1] & 0xFF) << 8) + ((buf[0] & 0xFF) << 0);
	}
}