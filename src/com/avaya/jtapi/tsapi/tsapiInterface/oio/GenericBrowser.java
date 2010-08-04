/*     */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*     */ 
/*     */ import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.util.JtapiUtils;
/*     */ 
/*     */ public class GenericBrowser
/*     */ {
/*  45 */   private static Logger log = Logger.getLogger(GenericBrowser.class);
/*     */   private String name;
/*  50 */   private static int SSL_HANDSHAKE_TIMEOUT = 5;
/*     */   public static final String SYSTEM_PROPERTIES_PREFIX = "com.avaya.jtapi.tsapi.";
/*     */   public static final String TELEPHONY_SERVERS_SYSTEM_PROPERTY = "com.avaya.jtapi.tsapi.servers";
/*  58 */   private boolean startUp = true;
/*     */   private String tsapiProLocation;
/*     */   private Properties sysSnapshot;
/*     */ 
/*     */   GenericBrowser()
/*     */   {
/*  67 */     this.name = "GENERIC";
/*     */   }
/*     */ 
/*     */   GenericBrowser(String _name)
/*     */   {
/*  72 */     this.name = _name;
/*     */   }
/*     */ 
/*     */   Socket trySocket(InetSocketAddress addr, SocketFactory sf)
/*     */     throws UnknownHostException, IOException
/*     */   {
/*  79 */     Socket socket = sf.createSocket();
/*     */ 
/*  81 */     TsapiHandshakeCompletedListener tsapiHandshakeCompletedListener = null;
/*     */ 
/*  84 */     if (socket instanceof SSLSocket)
/*     */     {
/*  86 */       SSLSocket sslSocket = (SSLSocket)socket;
/*     */ 
/*  88 */       setSslSocketProperties(sslSocket);
/*     */ 
/*  93 */       if (TsapiSSLContext.getVerifyServerCertificate())
/*     */       {
/*     */         try
/*     */         {
/*  97 */           tsapiHandshakeCompletedListener = new TsapiHandshakeCompletedListener();
/*     */ 
/*  99 */           sslSocket.addHandshakeCompletedListener(tsapiHandshakeCompletedListener);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 103 */           throw new TsapiPlatformException(4, 0, "Couldn't register HandshakeCompletedListener for secure connection.  SSLSocket.addHandshakeCompletedListener() failed; " + e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 120 */     int timeout = Tsapi.getMaxTcpSocketWait();
/*     */ 
/* 122 */     if ((timeout <= 0) || (timeout > 120))
/*     */     {
/* 124 */       timeout = 20;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 130 */       socket.connect(addr, timeout * 1000);
/*     */     }
/*     */     catch (SocketTimeoutException e)
/*     */     {
/* 134 */       throw new TsapiPlatformException(4, 0, "Couldn't connect to server " + addr + ". Socket.connect() timed out after " + timeout + " seconds.");
/*     */     }
/*     */ 
/* 142 */     if ((socket instanceof SSLSocket) && (tsapiHandshakeCompletedListener != null))
/*     */     {
/* 145 */       trySslHandshake((SSLSocket)socket, tsapiHandshakeCompletedListener);
/*     */     }
/*     */ 
/* 148 */     return socket;
/*     */   }
/*     */ 
/*     */   private void setSslSocketProperties(SSLSocket sslSocket)
/*     */   {
/*     */     try
/*     */     {
/* 161 */       sslSocket.setUseClientMode(true);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 165 */       throw new TsapiPlatformException(4, 0, "Couldn't initialize socket for secure client connection.  SSLSocket.setUseClientMode() failed. " + e);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 175 */       String[] protocols = { "TLSv1" };
/*     */ 
/* 177 */       sslSocket.setEnabledProtocols(protocols);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 181 */       throw new TsapiPlatformException(4, 0, "Couldn't initialize socket for secure client connection.  SSLSocket.setEnabledProtocols() failed. " + e);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 191 */       String[] cipherSuites = { "SSL_RSA_WITH_3DES_EDE_CBC_SHA", "TLS_RSA_WITH_AES_128_CBC_SHA" };
/*     */ 
/* 196 */       sslSocket.setEnabledCipherSuites(cipherSuites);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 200 */       throw new TsapiPlatformException(4, 0, "Couldn't initialize socket for secure client connection.  SSLSocket.setEnabledCipherSuites() failed. " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void trySslHandshake(SSLSocket sslSocket, TsapiHandshakeCompletedListener listener)
/*     */   {
			synchronized(this){
/*     */     try
/*     */     {
/* 228 */       sslSocket.startHandshake();
/* 229 */       listener.wait(SSL_HANDSHAKE_TIMEOUT * 1000);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 233 */       closeSocket(sslSocket);
/*     */ 
/* 235 */       throw new TsapiPlatformException(4, 0, "The TLS connection was closed because a network-level error occured during the SSL handshake; " + ioe);
/*     */     }
/*     */     catch (InterruptedException e)
/*     */     {
/* 242 */       closeSocket(sslSocket);
/*     */ 
/* 244 */       throw new TsapiPlatformException(4, 0, "The TLS connection was closed because the SSL handshake did not complete within " + SSL_HANDSHAKE_TIMEOUT + " seconds.");
/*     */     }
/*     */ }
/*     */     try
/*     */     {
/* 256 */       TLSServerCertificateValidator validator = new TLSServerCertificateValidator(sslSocket, listener.getSslSession(), TsapiSSLContext.getTrustManagers());
/*     */ 
/* 261 */       validator.validateAll();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 265 */       closeSocket(sslSocket);
/*     */ 
/* 267 */       throw new TsapiPlatformException(4, 0, "The TLS connection was closed because server certificate validation failed. " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void closeSocket(Socket socket)
/*     */   {
/*     */     try
/*     */     {
/* 283 */       socket.close();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 287 */       log.error("Couldn't close socket; " + ioe.getMessage(), ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   InputStream findProperties()
/*     */   {
/*     */     try
/*     */     {
/* 303 */       InputStream is = findFileSystemProperties();
/* 304 */       if ((!this.startUp) && (!checkIfTsapiProFileChanged(this.tsapiProLocation)) && (!systemPropertiesChanged())) {
/* 305 */         return null;
/*     */       }
/* 307 */       Properties fsProps = new Properties();
/* 308 */       if (is != null) {
/* 309 */         fsProps.load(is);
/* 310 */         is.close();
/*     */       }
/*     */ 
/* 313 */       Properties sysProps = findSystemProperties();
/* 314 */       this.sysSnapshot = sysProps;
/* 315 */       Properties merged = merge(fsProps, sysProps);
/* 316 */       return wrapInStream(merged);
/*     */     } catch (IOException e) {
/* 318 */       throw new TsapiPlatformException(4, 0, "can't find properties");
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean systemPropertiesChanged()
/*     */   {
/* 324 */     for (Map.Entry entry : this.sysSnapshot.entrySet()) {
/* 325 */       String lhs = System.getProperty((String)entry.getKey());
/* 326 */       String rhs = (String)entry.getValue();
/* 327 */       if (((lhs != null) && (rhs == null)) || ((lhs == null) && (rhs != null))) {
/* 328 */         return true;
/*     */       }
/* 330 */       if ((lhs != null) && (!lhs.equals(rhs))) {
/* 331 */         return true;
/*     */       }
/*     */     }
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */   private InputStream wrapInStream(Properties props)
/*     */     throws IOException
/*     */   {
/* 344 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 345 */     props.store(out, "");
/* 346 */     out.flush();
/* 347 */     ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
/* 348 */     out.close();
/* 349 */     return in;
/*     */   }
/*     */ 
/*     */   private Properties merge(Properties fsProps, Properties sysProps)
/*     */   {
/* 361 */     Properties defaults = new Properties();
/* 362 */     defaults.putAll(fsProps);
/* 363 */     for (Map.Entry entry : sysProps.entrySet()) {
/* 364 */       if (!defaults.containsKey((String)entry.getKey())) {
/* 365 */         defaults.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/* 368 */     return defaults;
/*     */   }
/*     */ 
/*     */   private Properties findSystemProperties()
/*     */   {
/* 376 */     Properties jtapiProps = new Properties();
/*     */ 
/* 378 */     for (Iterator i$ = System.getProperties().keySet().iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 379 */       String key = (String)o;
/*     */ 
/* 383 */       if ((key.toLowerCase().startsWith("com.avaya.jtapi.tsapi.")) && (!key.equalsIgnoreCase("com.avaya.jtapi.tsapi.servers")))
/*     */       {
/* 385 */         jtapiProps.put(key.substring("com.avaya.jtapi.tsapi.".length()), System.getProperty(key));
/*     */       } }
/*     */ 
/* 388 */     return jtapiProps;
/*     */   }
/*     */ 
/*     */   private InputStream findFileSystemProperties()
/*     */   {
/* 397 */     InputStream in = null;
/* 398 */     for (int i = 0; i < 3; ++i)
/*     */     {
/*     */       String resource;
/* 400 */       switch (i)
/*     */       {
/*     */       case 0:
/*     */       default:
/* 404 */         resource = "tsapi.pro";
/* 405 */         break;
/*     */       case 1:
/* 407 */         resource = "Tsapi.pro";
/* 408 */         break;
/*     */       case 2:
/* 410 */         resource = "TSAPI.PRO";
/*     */       }
/*     */ 
/* 415 */       if (in != null) break;
/* 416 */       in = searchClasspath(resource);
/*     */ 
/* 421 */       if (in != null)
/*     */         break;
/* 423 */       in = searchResources(resource);
/*     */ 
/* 428 */       if (in != null)
/*     */         break;
/* 430 */       in = searchCodeBaseURL(resource);
/*     */ 
/* 435 */       if (in != null) break;
/* 436 */       in = searchUserDir(resource);
/*     */     }
/*     */ 
/* 442 */     return in;
/*     */   }
/*     */ 
/*     */   private InputStream searchUserDir(String resource) {
/* 446 */     InputStream in = null;
/* 447 */     File f = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + resource);
/* 448 */     if ((f.exists()) && (f.canRead())) {
/*     */       try {
/* 450 */         in = new BufferedInputStream(new FileInputStream(f));
/*     */       } catch (FileNotFoundException e) {
/* 452 */         log.error(e.getMessage(), e);
/*     */       }
/*     */     }
/* 455 */     if (in != null) {
/* 456 */       this.tsapiProLocation = f.getAbsolutePath();
/* 457 */       if (this.startUp) {
/* 458 */         info("Found '" + resource + "' at location '" + this.tsapiProLocation + "'");
/*     */       }
/* 460 */       return in;
/*     */     }
/* 462 */     return in;
/*     */   }
/*     */ 
/*     */   private InputStream searchCodeBaseURL(String resource) {
/* 466 */     InputStream in = null;
/* 467 */     URL myURL = getCodeBaseURL();
/* 468 */     if (myURL != null)
/*     */     {
/*     */       try
/*     */       {
/* 472 */         in = new URL(myURL, resource).openStream();
/* 473 */         if (in != null)
/*     */         {
/* 475 */           this.tsapiProLocation = myURL.toString();
/* 476 */           if (this.startUp) {
/* 477 */             info("Found '" + resource + "' at codeBaseURL '" + this.tsapiProLocation + "'");
/*     */           }
/* 479 */           return in;
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/* 486 */     return in;
/*     */   }
/*     */ 
/*     */   private static void info(String msg) {
/* 490 */     if (JtapiUtils.isLog4jConfigured())
/* 491 */       log.info(msg);
/*     */     else
/* 493 */       System.out.println(msg);
/*     */   }
/*     */ 
/*     */   private InputStream searchResources(String resource)
/*     */   {
/* 498 */     InputStream in = null;
/*     */     try
/*     */     {
/* 501 */       in = super.getClass().getResourceAsStream("/" + resource);
/* 502 */       if (in != null)
/*     */       {
/* 504 */         this.tsapiProLocation = "<unknown>";
/* 505 */         if (super.getClass().getResource("/" + resource) != null) {
/* 506 */           this.tsapiProLocation = super.getClass().getResource("/" + resource).getFile();
/*     */         }
/* 508 */         if (this.startUp) {
/* 509 */           info("Found '" + resource + "' as a resource at location '" + this.tsapiProLocation + "'");
/*     */         }
/* 511 */         return in;
/*     */       }
/*     */     }
/*     */     catch (NoSuchMethodError e)
/*     */     {
/* 516 */       File propfile = new File(resource);
/*     */       try
/*     */       {
/* 520 */         if (propfile.canRead())
/*     */         {
/* 522 */           in = new FileInputStream(propfile);
/*     */         }
/*     */       }
/*     */       catch (Exception e1) {
/*     */       }
/* 527 */       if (in != null)
/*     */       {
/* 529 */         this.tsapiProLocation = propfile.getAbsolutePath();
/* 530 */         info("Found '" + resource + "' as a file at location '" + this.tsapiProLocation + "'");
/* 531 */         return in;
/*     */       }
/*     */     }
/* 534 */     return in;
/*     */   }
/*     */ 
/*     */   private boolean checkIfTsapiProFileChanged(String location) {
/* 538 */     if (location != null) {
/* 539 */       File file = new File(location);
/* 540 */       long timeWhenLastChecked = System.currentTimeMillis() - Tsapi.getRefreshIntervalForTsapiPro() * 1000;
/* 541 */       if (file.lastModified() >= timeWhenLastChecked) {
/* 542 */         return true;
/*     */       }
/*     */     }
/* 545 */     return false;
/*     */   }
/*     */ 
/*     */   private InputStream searchClasspath(String resource) {
/* 549 */     InputStream in = null;
/*     */     try
/*     */     {
/* 552 */       in = ClassLoader.getSystemResourceAsStream("/" + resource);
/* 553 */       if (in != null)
/*     */       {
/* 555 */         this.tsapiProLocation = ClassLoader.getSystemResource("/" + resource).getFile();
/* 556 */         if (this.startUp) {
/* 557 */           info("Found '/" + resource + "' as a system resource at location '" + this.tsapiProLocation + "'");
/*     */         }
/* 559 */         label176: return in;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (NoSuchMethodError e)
/*     */     {
/* 565 */       File propfile = null;
/*     */       try
/*     */       {
/* 568 */         StringTokenizer classpath = new StringTokenizer(System.getProperty("java.class.path"), System.getProperty("path.separator"));
/*     */         do
/*     */         {
/* 571 */           if (!classpath.hasMoreTokens())
///*     */             break label176;
	return in;
/* 573 */           propfile = new File(classpath.nextToken(), resource);
/* 574 */         }while (!propfile.canRead());
/*     */ 
/* 576 */         in = new FileInputStream(propfile);
/*     */       }
/*     */       catch (Exception e1)
/*     */       {
/*     */       }
/*     */ 
/* 583 */       if (in != null)
/*     */       {
/* 585 */         this.tsapiProLocation = "<unknown>";
/* 586 */         if (propfile != null) {
/* 587 */           this.tsapiProLocation = propfile.getAbsolutePath();
/*     */         }
/* 589 */         info("Found '" + resource + "' by manual classpath search at location '" + this.tsapiProLocation + "'");
/* 590 */         return in;
/*     */       }
/*     */     }
/* 593 */     return in;
/*     */   }
/*     */ 
/*     */   static URL getCodeBaseURL()
/*     */   {
/* 604 */     SecurityManager sm = System.getSecurityManager();
/* 605 */     if (sm == null)
/*     */     {
/* 607 */       return null;
/*     */     }
/*     */ 
/* 610 */     Object context = sm.getSecurityContext();
/* 611 */     if (context instanceof URL)
/*     */     {
/* 613 */       return (URL)context;
/*     */     }
/*     */ 
/* 616 */     return null;
/*     */   }
/*     */ 
/*     */   public String getCodeBaseServer()
/*     */   {
/* 622 */     URL myURL = getCodeBaseURL();
/*     */ 
/* 624 */     if (myURL != null)
/*     */     {
/* 626 */       info("get codebase URL succeeded");
/* 627 */       return myURL.getHost();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 633 */       myURL = super.getClass().getResource(super.getClass().getName());
/*     */     }
/*     */     catch (NoSuchMethodError e) {
/*     */     }
/* 637 */     if (myURL != null)
/*     */     {
/* 639 */       info("get URL from class succeeded");
/* 640 */       return myURL.getHost();
/*     */     }
/*     */ 
/* 643 */     throw new TsapiPlatformException(4, 0, "can't find code base");
/*     */   }
/*     */ 
/*     */   public void setStartUp(boolean startUp)
/*     */   {
/* 648 */     this.startUp = startUp;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 652 */     return this.name;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.GenericBrowser
 * JD-Core Version:    0.5.4
 */