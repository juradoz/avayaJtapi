/*    */ package com.avaya.jtapi.tsapi.impl.core;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*    */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public abstract class AbstractTsapiPeer
/*    */ {
/*    */   protected Vector<TsapiVendor> vendors;
/*    */ 
/*    */   public AbstractTsapiPeer()
/*    */   {
/* 11 */     this.vendors = null;
/*    */   }
/*    */   public void addVendor(String name, String versions) {
/* 14 */     if (this.vendors == null)
/* 15 */       this.vendors = new Vector();
/* 16 */     this.vendors.addElement(new TsapiVendor(name, versions));
/*    */   }
/*    */ 
/*    */   public String[] getServices() {
/* 20 */     return Tsapi.getServices();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.AbstractTsapiPeer
 * JD-Core Version:    0.5.4
 */