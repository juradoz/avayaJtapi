package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public final class UnicodeDeviceID extends ASNSequenceOf {
	char[] array;

	public UnicodeDeviceID() {
	}

	public UnicodeDeviceID(char[] _array) {
		this.array = _array;
	}

	public static String decode(InputStream in) {
		UnicodeDeviceID _this = new UnicodeDeviceID();
		_this.doDecode(in);
		if (_this.array.length > 0) {
			return new String(_this.array);
		}
		return null;
	}

	public void doDecode(InputStream in) {
		super.doDecode(in);

		this.array = new char[this.vec.size()];

		for (int i = 0; i < this.array.length; i++) {
			this.array[i] = ((Character) this.vec.elementAt(i)).charValue();
		}
	}

	public static void encode(String device, OutputStream memberStream) {
		char[] deviceArray = device.toCharArray();
		UnicodeDeviceID _this = new UnicodeDeviceID(deviceArray);
		_this.doEncode(deviceArray.length, memberStream);
	}

	public void encodeMember(int idx, OutputStream memberStream) {
		ASNInteger.encode(this.array[idx], memberStream);
	}

	public Object decodeMember(InputStream memberStream) {
		return new Character((char) ASNInteger.decode(memberStream));
	}

	public static Collection<String> print(String str, String name,
			String indent) {
		return ASNIA5String.print(str, name, indent);
	}
}