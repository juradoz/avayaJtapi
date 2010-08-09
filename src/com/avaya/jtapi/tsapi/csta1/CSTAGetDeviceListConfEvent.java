 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTAGetDeviceListConfEvent extends CSTAConfirmation
 {
   short driverSdbLevel;
   short level;
   int index;
   String[] devList;
   public static final int PDU = 127;
   public static final int NO_SDB_CHECKING = -1;
   public static final int ACS_ONLY = 1;
 
   public static CSTAGetDeviceListConfEvent decode(InputStream in)
   {
     CSTAGetDeviceListConfEvent _this = new CSTAGetDeviceListConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     SDBLevel.encode(this.driverSdbLevel, memberStream);
     CSTALevel.encode(this.level, memberStream);
     ASNInteger.encode(this.index, memberStream);
     DeviceList.encode(this.devList, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.driverSdbLevel = SDBLevel.decode(memberStream);
     this.level = CSTALevel.decode(memberStream);
     this.index = ASNInteger.decode(memberStream);
     this.devList = DeviceList.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTAGetDeviceListConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(SDBLevel.print(this.driverSdbLevel, "driverSdbLevel", indent));
     lines.addAll(CSTALevel.print(this.level, "level", indent));
     lines.addAll(ASNInteger.print(this.index, "index", indent));
     lines.addAll(DeviceList.print(this.devList, "devList", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 127;
   }
 
   public String[] getDevList()
   {
     return this.devList;
   }
   public void setDevList(String[] devList) {
     this.devList = devList;
   }
 
   public short getDriverSdbLevel()
   {
     return this.driverSdbLevel;
   }
   public void setDriverSdbLevel(short driverSdbLevel) {
     this.driverSdbLevel = driverSdbLevel;
   }
 
   public int getIndex()
   {
     return this.index;
   }
   public void setIndex(int index) {
     this.index = index;
   }
 
   public short getLevel()
   {
     return this.level;
   }
   public void setLevel(short level) {
     this.level = level;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceListConfEvent
 * JD-Core Version:    0.5.4
 */