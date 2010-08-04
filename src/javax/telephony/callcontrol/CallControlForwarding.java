/*     */ package javax.telephony.callcontrol;
/*     */ 
/*     */ public class CallControlForwarding
/*     */ {
/*     */   private String destAddress;
/*     */   private String caller;
/*     */   private int type;
/*     */   private int whichCalls;
/*     */   public static final int ALL_CALLS = 1;
/*     */   public static final int INTERNAL_CALLS = 2;
/*     */   public static final int EXTERNAL_CALLS = 3;
/*     */   public static final int SPECIFIC_ADDRESS = 4;
/*     */   public static final int FORWARD_UNCONDITIONALLY = 1;
/*     */   public static final int FORWARD_ON_BUSY = 2;
/*     */   public static final int FORWARD_ON_NOANSWER = 3;
/*     */ 
/*     */   public CallControlForwarding(String destAddress)
/*     */   {
/* 101 */     this.destAddress = destAddress;
/* 102 */     this.type = 1;
/* 103 */     this.caller = null;
/* 104 */     this.whichCalls = 1;
/*     */   }
/*     */ 
/*     */   public CallControlForwarding(String destAddress, int type)
/*     */   {
/* 116 */     this.destAddress = destAddress;
/* 117 */     this.type = type;
/* 118 */     this.caller = null;
/* 119 */     this.whichCalls = 1;
/*     */   }
/*     */ 
/*     */   public CallControlForwarding(String destAddress, int type, boolean internalCalls)
/*     */   {
/* 135 */     this.destAddress = destAddress;
/* 136 */     this.type = type;
/* 137 */     this.caller = null;
/* 138 */     if (internalCalls) {
/* 139 */       this.whichCalls = 2;
/*     */     }
/*     */     else
/* 142 */       this.whichCalls = 3;
/*     */   }
/*     */ 
/*     */   public CallControlForwarding(String destAddress, int type, String caller)
/*     */   {
/* 157 */     this.destAddress = destAddress;
/* 158 */     this.type = type;
/* 159 */     this.caller = caller;
/* 160 */     this.whichCalls = 4;
/*     */   }
/*     */ 
/*     */   public String getDestinationAddress()
/*     */   {
/* 170 */     return this.destAddress;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 181 */     return this.type;
/*     */   }
/*     */ 
/*     */   public int getFilter()
/*     */   {
/* 194 */     return this.whichCalls;
/*     */   }
/*     */ 
/*     */   public String getSpecificCaller()
/*     */   {
/* 207 */     return this.caller;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlForwarding
 * JD-Core Version:    0.5.4
 */