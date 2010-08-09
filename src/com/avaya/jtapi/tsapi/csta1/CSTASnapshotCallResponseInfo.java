 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTASnapshotCallResponseInfo extends ASNSequence
 {
   CSTAExtendedDeviceID deviceOnCall;
   CSTAConnectionID callIdentifier;
   short localConnectionState;
 
   public CSTASnapshotCallResponseInfo()
   {
   }
 
   public CSTASnapshotCallResponseInfo(CSTAExtendedDeviceID _deviceOnCall, CSTAConnectionID _callIdentifier, short _localConnectionState)
   {
     this.deviceOnCall = _deviceOnCall;
     this.callIdentifier = _callIdentifier;
     this.localConnectionState = _localConnectionState;
   }
 
   public static CSTASnapshotCallResponseInfo decode(InputStream in)
   {
     CSTASnapshotCallResponseInfo _this = new CSTASnapshotCallResponseInfo();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.deviceOnCall = CSTAExtendedDeviceID.decode(memberStream);
     this.callIdentifier = CSTAConnectionID.decode(memberStream);
     this.localConnectionState = LocalConnectionState.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAExtendedDeviceID.encode(this.deviceOnCall, memberStream);
     CSTAConnectionID.encode(this.callIdentifier, memberStream);
     LocalConnectionState.encode(this.localConnectionState, memberStream);
   }
 
   public static Collection<String> print(CSTASnapshotCallResponseInfo _this, String name, String _indent)
   {
     Collection lines = new ArrayList();
 
     if (_this == null)
     {
       lines.add(_indent + name + " <null>");
       return lines;
     }
     if (name != null) {
       lines.add(_indent + name);
     }
     lines.add(_indent + "{");
 
     String indent = _indent + "  ";
 
     lines.addAll(CSTAExtendedDeviceID.print(_this.deviceOnCall, "deviceOnCall", indent));
     lines.addAll(CSTAConnectionID.print(_this.callIdentifier, "callIdentifier", indent));
     lines.addAll(LocalConnectionState.print(_this.localConnectionState, "localConnectionState", indent));
 
     lines.add(_indent + "}");
 
     return lines;
   }
 
   public CSTAConnectionID getCallIdentifier()
   {
     return this.callIdentifier;
   }
 
   public CSTAExtendedDeviceID getDeviceOnCall()
   {
     return this.deviceOnCall;
   }
 
   public short getLocalConnectionState()
   {
     return this.localConnectionState;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallResponseInfo
 * JD-Core Version:    0.5.4
 */