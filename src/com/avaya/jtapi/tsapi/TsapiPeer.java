/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.Provider;
/*    */ import javax.telephony.ProviderUnavailableException;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class TsapiPeer
/*    */   implements ITsapiPeer
/*    */ {
/*    */   public static final String VERSION = "5.2.0.483";
/* 22 */   ITsapiPeer impl = null;
/* 23 */   Logger logger = Logger.getLogger(TsapiPeer.class);
/*    */ 
/*    */   public TsapiPeer()
/*    */   {
/*    */     try
/*    */     {
/* 31 */       Class implClass = Class.forName("com.avaya.jtapi.tsapi.impl.TsapiPeerImpl");
/* 32 */       this.impl = ((ITsapiPeer)implClass.newInstance());
/*    */     } catch (Exception e) {
/* 34 */       this.logger.error(e.getMessage(), e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void addVendor(String vendorName, String versions)
/*    */   {
/* 41 */     this.impl.addVendor(vendorName, versions);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 48 */     return super.getClass().getName();
/*    */   }
/*    */ 
/*    */   public Provider getProvider(String providerString) throws ProviderUnavailableException
/*    */   {
/* 53 */     return this.impl.getProvider(providerString);
/*    */   }
/*    */ 
/*    */   public String[] getServices() {
/* 57 */     return this.impl.getServices();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiPeer
 * JD-Core Version:    0.5.4
 */