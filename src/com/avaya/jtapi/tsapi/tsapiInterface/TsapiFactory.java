/*    */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class TsapiFactory
/*    */ {
/*    */   private static final String OVERRIDE_IMPL = "tsapi_impl_class_name";
/*    */ 
/*    */   public static Tsapi getTsapi(String tlink, String login, String passwd, Vector<TsapiVendor> vendors, TsapiUnsolicitedHandler handler)
/*    */   {
/* 14 */     Tsapi tsapi = null;
/* 15 */     String className = null;
/*    */ 
/* 17 */     if ((vendors != null) && (!vendors.isEmpty()) && (vendors.get(0) instanceof TsapiVendor))
/*    */     {
/* 19 */       TsapiVendor vendor = (TsapiVendor)vendors.get(0);
/*    */ 
/* 21 */       if (vendor.name.equals("tsapi_impl_class_name"))
/* 22 */         className = vendor.versions;
/*    */       else
/* 24 */         className = Tsapi.class.getName();
/*    */     }
/*    */     else
/*    */     {
/* 28 */       className = Tsapi.class.getName();
/*    */     }
/*    */     try
/*    */     {
/* 32 */       Class theClass = Class.forName(className);
/* 33 */       tsapi = (Tsapi)theClass.newInstance();
/*    */     } catch (ClassNotFoundException e) {
/* 35 */       throw new RuntimeException("Class not found", e);
/*    */     } catch (InstantiationException e) {
/* 37 */       throw new RuntimeException("Could not instantiate", e);
/*    */     } catch (IllegalAccessException e) {
/* 39 */       throw new RuntimeException("Could not access", e);
/*    */     }
/*    */ 
/* 42 */     tsapi.init(tlink, login, passwd, vendors, handler);
/*    */ 
/* 44 */     return tsapi;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiFactory
 * JD-Core Version:    0.5.4
 */