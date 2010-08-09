 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentSetAdviceOfCharge extends LucentPrivateData
 {
   boolean featureFlag;
   static final int PDU = 99;
 
   public LucentSetAdviceOfCharge(boolean _featureFlag)
   {
     this.featureFlag = _featureFlag;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ASNBoolean.encode(this.featureFlag, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentSetAdviceOfCharge ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNBoolean.print(this.featureFlag, "featureFlag", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 99;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSetAdviceOfCharge
 * JD-Core Version:    0.5.4
 */