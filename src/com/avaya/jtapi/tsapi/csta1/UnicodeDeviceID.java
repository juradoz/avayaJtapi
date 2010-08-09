package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class UnicodeDeviceID extends ASNSequenceOf {
	public static String decode(InputStream in) {
		UnicodeDeviceID _this = new UnicodeDeviceID();
		_this.doDecode(in);
		if (_this.array.length > 0) {
			return new String(_this.array);
		}
		return null;
	}

	public static void encode(String device, OutputStream memberStream) {
		char[] deviceArray = device.toCharArray();
		UnicodeDeviceID _this = new UnicodeDeviceID(deviceArray);
		_this.doEncode(deviceArray.length, memberStream);
	}

	public static Collection<String> print(String str, String name,
			String indent) {
		return ASNIA5String.print(str, name, indent);
	}

	char[] array;

	public UnicodeDeviceID() {
	}

	public UnicodeDeviceID(char[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return new Character((char) ASNInteger.decode(memberStream));
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new char[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = ((Character) vec.elementAt(i)).charValue();
		}
	}

	@Override
	public void encodeMember(int idx, OutputStream memberStream) {
		ASNInteger.encode(array[idx], memberStream);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.UnicodeDeviceID JD-Core Version: 0.5.4
 */