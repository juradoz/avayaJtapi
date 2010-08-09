package javax.telephony.phone;

import javax.telephony.InvalidArgumentException;

public abstract interface PhoneDisplay extends Component {
	public abstract String getDisplay(int paramInt1, int paramInt2)
			throws InvalidArgumentException;

	public abstract int getDisplayColumns();

	public abstract int getDisplayRows();

	public abstract void setDisplay(String paramString, int paramInt1,
			int paramInt2) throws InvalidArgumentException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.phone.PhoneDisplay JD-Core Version: 0.5.4
 */