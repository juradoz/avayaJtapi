package com.avaya.jtapi.tsapi.impl.core;

final class DevWithType {
	TSDevice device;
	boolean isTerminal;

	DevWithType(TSDevice _device, boolean _isTerminal) {
		device = _device;
		isTerminal = _isTerminal;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DevWithType) {
			return (device == ((DevWithType) obj).device)
					&& (isTerminal == ((DevWithType) obj).isTerminal);
		}

		return false;
	}

	@Override
	public int hashCode() {
		if (isTerminal) {
			return device.hashCode() + 1;
		}
		return device.hashCode();
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.DevWithType JD-Core Version: 0.5.4
 */