package com.avaya.jtapi.tsapi.asn1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public abstract class ASNSequenceOf extends ASN1 {
	protected Vector<Object> vec;

	public ASNSequenceOf() {
		vec = new Vector<Object>();
	}

	public Object decodeMember(final InputStream in) {
		return null;
	}

	public void doDecode(final InputStream in) {
		try {
			if (in.read() != 48)
				throw new ASN1Exception("Decoder: expected SEQUENCE tag");
			final int length = ASN1.decodeLength(in);

			final byte[] buf = new byte[length];
			in.read(buf);
			final ByteArrayInputStream memberStream = new ByteArrayInputStream(
					buf);

			while (memberStream.available() > 0)
				vec.addElement(decodeMember(memberStream));
		} catch (final IOException e) {
			throw new ASN1Exception("Decoder: SEQUENCE OF got unexpected EOF");
		}
	}

	public void doEncode(final int numElements, final OutputStream out) {
		try {
			final ByteArrayOutputStream memberStream = new ByteArrayOutputStream();

			for (int i = 0; i < numElements; ++i)
				encodeMember(i, memberStream);
			out.write(48);
			ASN1.encodeLength(out, memberStream.size());
			memberStream.writeTo(out);
		} catch (final IOException e) {
			throw new ASN1Exception(
					"Encoder: SEQUENCE OF got unexpected IO error");
		}
	}

	public void encodeMember(final int index, final OutputStream out) {
	}
}
