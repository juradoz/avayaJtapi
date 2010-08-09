 package com.avaya.jtapi.tsapi.impl.core;
 
 final class TSAgentKey
 {
   String agentDeviceID;
   String acdDeviceID;
   String agentID;
 
   TSAgentKey(String _agentDeviceID, String _acdDeviceID, String _agentID)
   {
     this.agentDeviceID = _agentDeviceID;
     this.acdDeviceID = _acdDeviceID;
     this.agentID = _agentID;
   }
 
   public int hashCode()
   {
     return this.agentDeviceID.hashCode();
   }
 
   public boolean equals(Object anObject)
   {
     if (anObject instanceof TSAgentKey)
     {
       TSAgentKey anotherAgentKey = (TSAgentKey)anObject;
 
       boolean acdDeviceIDMatch = true;
       boolean agentIDMatch = true;
       if ((this.acdDeviceID != null) && (anotherAgentKey.acdDeviceID != null))
       {
         acdDeviceIDMatch = this.acdDeviceID.equals(anotherAgentKey.acdDeviceID);
       }
       if ((this.agentID != null) && (anotherAgentKey.agentID != null))
       {
         agentIDMatch = this.agentID.equals(anotherAgentKey.agentID);
       }
       return (this.agentDeviceID.equals(anotherAgentKey.agentDeviceID)) && (acdDeviceIDMatch) && (agentIDMatch);
     }
 
     return false;
   }
 
   public String toString()
   {
     return "TSAgentKey(" + this.agentDeviceID + "," + this.acdDeviceID + "," + this.agentID + ")";
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSAgentKey
 * JD-Core Version:    0.5.4
 */