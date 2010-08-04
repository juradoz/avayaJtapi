/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASnapshotDeviceResponseInfo extends ASNSequence
/*    */ {
/*    */   CSTAConnectionID callIdentifier;
/*    */   short[] localCallState;
/*    */ 
/*    */   public CSTASnapshotDeviceResponseInfo()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTASnapshotDeviceResponseInfo(CSTAConnectionID _callIdentifier, short[] _localCallState)
/*    */   {
/* 22 */     this.callIdentifier = _callIdentifier;
/* 23 */     this.localCallState = _localCallState;
/*    */   }
/*    */ 
/*    */   public static CSTASnapshotDeviceResponseInfo decode(InputStream in)
/*    */   {
/* 28 */     CSTASnapshotDeviceResponseInfo _this = new CSTASnapshotDeviceResponseInfo();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.callIdentifier = CSTAConnectionID.decode(memberStream);
/* 39 */     this.localCallState = CSTACallState.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 44 */     CSTAConnectionID.encode(this.callIdentifier, memberStream);
/* 45 */     CSTACallState.encode(this.localCallState, memberStream);
/*    */   }
/*    */ 
/*    */   public static Collection<String> print(CSTASnapshotDeviceResponseInfo _this, String name, String _indent)
/*    */   {
/* 50 */     Collection lines = new ArrayList();
/*    */ 
/* 52 */     if (_this == null)
/*    */     {
/* 54 */       lines.add(_indent + name + " <null>");
/* 55 */       return lines;
/*    */     }
/* 57 */     if (name != null) {
/* 58 */       lines.add(_indent + name);
/*    */     }
/* 60 */     lines.add(_indent + "{");
/*    */ 
/* 62 */     String indent = _indent + "  ";
/*    */ 
/* 64 */     lines.addAll(CSTAConnectionID.print(_this.callIdentifier, "callIdentifier", indent));
/* 65 */     lines.addAll(CSTACallState.print(_this.localCallState, "localCallState", indent));
/*    */ 
/* 67 */     lines.add(_indent + "}");
/* 68 */     return lines;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getCallIdentifier()
/*    */   {
/* 75 */     return this.callIdentifier;
/*    */   }
/*    */ 
/*    */   public void setCallIdentifier(CSTAConnectionID callIdentifier) {
/* 79 */     this.callIdentifier = callIdentifier;
/*    */   }
/*    */ 
/*    */   public short[] getLocalCallState()
/*    */   {
/* 86 */     return this.localCallState;
/*    */   }
/*    */ 
/*    */   public void setLocalCallState(short[] localCallState) {
/* 90 */     this.localCallState = new short[localCallState.length];
/* 91 */     System.arraycopy(localCallState, 0, this.localCallState, 0, localCallState.length);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceResponseInfo
 * JD-Core Version:    0.5.4
 */