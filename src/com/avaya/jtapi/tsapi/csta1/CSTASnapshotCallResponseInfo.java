/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASnapshotCallResponseInfo extends ASNSequence
/*    */ {
/*    */   CSTAExtendedDeviceID deviceOnCall;
/*    */   CSTAConnectionID callIdentifier;
/*    */   short localConnectionState;
/*    */ 
/*    */   public CSTASnapshotCallResponseInfo()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTASnapshotCallResponseInfo(CSTAExtendedDeviceID _deviceOnCall, CSTAConnectionID _callIdentifier, short _localConnectionState)
/*    */   {
/* 24 */     this.deviceOnCall = _deviceOnCall;
/* 25 */     this.callIdentifier = _callIdentifier;
/* 26 */     this.localConnectionState = _localConnectionState;
/*    */   }
/*    */ 
/*    */   public static CSTASnapshotCallResponseInfo decode(InputStream in)
/*    */   {
/* 31 */     CSTASnapshotCallResponseInfo _this = new CSTASnapshotCallResponseInfo();
/* 32 */     _this.doDecode(in);
/*    */ 
/* 34 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 41 */     this.deviceOnCall = CSTAExtendedDeviceID.decode(memberStream);
/* 42 */     this.callIdentifier = CSTAConnectionID.decode(memberStream);
/* 43 */     this.localConnectionState = LocalConnectionState.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 48 */     CSTAExtendedDeviceID.encode(this.deviceOnCall, memberStream);
/* 49 */     CSTAConnectionID.encode(this.callIdentifier, memberStream);
/* 50 */     LocalConnectionState.encode(this.localConnectionState, memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTASnapshotCallResponseInfo _this, String name, String _indent)
/*    */   {
/* 55 */     Collection lines = new ArrayList();
/*    */ 
/* 57 */     if (_this == null)
/*    */     {
/* 59 */       lines.add(_indent + name + " <null>");
/* 60 */       return lines;
/*    */     }
/* 62 */     if (name != null) {
/* 63 */       lines.add(_indent + name);
/*    */     }
/* 65 */     lines.add(_indent + "{");
/*    */ 
/* 67 */     String indent = _indent + "  ";
/*    */ 
/* 69 */     lines.addAll(CSTAExtendedDeviceID.print(_this.deviceOnCall, "deviceOnCall", indent));
/* 70 */     lines.addAll(CSTAConnectionID.print(_this.callIdentifier, "callIdentifier", indent));
/* 71 */     lines.addAll(LocalConnectionState.print(_this.localConnectionState, "localConnectionState", indent));
/*    */ 
/* 73 */     lines.add(_indent + "}");
/*    */ 
/* 75 */     return lines;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getCallIdentifier()
/*    */   {
/* 82 */     return this.callIdentifier;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getDeviceOnCall()
/*    */   {
/* 90 */     return this.deviceOnCall;
/*    */   }
/*    */ 
/*    */   public short getLocalConnectionState()
/*    */   {
/* 98 */     return this.localConnectionState;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallResponseInfo
 * JD-Core Version:    0.5.4
 */