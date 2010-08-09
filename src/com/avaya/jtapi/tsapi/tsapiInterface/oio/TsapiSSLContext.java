 package com.avaya.jtapi.tsapi.tsapiInterface.oio;
 
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import java.io.File;
 import java.io.FileInputStream;
 import java.security.KeyStore;
 import java.security.SecureRandom;
 import java.util.StringTokenizer;
 import javax.net.ssl.SSLContext;
 import javax.net.ssl.SSLSocketFactory;
 import javax.net.ssl.TrustManager;
 import javax.net.ssl.TrustManagerFactory;
 import org.apache.log4j.Logger;
 
 final class TsapiSSLContext
 {
   private static Logger log = Logger.getLogger(TsapiSSLContext.class);
 
   private static TsapiSSLContext instance = null;
 
   private static KeyStore serverTrustStore = null;
   private static SSLContext sslContext = null;
   private static TrustManagerFactory trustManagerFactory = null;
   private static boolean verifyServerCertificate = false;
 
   public static synchronized TsapiSSLContext getInstance(String trustStoreLocation, String trustStorePassword, boolean verifyServerCertificate)
   {
     if (instance == null)
     {
       try
       {
         instance = new TsapiSSLContext(trustStoreLocation, trustStorePassword);
 
         verifyServerCertificate = verifyServerCertificate;
       }
       catch (TsapiPlatformException e)
       {
         throw e;
       }
       catch (Exception e)
       {
         throw new TsapiPlatformException(4, 0, "Could not initialize SSL context. " + e.getMessage());
       }
 
     }
 
     return instance;
   }
 
   private TsapiSSLContext(String trustStoreLocation, String trustStorePassword)
   {
     SecureRandom secureRandom = new SecureRandom();
     secureRandom.nextInt();
 
     if (trustStoreLocation != null)
     {
       validateTrustStoreLocation(trustStoreLocation);
       setupServerTrustStore(trustStoreLocation, trustStorePassword);
     }
     else
     {
       setupServerTrustStore("avayaprca.jks", "password");
     }
 
     try
     {
       trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
     }
     catch (Exception e)
     {
       throw new TsapiPlatformException(4, 0, "TrustManagerFactory.getInstance() failed. " + e.getMessage());
     }
 
     try
     {
       trustManagerFactory.init(serverTrustStore);
     }
     catch (Exception e)
     {
       throw new TsapiPlatformException(4, 0, "TrustManagerFactory.init() failed. " + e.getMessage());
     }
 
     try
     {
       sslContext = SSLContext.getInstance("TLS");
     }
     catch (Exception e)
     {
       throw new TsapiPlatformException(4, 0, "SSLContext.getInstance() failed. " + e.getMessage());
     }
 
     try
     {
       sslContext.init(null, trustManagerFactory.getTrustManagers(), secureRandom);
     }
     catch (Exception e)
     {
       throw new TsapiPlatformException(4, 0, "SSLContext.init() failed. " + e.getMessage());
     }
   }
 
   private static File findTrustStore(String tsFileName)
     throws TsapiPlatformException
   {
     try
     {
       File tsFile = new File(tsFileName);
       if (tsFile.isAbsolute())
       {
         if (tsFile.canRead())
         {
           log.info("Found trust store \"" + tsFileName + "\"");
 
           return tsFile.getCanonicalFile();
         }
 
       }
       else
       {
         StringTokenizer classpath = new StringTokenizer(System.getProperty("java.class.path"), System.getProperty("path.separator"));
         do
         {
           if (!classpath.hasMoreTokens())
//             break label138;
	break;
           tsFile = new File(classpath.nextToken(), tsFileName);
         }while (!tsFile.canRead());
 
         log.info("Classpath search for trust store \"" + tsFileName + "\" succeeded");
 
         label138: return tsFile.getCanonicalFile();
       }
 
     }
     catch (Exception e)
     {
     }
 
     throw new TsapiPlatformException(4, 0, "Couldn't find trust store \"" + tsFileName + "\", or read access not permitted.");
   }
 
   private static void setupServerTrustStore(String trustStoreFile, String trustStorePassword)
     throws TsapiPlatformException
   {
     File jksFile = null;
     try
     {
       jksFile = findTrustStore(trustStoreFile);
 
       log.info("Using trust store file \"" + jksFile + "\"");
     }
     catch (Exception e)
     {
       throw new TsapiPlatformException(4, 0, "Couldn't find trust store file \"" + trustStoreFile + "\".");
     }
 
     try
     {
       FileInputStream jksInputStream = new FileInputStream(jksFile);
 
       serverTrustStore = KeyStore.getInstance("JKS");
       serverTrustStore.load(jksInputStream, trustStorePassword.toCharArray());
     }
     catch (Exception e)
     {
       serverTrustStore = null;
       throw new TsapiPlatformException(4, 0, "Couldn't set up server trust store \"" + trustStoreFile + "\". " + e.getMessage());
     }
   }
 
   private static void validateTrustStoreLocation(String trustStoreLocation)
   {
     if (trustStoreLocation == null)
       return;
     try
     {
       File file = new File(trustStoreLocation);
 
       if (!file.isAbsolute())
       {
         throw new TsapiPlatformException(4, 0, "Property setting \"trustStoreLocation=" + trustStoreLocation + "\" does not specify an absolute path.");
       }
 
       if (!file.isFile())
       {
         throw new TsapiPlatformException(4, 0, "Property setting \"trustStoreLocation=" + trustStoreLocation + "\" does not specify a normal file.");
       }
 
       if (!file.canRead())
       {
         throw new TsapiPlatformException(4, 0, "Property setting \"trustStoreLocation=" + trustStoreLocation + "\" does not specify a readable file.");
       }
 
     }
     catch (NullPointerException e)
     {
       throw new TsapiPlatformException(4, 0, "Property setting \"trustStoreLocation=" + trustStoreLocation + "\" is not valid. " + e.getMessage());
     }
   }
 
   public static boolean getVerifyServerCertificate()
   {
     return verifyServerCertificate;
   }
 
   public static TrustManager[] getTrustManagers()
   {
     if (trustManagerFactory == null)
     {
       return null;
     }
     return trustManagerFactory.getTrustManagers();
   }
 
   public static SSLSocketFactory getSocketFactory()
   {
     if (sslContext == null)
     {
       return null;
     }
     return sslContext.getSocketFactory();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiSSLContext
 * JD-Core Version:    0.5.4
 */