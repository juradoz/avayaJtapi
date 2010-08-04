/*    */ package com.avaya.jtapi.tsapi.impl.events.termConn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.PrivateDtmfEvent;
/*    */ 
/*    */ public class PrivateDtmfEventImpl
/*    */   implements PrivateDtmfEvent
/*    */ {
/*    */   private char dtmfDigit;
/*    */ 
/*    */   public PrivateDtmfEventImpl(char digit)
/*    */   {
/* 17 */     this.dtmfDigit = digit;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 25 */     int prime = 31;
/* 26 */     int result = 1;
/* 27 */     result = 31 * result + this.dtmfDigit;
/* 28 */     return result;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 36 */     if (this == obj)
/* 37 */       return true;
/* 38 */     if (obj == null)
/* 39 */       return false;
/* 40 */     if (!(obj instanceof PrivateDtmfEventImpl))
/* 41 */       return false;
/* 42 */     PrivateDtmfEventImpl other = (PrivateDtmfEventImpl)obj;
/*    */ 
/* 44 */     return this.dtmfDigit == other.dtmfDigit;
/*    */   }
/*    */ 
/*    */   public char getDtmfDigit()
/*    */   {
/* 52 */     return this.dtmfDigit;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.PrivateDtmfEventImpl
 * JD-Core Version:    0.5.4
 */