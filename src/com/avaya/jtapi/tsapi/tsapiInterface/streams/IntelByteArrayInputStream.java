package com.avaya.jtapi.tsapi.tsapiInterface.streams;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;

public final class IntelByteArrayInputStream extends ByteArrayInputStream {
	public IntelByteArrayInputStream(final byte[] buf) {
		super(buf);
	}

	public int readInt() throws IOException {
		final int ch1 = read();
		final int ch2 = read();
		final int ch3 = read();
		final int ch4 = read();

		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0);
	}

	public short readShort() throws IOException {
		final int ch1 = read();
		final int ch2 = read();

		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return (short) ((ch2 << 8) + (ch1 << 0));
	}
}
