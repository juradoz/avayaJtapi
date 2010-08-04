/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ public final class ConnectionID
/*    */ {
/*    */   public static final short STATIC_ID = 0;
/*    */   public static final short DYNAMIC_ID = 1;
/*    */   int callID;
/*    */   String deviceID;
/*    */   short devIDType;
/*    */ 
/*    */   ConnectionID()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ConnectionID(int _callID, String _deviceID, short _devIDType)
/*    */   {
/* 24 */     this.callID = _callID;
/* 25 */     this.deviceID = _deviceID;
/* 26 */     this.devIDType = _devIDType;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 31 */     if (this.deviceID == null)
/*    */     {
/* 33 */       return this.callID + (this.devIDType << 31);
/*    */     }
/*    */ 
/* 37 */     return this.callID + this.deviceID.hashCode() + (this.devIDType << 31);
/*    */   }
/*    */ 
/*    */   public boolean equals(Object anObject)
/*    */   {
/* 43 */     if (anObject instanceof ConnectionID)
/*    */     {
/* 45 */       ConnectionID anotherConnID = (ConnectionID)anObject;
/* 46 */       if (this.deviceID == null)
/*    */       {
/* 48 */         return (this.callID == anotherConnID.callID) && (this.devIDType == anotherConnID.devIDType) && (anotherConnID.deviceID == null);
/*    */       }
/*    */ 
/* 54 */       return (this.callID == anotherConnID.callID) && (this.devIDType == anotherConnID.devIDType) && (this.deviceID.equals(anotherConnID.deviceID));
/*    */     }
/*    */ 
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 64 */     return "ConnectionID(" + this.callID + "," + this.deviceID + "," + this.devIDType + ")";
/*    */   }
/*    */ 
/*    */   public String getDeviceID() {
/* 68 */     return this.deviceID;
/*    */   }
/*    */ 
/*    */   public int getCallID() {
/* 72 */     return this.callID;
/*    */   }
/*    */ 
/*    */   public int getDevIDType() {
/* 76 */     return this.devIDType;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.ConnectionID
 * JD-Core Version:    0.5.4
 */