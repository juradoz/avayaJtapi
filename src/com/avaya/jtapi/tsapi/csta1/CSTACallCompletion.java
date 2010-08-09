 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class CSTACallCompletion extends CSTARequest
 {
   short feature;
   CSTAConnectionID call;
   static final int PDU = 5;
 
   public CSTACallCompletion(short _feature, CSTAConnectionID _call)
   {
     this.feature = _feature;
     this.call = _call;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     Feature.encode(this.feature, memberStream);
     CSTAConnectionID.encode(this.call, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTACallCompletion ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(Feature.print(this.feature, "feature", indent));
     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 5;
   }
 
   public CSTAConnectionID getCall()
   {
     return this.call;
   }
 
   public short getFeature()
   {
     return this.feature;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTACallCompletion
 * JD-Core Version:    0.5.4
 */