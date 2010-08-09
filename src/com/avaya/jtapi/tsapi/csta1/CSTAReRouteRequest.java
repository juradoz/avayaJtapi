 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAReRouteRequest extends CSTARequest
 {
   int routeRegisterReqID;
   int routingCrossRefID;
   public static final int PDU = 85;
 
   public static CSTAReRouteRequest decode(InputStream in)
   {
     CSTAReRouteRequest _this = new CSTAReRouteRequest();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTAReRouteRequest ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 85;
   }
 
   public int getRouteRegisterReqID()
   {
     return this.routeRegisterReqID;
   }
 
   public int getRoutingCrossRefID()
   {
     return this.routingCrossRefID;
   }
 
   public void setRouteRegisterReqID(int routeRegisterReqID) {
     this.routeRegisterReqID = routeRegisterReqID;
   }
 
   public void setRoutingCrossRefID(int routingCrossRefID) {
     this.routingCrossRefID = routingCrossRefID;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAReRouteRequest
 * JD-Core Version:    0.5.4
 */