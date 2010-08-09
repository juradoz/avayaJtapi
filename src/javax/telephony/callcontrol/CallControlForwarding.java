 package javax.telephony.callcontrol;
 
 public class CallControlForwarding
 {
   private String destAddress;
   private String caller;
   private int type;
   private int whichCalls;
   public static final int ALL_CALLS = 1;
   public static final int INTERNAL_CALLS = 2;
   public static final int EXTERNAL_CALLS = 3;
   public static final int SPECIFIC_ADDRESS = 4;
   public static final int FORWARD_UNCONDITIONALLY = 1;
   public static final int FORWARD_ON_BUSY = 2;
   public static final int FORWARD_ON_NOANSWER = 3;
 
   public CallControlForwarding(String destAddress)
   {
     this.destAddress = destAddress;
     this.type = 1;
     this.caller = null;
     this.whichCalls = 1;
   }
 
   public CallControlForwarding(String destAddress, int type)
   {
     this.destAddress = destAddress;
     this.type = type;
     this.caller = null;
     this.whichCalls = 1;
   }
 
   public CallControlForwarding(String destAddress, int type, boolean internalCalls)
   {
     this.destAddress = destAddress;
     this.type = type;
     this.caller = null;
     if (internalCalls) {
       this.whichCalls = 2;
     }
     else
       this.whichCalls = 3;
   }
 
   public CallControlForwarding(String destAddress, int type, String caller)
   {
     this.destAddress = destAddress;
     this.type = type;
     this.caller = caller;
     this.whichCalls = 4;
   }
 
   public String getDestinationAddress()
   {
     return this.destAddress;
   }
 
   public int getType()
   {
     return this.type;
   }
 
   public int getFilter()
   {
     return this.whichCalls;
   }
 
   public String getSpecificCaller()
   {
     return this.caller;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlForwarding
 * JD-Core Version:    0.5.4
 */