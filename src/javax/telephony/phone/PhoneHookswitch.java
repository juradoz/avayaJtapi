package javax.telephony.phone;

import javax.telephony.InvalidArgumentException;

public abstract interface PhoneHookswitch extends Component {
	public static final int ON_HOOK = 0;
	public static final int OFF_HOOK = 1;

	public abstract void setHookSwitch(int paramInt)
			throws InvalidArgumentException;

	public abstract int getHookSwitchState();
}