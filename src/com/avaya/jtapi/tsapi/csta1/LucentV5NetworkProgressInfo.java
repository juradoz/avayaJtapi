 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentV5NetworkProgressInfo extends LucentNetworkProgressInfo
 {
   String trunkGroup;
   String trunkMember;
   public static final int PDU = 101;
 
   public static LucentNetworkProgressInfo decode(InputStream in)
   {
     LucentV5NetworkProgressInfo _this = new LucentV5NetworkProgressInfo();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.trunkGroup = DeviceID.decode(memberStream);
     this.trunkMember = DeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     super.encodeMembers(memberStream);
     DeviceID.encode(this.trunkGroup, memberStream);
     DeviceID.encode(this.trunkMember, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("V5NetworkProgressInfo ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ProgressLocation.print(this.progressLocation, "progressLocation", indent));
 
     lines.addAll(ProgressDescription.print(this.progressDescription, "progressDescription", indent));
 
     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 101;
   }
 
   public String getTrunkGroup()
   {
     return this.trunkGroup;
   }
 
   public String getTrunkMember()
   {
     return this.trunkMember;
   }
 
   public void setTrunkGroup(String trunkGroup) {
     this.trunkGroup = trunkGroup;
   }
 
   public void setTrunkMember(String trunkMember) {
     this.trunkMember = trunkMember;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5NetworkProgressInfo
 * JD-Core Version:    0.5.4
 */