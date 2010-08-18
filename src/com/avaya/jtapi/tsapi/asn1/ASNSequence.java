package com.avaya.jtapi.tsapi.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

public abstract class ASNSequence extends ASN1 {
	private static Logger log = Logger.getLogger(ASNSequence.class);

	public static void encode(final ASNSequence _this, final OutputStream out) {
		if (_this == null) {
			ASNSequence.log.error("Encoder: received null sequence");
			throw new ASN1Exception("Encoder: received null sequence");
		}
		_this.encode(out);
	}

	public void decodeMembers(final InputStream memberStream) {
	}

	public final void doDecode(final InputStream in) {
		try {
			if (in.read() != 48)
				throw new ASN1Exception("Decoder: expected SEQUENCE tag");
			ASN1.decodeLength(in);
			decodeMembers(in);
		} catch (final IOException e) {
			throw new ASN1Exception("Decoder: SEQUENCE OF got unexpected EOF");
		}
	}

	public final void encode(final OutputStream out) {
		try {
			final ByteArrayOutputStream memberStream = new ByteArrayOutputStream();

			encodeMembers(memberStream);
			out.write(48);
			ASN1.encodeLength(out, memberStream.size());
			memberStream.writeTo(out);
		} catch (final IOException e) {
			throw new ASN1Exception("Encoder: SEQUENCE got unexpected IO error");
		}
	}

	public void encodeMembers(final OutputStream memberStream) {
	}

	public int getPDU() {
		return 0;
	}

	public Collection<String> print() {
		return new ArrayList<String>();
	}

}
