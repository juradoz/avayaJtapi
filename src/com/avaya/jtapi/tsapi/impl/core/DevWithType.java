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

