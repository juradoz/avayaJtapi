/*    */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*    */ import java.net.SocketException;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class TsapiLightweightUnsolicitedHandler
/*    */   implements TsapiUnsolicitedHandler
/*    */ {
/* 16 */   private static Logger log = Logger.getLogger(TsapiLightweightUnsolicitedHandler.class);
/*    */   private TsapiSession m_session;
/*    */ 
/*    */   public TsapiLightweightUnsolicitedHandler(TsapiSession sess)
/*    */   {
/* 22 */     this.m_session = sess;
/*    */   }
/*    */ 
/*    */   public void acsUnsolicited(CSTAEvent event)
/*    */   {
/* 29 */     log.info("TsapiLightweightUnsolicitedHandler acsUnsolicited saw unexpected event " + event);
/*    */   }
/*    */ 
/*    */   public void cstaEventReport(CSTAEvent event)
/*    */   {
/* 36 */     log.info("TsapiLightweightUnsolicitedHandler cstaEventReport saw unexpected event " + event);
/*    */   }
/*    */ 
/*    */   public void cstaRequest(CSTAEvent event)
/*    */   {
/* 43 */     log.info("TsapiLightweightUnsolicitedHandler cstaRequest saw unexpected event " + event);
/*    */   }
/*    */ 
/*    */   public void cstaUnsolicited(CSTAEvent event)
/*    */   {
/* 50 */     log.info("TsapiLightweightUnsolicitedHandler cstaUnsolicited saw unexpected event " + event);
/*    */   }
/*    */ 
/*    */   public void eventDistributorException(Exception e)
/*    */   {
/* 57 */     if (e instanceof SocketException)
/*    */     {
/* 61 */       log.info("TsapiLightweightUnsolicitedHandler eventDistributorException: normal 'Socket closed'.");
/*    */     }
/*    */     else
/*    */     {
/* 65 */       log.error("TsapiLightweightUnsolicitedHandler eventDistributorException: unexpected exception received: " + e);
/* 66 */       log.error(e.getMessage(), e);
/*    */     }
/*    */ 
/* 70 */     this.m_session.close();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiLightweightUnsolicitedHandler
 * JD-Core Version:    0.5.4
 */