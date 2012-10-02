package com.avaya.jtapi.tsapi.impl.core;

final class DevWithType {
	TSDevice device;
	boolean isTerminal;

	DevWithType(TSDevice _device, boolean _isTerminal) {
		this.device = _device;
		this.isTerminal = _isTerminal;
	}

	public int hashCode() {
		if (this.isTerminal) {
			return this.device.hashCode() + 1;
		}
		return this.device.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof DevWithType)) {
			if ((this.device == ((DevWithType) obj).device)
					&& (this.isTerminal == ((DevWithType) obj).isTerminal)) {
				return true;
			}

			return false;
		}

		return false;
	}
}