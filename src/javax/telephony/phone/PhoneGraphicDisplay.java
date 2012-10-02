package javax.telephony.phone;

import java.awt.Dimension;
import java.awt.Graphics;

public abstract interface PhoneGraphicDisplay extends Component {
	public abstract Graphics getGraphics();

	public abstract Dimension size();
}