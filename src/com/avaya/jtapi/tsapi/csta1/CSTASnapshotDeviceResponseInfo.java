 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTASnapshotDeviceResponseInfo extends ASNSequence
 {
   CSTAConnectionID callIdentifier;
   short[] localCallState;
 
   public CSTASnapshotDeviceResponseInfo()
   {
   }
 
   public CSTASnapshotDeviceResponseInfo(CSTAConnectionID _callIdentifier, short[] _localCallState)
   {
     this.callIdentifier = _callIdentifier;
     this.localCallState = _localCallState;
   }
 
   public static CSTASnapshotDeviceResponseInfo decode(InputStream in)
   {
     CSTASnapshotDeviceResponseInfo _this = new CSTASnapshotDeviceResponseInfo();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.callIdentifier = CSTAConnectionID.decode(memberStream);
     this.localCallState = CSTACallState.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.callIdentifier, memberStream);
     CSTACallState.encode(this.localCallState, memberStream);
   }
 
   public static Collection<String> print(CSTASnapshotDeviceResponseInfo _this, String name, String _indent)
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
 
     lines.addAll(CSTAConnectionID.print(_this.callIdentifier, "callIdentifier", indent));
     lines.addAll(CSTACallState.print(_this.localCallState, "localCallState", indent));
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public CSTAConnectionID getCallIdentifier()
   {
     return this.callIdentifier;
   }
 
   public void setCallIdentifier(CSTAConnectionID callIdentifier) {
     this.callIdentifier = callIdentifier;
   }
 
   public short[] getLocalCallState()
   {
     return this.localCallState;
   }
 
   public void setLocalCallState(short[] localCallState) {
     this.localCallState = new short[localCallState.length];
     System.arraycopy(localCallState, 0, this.localCallState, 0, localCallState.length);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceResponseInfo
 * JD-Core Version:    0.5.4
 */