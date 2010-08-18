package javax.telephony.events;

public abstract interface CallObservationEndedEv extends CallEv {
	public static final int ID = 103;

	public abstract Object getEndedObject();
}
