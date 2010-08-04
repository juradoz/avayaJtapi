/*    */ package javax.telephony;
/*    */ 
/*    */ public class InvalidPartyException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int _type;
/*    */   public static final int ORIGINATING_PARTY = 0;
/*    */   public static final int DESTINATION_PARTY = 1;
/*    */   public static final int UNKNOWN_PARTY = 2;
/*    */ 
/*    */   public InvalidPartyException(int type)
/*    */   {
/* 63 */     this._type = type;
/*    */   }
/*    */ 
/*    */   public InvalidPartyException(int type, String s)
/*    */   {
/* 74 */     super(s);
/* 75 */     this._type = type;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 86 */     return this._type;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.InvalidPartyException
 * JD-Core Version:    0.5.4
 */