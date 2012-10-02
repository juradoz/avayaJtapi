package com.avaya.jtapi.tsapi.asn1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.apache.log4j.Logger;

public abstract class ASNSequenceOf extends ASN1 {
	protected Vector<Object> vec;
	private static Logger log = Logger.getLogger(ASNSequenceOf.class);

	public ASNSequenceOf() {
		this.vec = new Vector<Object>();
	}

	public void doDecode(InputStream in) {
		try {
			if (in.read() != 48) {
				throw new ASN1Exception("Decoder: expected SEQUENCE tag");
			}
			int length = decodeLength(in);

			byte[] buf = new byte[length];
			int count = in.read(buf);
			if ((length > 0) && (count != length)) {
				log.error("expected " + length + "bytes but read " + count
						+ "bytes");
			}
			ByteArrayInputStream memberStream = new ByteArrayInputStream(buf);

			while (memberStream.available() > 0) {
				this.vec.addElement(decodeMember(memberStream));
			}
		} catch (IOException e) {
			throw new ASN1Exception("Decoder: SEQUENCE OF got unexpected EOF");
		}
	}

	public void doEncode(int numElements, OutputStream out) {
		try {
			ByteArrayOutputStream memberStream = new ByteArrayOutputStream();

			for (int i = 0; i < numElements; i++) {
				encodeMember(i, memberStream);
			}
			out.write(48);
			encodeLength(out, memberStream.size());
			memberStream.writeTo(out);
		} catch (IOException e) {
			throw new ASN1Exception(
					"Encoder: SEQUENCE OF got unexpected IO error");
		}
	}

	public Object decodeMember(InputStream in) {
		return null;
	}

	public void encodeMember(int index, OutputStream out) {
	}
}