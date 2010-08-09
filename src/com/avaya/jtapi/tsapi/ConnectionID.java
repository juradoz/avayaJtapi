 package com.avaya.jtapi.tsapi;
 
 public final class ConnectionID
 {
   public static final short STATIC_ID = 0;
   public static final short DYNAMIC_ID = 1;
   int callID;
   String deviceID;
   short devIDType;
 
   ConnectionID()
   {
   }
 
   public ConnectionID(int _callID, String _deviceID, short _devIDType)
   {
     this.callID = _callID;
     this.deviceID = _deviceID;
     this.devIDType = _devIDType;
   }
 
   public int hashCode()
   {
     if (this.deviceID == null)
     {
       return this.callID + (this.devIDType << 31);
     }
 
     return this.callID + this.deviceID.hashCode() + (this.devIDType << 31);
   }
 
   public boolean equals(Object anObject)
   {
     if (anObject instanceof ConnectionID)
     {
       ConnectionID anotherConnID = (ConnectionID)anObject;
       if (this.deviceID == null)
       {
         return (this.callID == anotherConnID.callID) && (this.devIDType == anotherConnID.devIDType) && (anotherConnID.deviceID == null);
       }
 
       return (this.callID == anotherConnID.callID) && (this.devIDType == anotherConnID.devIDType) && (this.deviceID.equals(anotherConnID.deviceID));
     }
 
     return false;
   }
 
   public String toString()
   {
     return "ConnectionID(" + this.callID + "," + this.deviceID + "," + this.devIDType + ")";
   }
 
   public String getDeviceID() {
     return this.deviceID;
   }
 
   public int getCallID() {
     return this.callID;
   }
 
   public int getDevIDType() {
     return this.devIDType;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.ConnectionID
 * JD-Core Version:    0.5.4
 */