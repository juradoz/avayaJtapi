 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
 import javax.telephony.Connection;
 
 public class ConnEventParams extends CallEventParams
 {
   private Connection connection;
 
   public ConnEventParams()
   {
     this.connection = null;
   }
   public Connection getConnection() {
     return this.connection;
   }
   public void setConnection(Connection connection) {
     this.connection = connection;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.ConnEventParams
 * JD-Core Version:    0.5.4
 */