package javax.telephony;

public abstract interface MultiCallMetaEvent extends MetaEvent
{
  public static final int MULTICALL_META_MERGE_STARTED = 200;
  public static final int MULTICALL_META_MERGE_ENDED = 201;
  public static final int MULTICALL_META_TRANSFER_STARTED = 202;
  public static final int MULTICALL_META_TRANSFER_ENDED = 203;

  public abstract Call getNewCall();

  public abstract Call[] getOldCalls();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.MultiCallMetaEvent
 * JD-Core Version:    0.5.4
 */