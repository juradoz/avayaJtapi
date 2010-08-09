 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentNetworkProgressInfo extends LucentPrivateData
 {
   public static final short PL_USER = 0;
   public static final short PL_PUB_LOCAL = 1;
   public static final short PL_PUB_REMOTE = 4;
   public static final short PL_PRIV_REMOTE = 5;
   public static final short PD_CALL_OFF_ISDN = 1;
   public static final short PD_DEST_NOT_ISDN = 2;
   public static final short PD_ORIG_NOT_ISDN = 3;
   public static final short PD_CALL_ON_ISDN = 4;
   public static final short PD_INBAND = 8;
   public short progressLocation;
   public short progressDescription;
   static final int PDU = 40;
 
   static LucentNetworkProgressInfo decode(InputStream in)
   {
     LucentNetworkProgressInfo _this = new LucentNetworkProgressInfo();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.progressLocation = ProgressLocation.decode(memberStream);
     this.progressDescription = ProgressDescription.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     ProgressLocation.encode(this.progressLocation, memberStream);
     ProgressDescription.encode(this.progressDescription, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("NetworkProgressInfo ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ProgressLocation.print(this.progressLocation, "progressLocation", indent));
     lines.addAll(ProgressDescription.print(this.progressDescription, "progressDescription", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 40;
   }
   public short getProgressDescription() {
     return this.progressDescription;
   }
 
   public void setProgressDescription(short progressDescription) {
     this.progressDescription = progressDescription;
   }
 
   public short getProgressLocation() {
     return this.progressLocation;
   }
 
   public void setProgressLocation(short progressLocation) {
     this.progressLocation = progressLocation;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentNetworkProgressInfo
 * JD-Core Version:    0.5.4
 */