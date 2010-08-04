package javax.telephony.events;

public abstract interface Ev
{
  public static final int CAUSE_NORMAL = 100;
  public static final int CAUSE_UNKNOWN = 101;
  public static final int CAUSE_CALL_CANCELLED = 102;
  public static final int CAUSE_DEST_NOT_OBTAINABLE = 103;
  public static final int CAUSE_INCOMPATIBLE_DESTINATION = 104;
  public static final int CAUSE_LOCKOUT = 105;
  public static final int CAUSE_NEW_CALL = 106;
  public static final int CAUSE_RESOURCES_NOT_AVAILABLE = 107;
  public static final int CAUSE_NETWORK_CONGESTION = 108;
  public static final int CAUSE_NETWORK_NOT_OBTAINABLE = 109;
  public static final int CAUSE_SNAPSHOT = 110;
  public static final int META_CALL_STARTING = 128;
  public static final int META_CALL_PROGRESS = 129;
  public static final int META_CALL_ADDITIONAL_PARTY = 130;
  public static final int META_CALL_REMOVING_PARTY = 131;
  public static final int META_CALL_ENDING = 132;
  public static final int META_CALL_MERGING = 133;
  public static final int META_CALL_TRANSFERRING = 134;
  public static final int META_SNAPSHOT = 135;
  public static final int META_UNKNOWN = 136;

  public abstract int getCause();

  public abstract int getMetaCode();

  public abstract boolean isNewMetaEvent();

  public abstract int getID();

  /** @deprecated */
  public abstract Object getObserved();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.events.Ev
 * JD-Core Version:    0.5.4
 */