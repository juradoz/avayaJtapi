/*     */ package javax.telephony;
/*     */ 
/*     */ public class ProviderUnavailableException extends RuntimeException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int CAUSE_UNKNOWN = 160;
/*     */   public static final int CAUSE_NOT_IN_SERVICE = 161;
/*     */   public static final int CAUSE_INVALID_SERVICE = 162;
/*     */   public static final int CAUSE_INVALID_ARGUMENT = 163;
/*     */   private int _cause;
/*     */ 
/*     */   public ProviderUnavailableException()
/*     */   {
/* 102 */     this._cause = 160;
/*     */   }
/*     */ 
/*     */   public ProviderUnavailableException(int cause)
/*     */   {
/* 112 */     this._cause = cause;
/*     */   }
/*     */ 
/*     */   public ProviderUnavailableException(String s)
/*     */   {
/* 122 */     super(s);
/*     */ 
/* 124 */     this._cause = 160;
/*     */   }
/*     */ 
/*     */   public ProviderUnavailableException(int cause, String s)
/*     */   {
/* 135 */     super(s);
/*     */ 
/* 137 */     this._cause = cause;
/*     */   }
/*     */ 
/*     */   public int getReason()
/*     */   {
/* 151 */     return this._cause;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.ProviderUnavailableException
 * JD-Core Version:    0.5.4
 */