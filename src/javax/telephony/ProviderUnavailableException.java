 package javax.telephony;
 
 public class ProviderUnavailableException extends RuntimeException
 {
   private static final long serialVersionUID = 1L;
   public static final int CAUSE_UNKNOWN = 160;
   public static final int CAUSE_NOT_IN_SERVICE = 161;
   public static final int CAUSE_INVALID_SERVICE = 162;
   public static final int CAUSE_INVALID_ARGUMENT = 163;
   private int _cause;
 
   public ProviderUnavailableException()
   {
     this._cause = 160;
   }
 
   public ProviderUnavailableException(int cause)
   {
     this._cause = cause;
   }
 
   public ProviderUnavailableException(String s)
   {
     super(s);
 
     this._cause = 160;
   }
 
   public ProviderUnavailableException(int cause, String s)
   {
     super(s);
 
     this._cause = cause;
   }
 
   public int getReason()
   {
     return this._cause;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.ProviderUnavailableException
 * JD-Core Version:    0.5.4
 */