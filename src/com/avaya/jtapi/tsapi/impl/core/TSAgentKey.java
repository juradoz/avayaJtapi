/*    */ package com.avaya.jtapi.tsapi.impl.core;
/*    */ 
/*    */ final class TSAgentKey
/*    */ {
/*    */   String agentDeviceID;
/*    */   String acdDeviceID;
/*    */   String agentID;
/*    */ 
/*    */   TSAgentKey(String _agentDeviceID, String _acdDeviceID, String _agentID)
/*    */   {
/* 13 */     this.agentDeviceID = _agentDeviceID;
/* 14 */     this.acdDeviceID = _acdDeviceID;
/* 15 */     this.agentID = _agentID;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 20 */     return this.agentDeviceID.hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object anObject)
/*    */   {
/* 25 */     if (anObject instanceof TSAgentKey)
/*    */     {
/* 27 */       TSAgentKey anotherAgentKey = (TSAgentKey)anObject;
/*    */ 
/* 29 */       boolean acdDeviceIDMatch = true;
/* 30 */       boolean agentIDMatch = true;
/* 31 */       if ((this.acdDeviceID != null) && (anotherAgentKey.acdDeviceID != null))
/*    */       {
/* 33 */         acdDeviceIDMatch = this.acdDeviceID.equals(anotherAgentKey.acdDeviceID);
/*    */       }
/* 35 */       if ((this.agentID != null) && (anotherAgentKey.agentID != null))
/*    */       {
/* 37 */         agentIDMatch = this.agentID.equals(anotherAgentKey.agentID);
/*    */       }
/* 39 */       return (this.agentDeviceID.equals(anotherAgentKey.agentDeviceID)) && (acdDeviceIDMatch) && (agentIDMatch);
/*    */     }
/*    */ 
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 47 */     return "TSAgentKey(" + this.agentDeviceID + "," + this.acdDeviceID + "," + this.agentID + ")";
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSAgentKey
 * JD-Core Version:    0.5.4
 */