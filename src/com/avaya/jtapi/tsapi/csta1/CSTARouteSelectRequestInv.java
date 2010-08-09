 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTARouteSelectRequestInv extends CSTARequest
 {
   int routeRegisterReqID;
   int routingCrossRefID;
   String routeSelected;
   int remainRetry;
   byte[] setupInformation;
   boolean routeUsedReq;
   public static final int PDU = 132;
 
   public CSTARouteSelectRequestInv()
   {
   }
 
   public CSTARouteSelectRequestInv(int _routeRegisterReqID, int _routingCrossRefID, String _routeSelected, int _remainRetry, byte[] _setupInformation, boolean _routeUsedReq)
   {
     this.routeRegisterReqID = _routeRegisterReqID;
     this.routingCrossRefID = _routingCrossRefID;
     this.routeSelected = _routeSelected;
     this.remainRetry = _remainRetry;
     this.setupInformation = _setupInformation;
     this.routeUsedReq = _routeUsedReq;
   }
 
   public static CSTARouteSelectRequestInv decode(InputStream in)
   {
     CSTARouteSelectRequestInv _this = new CSTARouteSelectRequestInv();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
     this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
     this.routeSelected = DeviceID.decode(memberStream);
     this.remainRetry = RetryValue.decode(memberStream);
     this.setupInformation = SetUpValues.decode(memberStream);
     this.routeUsedReq = ASNBoolean.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
     RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
     DeviceID.encode(this.routeSelected, memberStream);
     RetryValue.encode(this.remainRetry, memberStream);
     SetUpValues.encode(this.setupInformation, memberStream);
     ASNBoolean.encode(this.routeUsedReq, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTARouteSelectRequestInv ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID, "routeRegisterReqID", indent));
     lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID, "routingCrossRefID", indent));
     lines.addAll(DeviceID.print(this.routeSelected, "routeSelected", indent));
     lines.addAll(RetryValue.print(this.remainRetry, "remainRetry", indent));
     lines.addAll(SetUpValues.print(this.setupInformation, "setupInformation", indent));
     lines.addAll(ASNBoolean.print(this.routeUsedReq, "routeUsedReq", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 132;
   }
 
   public int getRemainRetry()
   {
     return this.remainRetry;
   }
 
   public int getRouteRegisterReqID()
   {
     return this.routeRegisterReqID;
   }
 
   public String getRouteSelected()
   {
     return this.routeSelected;
   }
 
   public boolean isRouteUsedReq()
   {
     return this.routeUsedReq;
   }
 
   public int getRoutingCrossRefID()
   {
     return this.routingCrossRefID;
   }
 
   public byte[] getSetupInformation()
   {
     return this.setupInformation;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARouteSelectRequestInv
 * JD-Core Version:    0.5.4
 */