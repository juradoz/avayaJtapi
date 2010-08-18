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

	public Object decodeMember(InputStream in) {
		return null;
	}

	public void doDecode(InputStream in) {
		try {
			if (in.read() != 48) {
				throw new ASN1Exception("Decoder: expected SEQUENCE tag");
			}
			int length = decodeLength(in);

			byte[] buf = new byte[length];
			in.read(buf);
			ByteArrayInputStream memberStream = new ByteArrayInputStream(buf);

			while (memberStream.available() > 0) {
				vec.addElement(decodeMember(memberStream));
			}
		} catch (IOException e) {
			throw new ASN1Exception("Decoder: SEQUENCE OF got unexpected EOF");
		}
	}

	public void doEncode(int numElements, OutputStream out) {
		try {
			ByteArrayOutputStream memberStream = new ByteArrayOutputStream();

			for (int i = 0; i < numElements; ++i) {
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

	public void encodeMember(int index, OutputStream out) {
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.asn1.ASNSequenceOf JD-Core Version: 0.5.4
 */