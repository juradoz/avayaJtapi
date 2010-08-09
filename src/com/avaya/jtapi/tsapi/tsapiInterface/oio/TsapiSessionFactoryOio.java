 package com.avaya.jtapi.tsapi.tsapiInterface.oio;
 
 import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannel;
 import com.avaya.jtapi.tsapi.tsapiInterface.TsapiLightweightUnsolicitedHandler;
 import com.avaya.jtapi.tsapi.tsapiInterface.TsapiSession;
 import com.avaya.jtapi.tsapi.tsapiInterface.TsapiSessionFactory;
 import java.io.IOException;
 import java.net.InetSocketAddress;
 import java.util.Enumeration;
 import java.util.Properties;
 import javax.net.SocketFactory;
 import org.apache.log4j.Logger;
 
 public class TsapiSessionFactoryOio extends TsapiSessionFactory
 {
   private static Logger log = Logger.getLogger(TsapiSessionFactoryOio.class);
   private String debugID;
   private static String trustStoreLocation = null;
 
   private static String trustStorePassword = null;
 
   private static boolean verifyServerCertificate = Boolean.valueOf("false").booleanValue();
 
   public void configure(Properties props)
   {
     Enumeration eprop = props.propertyNames();
 
     while (eprop.hasMoreElements())
     {
       String tsapiProperty = (String)eprop.nextElement();
 
       if (tsapiProperty.equalsIgnoreCase("trustStoreLocation"))
       {
         trustStoreLocation = props.getProperty(tsapiProperty);
 
         if (trustStoreLocation.length() == 0)
         {
           trustStoreLocation = null;
         }
 
         if (trustStoreLocation != null)
         {
           log.info("Property \"trustStoreLocation\" is \"" + trustStoreLocation + "\"");
         }
         else
         {
           log.info("Property \"trustStoreLocation\" is null");
         }
       }
       else if (tsapiProperty.equalsIgnoreCase("trustStorePassword"))
       {
         trustStorePassword = props.getProperty(tsapiProperty);
 
         if (trustStorePassword != null)
         {
           log.info("Property \"trustStorePassword\" is \"****\"");
         }
         else
         {
           log.info("Property \"trustStorePassword\" is null");
         }
       }
       else if (tsapiProperty.equalsIgnoreCase("verifyServerCertificate"))
       {
         String propertyValue = props.getProperty(tsapiProperty, "false");
 
         verifyServerCertificate = Boolean.valueOf(propertyValue).booleanValue();
 
         log.info("Property \"verifyServerCertificate\" is " + verifyServerCertificate);
       }
     }
   }
 
   public void setDebugID(String _debugID)
   {
     this.debugID = _debugID;
   }
 
   public TsapiSession getTsapiSession(InetSocketAddress addr)
     throws IOException
   {
     TsapiChannel channel = new TsapiChannelOio(addr, SocketFactory.getDefault());
 
     return new TsapiSession(channel, true, this.debugID);
   }
 
   public TsapiSession getTsapiSession(InetSocketAddress addr, String tlink)
     throws IOException
   {
     SocketFactory socketFactory;
     if (isSecureTlink(tlink))
     {
       TsapiSSLContext.getInstance(trustStoreLocation, trustStorePassword, verifyServerCertificate);
 
       socketFactory = TsapiSSLContext.getSocketFactory();
     }
     else
     {
       socketFactory = SocketFactory.getDefault();
     }
 
     TsapiChannel channel = new TsapiChannelOio(addr, socketFactory);
 
     return new TsapiSession(channel, true, this.debugID);
   }
 
   public TsapiSession getLightweightTsapiSession(InetSocketAddress addr)
     throws IOException
   {
     log.debug("Attempting to connect to server <" + addr + ">");
     TsapiChannel channel = new TsapiChannelOio(addr, SocketFactory.getDefault());
 
     log.debug("Successfully  connected to server <" + addr + ">");
 
     TsapiSession sess = new TsapiSession(channel, false, this.debugID);
 
     sess.setHandler(new TsapiLightweightUnsolicitedHandler(sess));
 
     return sess;
   }
 
   static boolean isSecureTlink(String tlink)
   {
     String[] tokens = tlink.split("#");
     try
     {
       if (tokens[2].equalsIgnoreCase("CSTA-S"))
       {
         return true;
       }
     }
     catch (ArrayIndexOutOfBoundsException e)
     {
       return false;
     }
 
     return false;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiSessionFactoryOio
 * JD-Core Version:    0.5.4
 */