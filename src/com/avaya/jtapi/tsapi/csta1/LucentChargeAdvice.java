 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentChargeAdvice extends LucentPrivateData
 {
   private CSTAConnectionID connection_asn;
   private String calledDevice_asn;
   private String chargingDevice_asn;
   private String trunkGroup;
   private String trunkMember;
   private short chargeType;
   private int charge;
   private short error;
   static final int PDU = 96;
 
   public final int getCharge()
   {
     return this.charge;
   }
 
   public final short getChargeType()
   {
     return this.chargeType;
   }
 
   public final short getChargeError()
   {
     return this.error;
   }
 
   public static LucentChargeAdvice decode(InputStream in)
   {
     LucentChargeAdvice _this = new LucentChargeAdvice();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.connection_asn = CSTAConnectionID.decode(memberStream);
     this.calledDevice_asn = DeviceID.decode(memberStream);
     this.chargingDevice_asn = DeviceID.decode(memberStream);
     this.trunkGroup = DeviceID.decode(memberStream);
     this.trunkMember = DeviceID.decode(memberStream);
     this.chargeType = ChargeType.decode(memberStream);
     this.charge = ASNInteger.decode(memberStream);
     this.error = ChargeError.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.connection_asn, memberStream);
     DeviceID.encode(this.calledDevice_asn, memberStream);
     DeviceID.encode(this.chargingDevice_asn, memberStream);
     DeviceID.encode(this.trunkGroup, memberStream);
     DeviceID.encode(this.trunkMember, memberStream);
     ChargeType.encode(this.chargeType, memberStream);
     ASNInteger.encode(this.charge, memberStream);
     ChargeError.encode(this.error, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentChargeAdviceEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.connection_asn, "connection", indent));
     lines.addAll(DeviceID.print(this.calledDevice_asn, "calledDevice", indent));
     lines.addAll(DeviceID.print(this.chargingDevice_asn, "chargingDevice", indent));
     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
     lines.addAll(ChargeType.print(this.chargeType, "chargeType", indent));
     lines.addAll(ASNInteger.print(this.charge, "charge", indent));
     lines.addAll(ChargeError.print(this.error, "error", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 96;
   }
 
   public String getCalledDevice_asn()
   {
     return this.calledDevice_asn;
   }
 
   public String getChargingDevice_asn()
   {
     return this.chargingDevice_asn;
   }
 
   public CSTAConnectionID getConnection_asn()
   {
     return this.connection_asn;
   }
 
   public short getError()
   {
     return this.error;
   }
 
   public String getTrunkGroup()
   {
     return this.trunkGroup;
   }
 
   public String getTrunkMember()
   {
     return this.trunkMember;
   }
 
   public void setCalledDevice_asn(String calledDevice_asn) {
     this.calledDevice_asn = calledDevice_asn;
   }
 
   public void setCharge(int charge) {
     this.charge = charge;
   }
 
   public void setChargeType(short chargeType) {
     this.chargeType = chargeType;
   }
 
   public void setChargingDevice_asn(String chargingDevice_asn) {
     this.chargingDevice_asn = chargingDevice_asn;
   }
 
   public void setConnection_asn(CSTAConnectionID connection_asn) {
     this.connection_asn = connection_asn;
   }
 
   public void setError(short error) {
     this.error = error;
   }
 
   public void setTrunkGroup(String trunkGroup) {
     this.trunkGroup = trunkGroup;
   }
 
   public void setTrunkMember(String trunkMember) {
     this.trunkMember = trunkMember;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentChargeAdvice
 * JD-Core Version:    0.5.4
 */