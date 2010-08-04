/*     */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannel;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiLightweightUnsolicitedHandler;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiSession;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import javax.net.SocketFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TsapiSessionFactoryOio extends TsapiSessionFactory
/*     */ {
/*  51 */   private static Logger log = Logger.getLogger(TsapiSessionFactoryOio.class);
/*     */   private String debugID;
/*  62 */   private static String trustStoreLocation = null;
/*     */ 
/*  67 */   private static String trustStorePassword = null;
/*     */ 
/*  73 */   private static boolean verifyServerCertificate = Boolean.valueOf("false").booleanValue();
/*     */ 
/*     */   public void configure(Properties props)
/*     */   {
/*  83 */     Enumeration eprop = props.propertyNames();
/*     */ 
/*  85 */     while (eprop.hasMoreElements())
/*     */     {
/*  87 */       String tsapiProperty = (String)eprop.nextElement();
/*     */ 
/*  89 */       if (tsapiProperty.equalsIgnoreCase("trustStoreLocation"))
/*     */       {
/*  92 */         trustStoreLocation = props.getProperty(tsapiProperty);
/*     */ 
/*  94 */         if (trustStoreLocation.length() == 0)
/*     */         {
/*  96 */           trustStoreLocation = null;
/*     */         }
/*     */ 
/*  99 */         if (trustStoreLocation != null)
/*     */         {
/* 101 */           log.info("Property \"trustStoreLocation\" is \"" + trustStoreLocation + "\"");
/*     */         }
/*     */         else
/*     */         {
/* 105 */           log.info("Property \"trustStoreLocation\" is null");
/*     */         }
/*     */       }
/* 108 */       else if (tsapiProperty.equalsIgnoreCase("trustStorePassword"))
/*     */       {
/* 111 */         trustStorePassword = props.getProperty(tsapiProperty);
/*     */ 
/* 113 */         if (trustStorePassword != null)
/*     */         {
/* 115 */           log.info("Property \"trustStorePassword\" is \"****\"");
/*     */         }
/*     */         else
/*     */         {
/* 119 */           log.info("Property \"trustStorePassword\" is null");
/*     */         }
/*     */       }
/* 122 */       else if (tsapiProperty.equalsIgnoreCase("verifyServerCertificate"))
/*     */       {
/* 125 */         String propertyValue = props.getProperty(tsapiProperty, "false");
/*     */ 
/* 128 */         verifyServerCertificate = Boolean.valueOf(propertyValue).booleanValue();
/*     */ 
/* 131 */         log.info("Property \"verifyServerCertificate\" is " + verifyServerCertificate);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDebugID(String _debugID)
/*     */   {
/* 145 */     this.debugID = _debugID;
/*     */   }
/*     */ 
/*     */   public TsapiSession getTsapiSession(InetSocketAddress addr)
/*     */     throws IOException
/*     */   {
/* 158 */     TsapiChannel channel = new TsapiChannelOio(addr, SocketFactory.getDefault());
/*     */ 
/* 163 */     return new TsapiSession(channel, true, this.debugID);
/*     */   }
/*     */ 
/*     */   public TsapiSession getTsapiSession(InetSocketAddress addr, String tlink)
/*     */     throws IOException
/*     */   {
/*     */     SocketFactory socketFactory;
/* 183 */     if (isSecureTlink(tlink))
/*     */     {
/* 185 */       TsapiSSLContext.getInstance(trustStoreLocation, trustStorePassword, verifyServerCertificate);
/*     */ 
/* 187 */       socketFactory = TsapiSSLContext.getSocketFactory();
/*     */     }
/*     */     else
/*     */     {
/* 191 */       socketFactory = SocketFactory.getDefault();
/*     */     }
/*     */ 
/* 194 */     TsapiChannel channel = new TsapiChannelOio(addr, socketFactory);
/*     */ 
/* 198 */     return new TsapiSession(channel, true, this.debugID);
/*     */   }
/*     */ 
/*     */   public TsapiSession getLightweightTsapiSession(InetSocketAddress addr)
/*     */     throws IOException
/*     */   {
/* 212 */     log.debug("Attempting to connect to server <" + addr + ">");
/* 213 */     TsapiChannel channel = new TsapiChannelOio(addr, SocketFactory.getDefault());
/*     */ 
/* 215 */     log.debug("Successfully  connected to server <" + addr + ">");
/*     */ 
/* 219 */     TsapiSession sess = new TsapiSession(channel, false, this.debugID);
/*     */ 
/* 221 */     sess.setHandler(new TsapiLightweightUnsolicitedHandler(sess));
/*     */ 
/* 224 */     return sess;
/*     */   }
/*     */ 
/*     */   static boolean isSecureTlink(String tlink)
/*     */   {
/* 238 */     String[] tokens = tlink.split("#");
/*     */     try
/*     */     {
/* 243 */       if (tokens[2].equalsIgnoreCase("CSTA-S"))
/*     */       {
/* 245 */         return true;
/*     */       }
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/* 250 */       return false;
/*     */     }
/*     */ 
/* 253 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiSessionFactoryOio
 * JD-Core Version:    0.5.4
 */