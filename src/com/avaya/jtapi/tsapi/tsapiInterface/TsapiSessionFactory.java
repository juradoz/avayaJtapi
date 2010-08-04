/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.acs.ACSNameAddr;
/*     */ import com.avaya.jtapi.tsapi.acs.ACSNameSrvReply;
/*     */ import com.avaya.jtapi.tsapi.acs.ACSNameSrvRequest;
/*     */ import com.avaya.jtapi.tsapi.asn1.TsapiRequest;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class TsapiSessionFactory
/*     */ {
/*  54 */   private static Logger log = Logger.getLogger(TsapiSessionFactory.class);
/*     */   public static final String FACTORY_KEY = "com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory.sessionFactory";
/*     */ 
/*     */   public static TsapiSessionFactory getTsapiSessionFactory(Properties props)
/*     */   {
/*  80 */     String className = "com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiSessionFactoryOio";
/*     */ 
/*  82 */     if ((props != null) && (props.get("com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory.sessionFactory") != null)) {
/*  83 */       className = (String)props.get("com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory.sessionFactory");
/*     */     }
/*  85 */     TsapiSessionFactory factory = null;
/*     */     try {
/*  87 */       Class theClass = Class.forName(className);
/*  88 */       factory = (TsapiSessionFactory)theClass.newInstance();
/*     */     } catch (ClassNotFoundException e) {
/*  90 */       throw new RuntimeException("class not found", e);
/*     */     } catch (InstantiationException e) {
/*  92 */       throw new RuntimeException("Could not instantiate", e);
/*     */     } catch (IllegalAccessException e) {
/*  94 */       throw new RuntimeException("Could not access", e);
/*     */     }
/*     */ 
/*  97 */     factory.configure(props);
/*     */ 
/*  99 */     return factory;
/*     */   }
/*     */ 
/*     */   protected abstract void configure(Properties paramProperties);
/*     */ 
/*     */   public abstract TsapiSession getTsapiSession(InetSocketAddress paramInetSocketAddress)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract TsapiSession getTsapiSession(InetSocketAddress paramInetSocketAddress, String paramString)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract TsapiSession getLightweightTsapiSession(InetSocketAddress paramInetSocketAddress)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract void setDebugID(String paramString);
/*     */ 
/*     */   public Vector<ACSNameAddr> enumServices(Vector<InetSocketAddress> servers, boolean useTLinkIP)
/*     */   {
/* 179 */     return enumServices(servers, useTLinkIP, Tsapi.getGetServicesTimeout());
/*     */   }
/*     */ 
/*     */   public Vector<ACSNameAddr> enumServices(Vector<InetSocketAddress> servers, boolean useTLinkIP, int timeout)
/*     */   {
/* 199 */     Enumeration eserv = servers.elements();
/* 200 */     Vector services = new Vector();
/* 201 */     while (eserv.hasMoreElements())
/*     */     {
/*     */       InetSocketAddress addr;
/*     */       try
/*     */       {
/* 207 */         addr = (InetSocketAddress)eserv.nextElement();
/*     */       }
/*     */       catch (NoSuchElementException e)
/*     */       {
/* 211 */         log.error(e.getMessage(), e);
continue;
/* 212 */       }
/*     */ 
/* 215 */       TsapiSession session = null;
/*     */       try
/*     */       {
/* 219 */         session = getLightweightTsapiSession(addr);
/*     */ 
/* 221 */         TsapiRequest req = new ACSNameSrvRequest((short)1);
/*     */ 
/* 225 */         byte[] inetAddr = addr.getAddress().getAddress();
/*     */         ACSNameSrvReply reply;
/*     */         do
/*     */         {
/* 229 */           CSTAEvent event = session.send(req, null, timeout);
/*     */ 
/* 231 */           if (!(event.getEvent() instanceof ACSNameSrvReply))
/*     */           {
/* 233 */             log.info("unexpected reply from name server <" + addr + ">");
/* 234 */             throw new TsapiPlatformException(4, 0, "unexpected reply from name server");
/*     */           }
/*     */ 
/* 237 */           reply = (ACSNameSrvReply)event.getEvent();
/* 238 */           ACSNameAddr[] replyList = reply.getList();
/* 239 */           log.debug("Listing services available on server <" + addr + ">");
/* 240 */           for (int i = 0; i < replyList.length; ++i)
/*     */           {
/* 242 */             byte[] serverAddr = replyList[i].getServerAddr();
/* 243 */             if (!useTLinkIP)
/*     */             {
/* 247 */               serverAddr[4] = inetAddr[0];
/* 248 */               serverAddr[5] = inetAddr[1];
/* 249 */               serverAddr[6] = inetAddr[2];
/* 250 */               serverAddr[7] = inetAddr[3];
/*     */             }
/* 252 */             log.debug("SERVICE[" + i + "]: " + replyList[i].getServerName());
/* 253 */             services.addElement(replyList[i]);
/*     */           }
/*     */         }
/* 255 */         while (reply.isMore());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 260 */         log.error("enumServices() <" + addr + ">: " + e);
/*     */       }
/*     */       finally
/*     */       {
/* 266 */         if (session != null)
/*     */         {
/* 268 */           session.close();
/*     */         }
/*     */       }
/*     */     }
/* 272 */     if (services.size() == 0)
/*     */     {
/* 274 */       log.warn("No valid telephony servers found");
/*     */     }
/* 276 */     return services;
/*     */   }
/*     */ 
/*     */   public ACSNameAddr findTlink(String tlink, Vector<InetSocketAddress> servers, boolean useTLinkIP)
/*     */   {
/* 283 */     TsapiAlternateTlinkEntriesList alternateTlinkEntriesList = TsapiAlternateTlinkEntriesList.Instance();
/*     */ 
/* 285 */     boolean foundAlternate = false;
/* 286 */     int alternateIndex = -1;
/* 287 */     ACSNameAddr alternateACSNameAddr = new ACSNameAddr();
/*     */ 
/* 289 */     Enumeration services = enumServices(servers, useTLinkIP).elements();
/* 290 */     while (services.hasMoreElements())
/*     */     {
/*     */       ACSNameAddr nameAddr;
/*     */       try
/*     */       {
/* 295 */         nameAddr = (ACSNameAddr)services.nextElement();
/*     */       }
/*     */       catch (NoSuchElementException e)
/*     */       {
/* 299 */         log.error(e.getMessage(), e);
continue;
/* 300 */       }
/*     */ 
/* 303 */       if (nameAddr.getServerName().equalsIgnoreCase(tlink))
/*     */       {
/* 305 */         return nameAddr;
/*     */       }
/*     */ 
/* 312 */       int index = alternateTlinkEntriesList.getAlternateTlinkIndex(tlink, nameAddr.getServerName());
/*     */ 
/* 315 */       if ((index >= 0) && ((
/* 318 */         (!foundAlternate) || (index < alternateIndex))))
/*     */       {
/* 323 */         foundAlternate = true;
/* 324 */         alternateIndex = index;
/* 325 */         alternateACSNameAddr = nameAddr;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 330 */     if (foundAlternate == true)
/*     */     {
/* 335 */       return alternateACSNameAddr;
/*     */     }
/*     */ 
/* 338 */     throw new TsapiPlatformException(4, 0, "server " + new ArrayList(servers) + " with tlink '" + tlink + "' not found");
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory
 * JD-Core Version:    0.5.4
 */