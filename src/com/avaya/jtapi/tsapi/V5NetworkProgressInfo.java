 package com.avaya.jtapi.tsapi;
 
 import javax.telephony.callcenter.CallCenterTrunk;
 
 public class V5NetworkProgressInfo extends NetworkProgressInfo
 {
   String trunkGroup;
   String trunkMember;
   public CallCenterTrunk trunk;
 
   V5NetworkProgressInfo(String _trunkGroup, String _trunkMember, TsapiTrunk _trunk, short progressLocation, short progressDescription)
   {
     super(progressLocation, progressDescription);
     this.trunkGroup = _trunkGroup;
     this.trunkMember = _trunkMember;
     this.trunk = _trunk;
   }
 
   public V5NetworkProgressInfo()
   {
   }
 
   public void setTrunkGroup(String trunkGroup)
   {
     this.trunkGroup = trunkGroup;
   }
 
   public void setTrunkMember(String trunkMember)
   {
     this.trunkMember = trunkMember;
   }
 
   public void setTsapiTrunk(TsapiTrunk trunk)
   {
     this.trunk = trunk;
   }
 
   String getTrunkGroup()
   {
     return this.trunkGroup;
   }
 
   String getTrunkMember()
   {
     return this.trunkMember;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.V5NetworkProgressInfo
 * JD-Core Version:    0.5.4
 */