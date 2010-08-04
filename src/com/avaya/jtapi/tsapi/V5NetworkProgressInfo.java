/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.callcenter.CallCenterTrunk;
/*    */ 
/*    */ public class V5NetworkProgressInfo extends NetworkProgressInfo
/*    */ {
/*    */   String trunkGroup;
/*    */   String trunkMember;
/*    */   public CallCenterTrunk trunk;
/*    */ 
/*    */   V5NetworkProgressInfo(String _trunkGroup, String _trunkMember, TsapiTrunk _trunk, short progressLocation, short progressDescription)
/*    */   {
/* 46 */     super(progressLocation, progressDescription);
/* 47 */     this.trunkGroup = _trunkGroup;
/* 48 */     this.trunkMember = _trunkMember;
/* 49 */     this.trunk = _trunk;
/*    */   }
/*    */ 
/*    */   public V5NetworkProgressInfo()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setTrunkGroup(String trunkGroup)
/*    */   {
/* 61 */     this.trunkGroup = trunkGroup;
/*    */   }
/*    */ 
/*    */   public void setTrunkMember(String trunkMember)
/*    */   {
/* 69 */     this.trunkMember = trunkMember;
/*    */   }
/*    */ 
/*    */   public void setTsapiTrunk(TsapiTrunk trunk)
/*    */   {
/* 77 */     this.trunk = trunk;
/*    */   }
/*    */ 
/*    */   String getTrunkGroup()
/*    */   {
/* 84 */     return this.trunkGroup;
/*    */   }
/*    */ 
/*    */   String getTrunkMember()
/*    */   {
/* 92 */     return this.trunkMember;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.V5NetworkProgressInfo
 * JD-Core Version:    0.5.4
 */