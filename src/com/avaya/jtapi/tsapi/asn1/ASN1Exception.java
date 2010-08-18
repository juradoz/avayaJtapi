package com.avaya.jtapi.tsapi.asn1;

public class ASN1Exception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ASN1Exception() {
	}

	public ASN1Exception(final String message) {
		super(message);
	}

	public ASN1Exception(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ASN1Exception(final Throwable cause) {
		super(cause);
	}
}
