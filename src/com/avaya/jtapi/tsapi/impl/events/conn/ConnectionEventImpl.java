 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
 import javax.telephony.Connection;
 import javax.telephony.ConnectionEvent;
 import javax.telephony.MetaEvent;
 
 public class ConnectionEventImpl extends CallEventImpl
   implements ConnectionEvent
 {
   public ConnectionEventImpl(CallEventParams params, MetaEvent event, int eventId)
   {
     super(params, event, eventId);
   }
 
   public Connection getConnection()
   {
     Connection connection = null;
     if (this.callEventParams instanceof ConnEventParams)
       connection = ((ConnEventParams)this.callEventParams).getConnection();
     return connection;
   }
 
   public Object getSource()
   {
     if (this.callEventParams instanceof ConnEventParams) {
       return ((ConnEventParams)this.callEventParams).getConnection();
     }
     return super.getSource();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.ConnectionEventImpl
 * JD-Core Version:    0.5.4
 */