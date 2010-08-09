 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTARouteRegisterAbortEventReport extends CSTAEventReport
 {
   int routeRegisterReqID;
   public static final int PDU = 82;
 
   public static CSTARouteRegisterAbortEventReport decode(InputStream in)
   {
     CSTARouteRegisterAbortEventReport _this = new CSTARouteRegisterAbortEventReport();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
   }
 
   public Collection<String> print() {
     Collection lines = new ArrayList();
 
     lines.add("CSTARouteRegisterAbortEventReport ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 82;
   }
 
   public int getRouteRegisterReqID()
   {
     return this.routeRegisterReqID;
   }
 
   public void setRouteRegisterReqID(int routeRegisterReqID) {
     this.routeRegisterReqID = routeRegisterReqID;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterAbortEventReport
 * JD-Core Version:    0.5.4
 */