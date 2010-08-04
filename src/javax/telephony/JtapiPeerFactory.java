/*     */ package javax.telephony;
/*     */ 
/*     */ public class JtapiPeerFactory
/*     */ {
/*     */   public static synchronized JtapiPeer getJtapiPeer(String jtapiPeerName)
/*     */     throws JtapiPeerUnavailableException
/*     */   {
/* 114 */     if ((jtapiPeerName == null) || (jtapiPeerName.length() == 0)) {
/* 115 */       jtapiPeerName = getDefaultJtapiPeerName();
/*     */     }
/*     */ 
/* 119 */     if (jtapiPeerName == null) {
/* 120 */       throw new JtapiPeerUnavailableException();
/*     */     }
/*     */     try
/*     */     {
/* 124 */       Class jtapiPeerClass = Class.forName(jtapiPeerName);
/*     */ 
/* 130 */       return (JtapiPeer)jtapiPeerClass.newInstance();
/*     */     }
/*     */     catch (Exception e) {
/* 133 */       String errmsg = "JtapiPeer: " + jtapiPeerName + " could not be instantiated.";
/*     */ 
/* 135 */       throw new JtapiPeerUnavailableException(errmsg);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getDefaultJtapiPeerName()
/*     */   {
/* 151 */     String JtapiPeerName = "DefaultJtapiPeer";
/* 152 */     return JtapiPeerName;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.JtapiPeerFactory
 * JD-Core Version:    0.5.4
 */