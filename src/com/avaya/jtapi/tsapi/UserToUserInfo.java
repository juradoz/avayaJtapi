package com.avaya.jtapi.tsapi;

public class UserToUserInfo {
	protected short type;
	protected byte[] data;

	public UserToUserInfo(byte[] _data) {
		type = 0;
		data = _data;
	}

	public UserToUserInfo(byte[] _data, short _type) {
		type = _type;
		data = _data;
	}

	public UserToUserInfo(String _data) {
		type = 4;
		data = _data.getBytes();
	}

	public byte[] getBytes() {
		return data;
	}

	public String getString() {
		return new String(data);
	}

	public short getType() {
		return type;
	}

	public boolean isAscii() {
		return type == 4;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.UserToUserInfo JD-Core Version: 0.5.4
 */