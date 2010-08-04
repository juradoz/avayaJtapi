/*     */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.security.KeyStore;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ final class TsapiSSLContext
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(TsapiSSLContext.class);
/*     */ 
/*  42 */   private static TsapiSSLContext instance = null;
/*     */ 
/*  44 */   private static KeyStore serverTrustStore = null;
/*  45 */   private static SSLContext sslContext = null;
/*  46 */   private static TrustManagerFactory trustManagerFactory = null;
/*  47 */   private static boolean verifyServerCertificate = false;
/*     */ 
/*     */   public static synchronized TsapiSSLContext getInstance(String trustStoreLocation, String trustStorePassword, boolean verifyServerCertificate)
/*     */   {
/*  69 */     if (instance == null)
/*     */     {
/*     */       try
/*     */       {
/*  73 */         instance = new TsapiSSLContext(trustStoreLocation, trustStorePassword);
/*     */ 
/*  75 */         verifyServerCertificate = verifyServerCertificate;
/*     */       }
/*     */       catch (TsapiPlatformException e)
/*     */       {
/*  80 */         throw e;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  84 */         throw new TsapiPlatformException(4, 0, "Could not initialize SSL context. " + e.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  90 */     return instance;
/*     */   }
/*     */ 
/*     */   private TsapiSSLContext(String trustStoreLocation, String trustStorePassword)
/*     */   {
/* 104 */     SecureRandom secureRandom = new SecureRandom();
/* 105 */     secureRandom.nextInt();
/*     */ 
/* 120 */     if (trustStoreLocation != null)
/*     */     {
/* 122 */       validateTrustStoreLocation(trustStoreLocation);
/* 123 */       setupServerTrustStore(trustStoreLocation, trustStorePassword);
/*     */     }
/*     */     else
/*     */     {
/* 128 */       setupServerTrustStore("avayaprca.jks", "password");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 134 */       trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 138 */       throw new TsapiPlatformException(4, 0, "TrustManagerFactory.getInstance() failed. " + e.getMessage());
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 147 */       trustManagerFactory.init(serverTrustStore);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 151 */       throw new TsapiPlatformException(4, 0, "TrustManagerFactory.init() failed. " + e.getMessage());
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 159 */       sslContext = SSLContext.getInstance("TLS");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 163 */       throw new TsapiPlatformException(4, 0, "SSLContext.getInstance() failed. " + e.getMessage());
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 171 */       sslContext.init(null, trustManagerFactory.getTrustManagers(), secureRandom);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 176 */       throw new TsapiPlatformException(4, 0, "SSLContext.init() failed. " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static File findTrustStore(String tsFileName)
/*     */     throws TsapiPlatformException
/*     */   {
/*     */     try
/*     */     {
/* 202 */       File tsFile = new File(tsFileName);
/* 203 */       if (tsFile.isAbsolute())
/*     */       {
/* 206 */         if (tsFile.canRead())
/*     */         {
/* 208 */           log.info("Found trust store \"" + tsFileName + "\"");
/*     */ 
/* 210 */           return tsFile.getCanonicalFile();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 218 */         StringTokenizer classpath = new StringTokenizer(System.getProperty("java.class.path"), System.getProperty("path.separator"));
/*     */         do
/*     */         {
/* 222 */           if (!classpath.hasMoreTokens())
/*     */             break label138;
/* 224 */           tsFile = new File(classpath.nextToken(), tsFileName);
/* 225 */         }while (!tsFile.canRead());
/*     */ 
/* 227 */         log.info("Classpath search for trust store \"" + tsFileName + "\" succeeded");
/*     */ 
/* 229 */         label138: return tsFile.getCanonicalFile();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 238 */     throw new TsapiPlatformException(4, 0, "Couldn't find trust store \"" + tsFileName + "\", or read access not permitted.");
/*     */   }
/*     */ 
/*     */   private static void setupServerTrustStore(String trustStoreFile, String trustStorePassword)
/*     */     throws TsapiPlatformException
/*     */   {
/* 255 */     File jksFile = null;
/*     */     try
/*     */     {
/* 260 */       jksFile = findTrustStore(trustStoreFile);
/*     */ 
/* 262 */       log.info("Using trust store file \"" + jksFile + "\"");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 266 */       throw new TsapiPlatformException(4, 0, "Couldn't find trust store file \"" + trustStoreFile + "\".");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 274 */       FileInputStream jksInputStream = new FileInputStream(jksFile);
/*     */ 
/* 276 */       serverTrustStore = KeyStore.getInstance("JKS");
/* 277 */       serverTrustStore.load(jksInputStream, trustStorePassword.toCharArray());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 282 */       serverTrustStore = null;
/* 283 */       throw new TsapiPlatformException(4, 0, "Couldn't set up server trust store \"" + trustStoreFile + "\". " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void validateTrustStoreLocation(String trustStoreLocation)
/*     */   {
/* 298 */     if (trustStoreLocation == null)
/*     */       return;
/*     */     try
/*     */     {
/* 302 */       File file = new File(trustStoreLocation);
/*     */ 
/* 304 */       if (!file.isAbsolute())
/*     */       {
/* 306 */         throw new TsapiPlatformException(4, 0, "Property setting \"trustStoreLocation=" + trustStoreLocation + "\" does not specify an absolute path.");
/*     */       }
/*     */ 
/* 313 */       if (!file.isFile())
/*     */       {
/* 315 */         throw new TsapiPlatformException(4, 0, "Property setting \"trustStoreLocation=" + trustStoreLocation + "\" does not specify a normal file.");
/*     */       }
/*     */ 
/* 322 */       if (!file.canRead())
/*     */       {
/* 324 */         throw new TsapiPlatformException(4, 0, "Property setting \"trustStoreLocation=" + trustStoreLocation + "\" does not specify a readable file.");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (NullPointerException e)
/*     */     {
/* 333 */       throw new TsapiPlatformException(4, 0, "Property setting \"trustStoreLocation=" + trustStoreLocation + "\" is not valid. " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean getVerifyServerCertificate()
/*     */   {
/* 347 */     return verifyServerCertificate;
/*     */   }
/*     */ 
/*     */   public static TrustManager[] getTrustManagers()
/*     */   {
/* 356 */     if (trustManagerFactory == null)
/*     */     {
/* 358 */       return null;
/*     */     }
/* 360 */     return trustManagerFactory.getTrustManagers();
/*     */   }
/*     */ 
/*     */   public static SSLSocketFactory getSocketFactory()
/*     */   {
/* 368 */     if (sslContext == null)
/*     */     {
/* 370 */       return null;
/*     */     }
/* 372 */     return sslContext.getSocketFactory();
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiSSLContext
 * JD-Core Version:    0.5.4
 */