/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ public class UserToUserInfo
/*    */ {
/*    */   protected short type;
/*    */   protected byte[] data;
/*    */ 
/*    */   public UserToUserInfo(String _data)
/*    */   {
/* 30 */     this.type = 4;
/* 31 */     this.data = _data.getBytes();
/*    */   }
/*    */ 
/*    */   public UserToUserInfo(byte[] _data)
/*    */   {
/* 39 */     this.type = 0;
/* 40 */     this.data = _data;
/*    */   }
/*    */ 
/*    */   public String getString()
/*    */   {
/* 49 */     return new String(this.data);
/*    */   }
/*    */ 
/*    */   public byte[] getBytes()
/*    */   {
/* 57 */     return this.data;
/*    */   }
/*    */ 
/*    */   public boolean isAscii()
/*    */   {
/* 65 */     return this.type == 4;
/*    */   }
/*    */ 
/*    */   public UserToUserInfo(byte[] _data, short _type)
/*    */   {
/* 78 */     this.type = _type; this.data = _data;
/*    */   }
/*    */ 
/*    */   public short getType()
/*    */   {
/* 85 */     return this.type;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.UserToUserInfo
 * JD-Core Version:    0.5.4
 */