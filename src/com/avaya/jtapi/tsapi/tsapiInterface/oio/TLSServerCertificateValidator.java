/*     */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*     */ 
/*     */ import java.net.InetSocketAddress;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ class TLSServerCertificateValidator
/*     */ {
/*  46 */   private static Logger log = Logger.getLogger(TLSServerCertificateValidator.class);
/*     */   private SSLSocket socket;
/*  48 */   private X509Certificate[] certificates = null;
/*     */   private X509Certificate certificate;
/*  50 */   private X509TrustManager trustManager = null;
/*     */ 
/*     */   TLSServerCertificateValidator(SSLSocket socket, SSLSession session, TrustManager[] trustManagers)
/*     */     throws CertificateException
/*     */   {
/*  67 */     Certificate[] peerCertificates = null;
/*     */ 
/*  69 */     if (socket == null)
/*     */     {
/*  71 */       throw new NullPointerException("Socket is null");
/*     */     }
/*     */ 
/*  74 */     this.socket = socket;
/*     */     try
/*     */     {
/*  78 */       peerCertificates = session.getPeerCertificates();
/*     */     }
/*     */     catch (SSLPeerUnverifiedException e)
/*     */     {
/*  82 */       throw new CertificateException(e);
/*     */     }
/*     */ 
/*  85 */     if (peerCertificates.length == 0)
/*     */     {
/*  90 */       throw new CertificateException("Cannot authenticate server; the server's certificate chain is empty.");
/*     */     }
/*     */ 
/*  94 */     if (!(peerCertificates[0] instanceof X509Certificate))
/*     */     {
/*  97 */       throw new CertificateException("Cannot authenticate server; the server certificate is not an X509 certificate.");
/*     */     }
/*     */ 
/* 101 */     this.certificates = ((X509Certificate[])(X509Certificate[])peerCertificates);
/* 102 */     this.certificate = this.certificates[0];
/*     */ 
/* 104 */     if (trustManagers != null)
/*     */     {
/* 108 */       for (int i = 0; i < trustManagers.length; ++i)
/*     */       {
/* 110 */         TrustManager tm = trustManagers[i];
/* 111 */         if (!(tm instanceof X509TrustManager))
/*     */           continue;
/* 113 */         this.trustManager = ((X509TrustManager)tm);
/* 114 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     if (this.trustManager != null)
/*     */       return;
/* 121 */     throw new CertificateException("Cannot authenticate server; no X509 trust managers found.");
/*     */   }
/*     */ 
/*     */   public void validateAll()
/*     */     throws CertificateException
/*     */   {
/* 133 */     validateDateWindow();
/* 134 */     validateCommonName();
/* 135 */     validateServerCertificateChain();
/*     */   }
/*     */ 
/*     */   public void validateDateWindow()
/*     */     throws CertificateException
/*     */   {
/* 148 */     this.certificate.checkValidity();
/*     */   }
/*     */ 
/*     */   public void validateCommonName()
/*     */     throws CertificateException
/*     */   {
/* 164 */     Collection altNames = this.certificate.getSubjectAlternativeNames();
/* 165 */     String commonName = "";
/*     */ 
/* 167 */     if (altNames == null)
/*     */     {
/* 169 */       log.info("The peer's certificate is not X509v3.  Parsing the CN out of the certificate.");
/*     */ 
/* 173 */       commonName = getNameFromX509(this.certificate);
/*     */     }
/*     */     else
/*     */     {
/* 177 */       log.info("The peer's certificate is X509v3.  Examining subjectAltNames for dNSName.");
/*     */ 
/* 181 */       commonName = getNameFromX509v3(altNames);
/*     */ 
/* 186 */       if (commonName.equals(""))
/*     */       {
/* 188 */         log.info("Didn't find dNSName in subjectAltNames.  Falling back to parsing the CN out of the certificate.");
/*     */ 
/* 192 */         commonName = getNameFromX509(this.certificate);
/*     */       }
/*     */     }
/*     */ 
/* 196 */     compareToResolvedName(commonName);
/*     */   }
/*     */ 
/*     */   private String getNameFromX509(X509Certificate certificate)
/*     */   {
/* 207 */     String commonName = "";
/*     */ 
/* 209 */     X500Principal principal = certificate.getSubjectX500Principal();
/* 210 */     String name = principal.getName("RFC1779");
/*     */ 
/* 212 */     log.info("X500Principal name = \"" + name + "\"");
/*     */ 
/* 223 */     StringTokenizer tokenizer = new StringTokenizer(name);
/*     */ 
/* 225 */     while (tokenizer.hasMoreTokens())
/*     */     {
/* 227 */       String token = tokenizer.nextToken();
/*     */ 
/* 229 */       log.info("token = \"" + token + "\"");
/*     */ 
/* 231 */       if (token.startsWith("CN="))
/*     */       {
/* 234 */         if (token.endsWith(","))
/*     */         {
/* 236 */           commonName = token.substring(3, token.length() - 1); break;
/*     */         }
/*     */ 
/* 240 */         commonName = token.substring(3, token.length());
/*     */ 
/* 242 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 246 */     return commonName;
/*     */   }
/*     */ 
/*     */   private String getNameFromX509v3(Collection<List<?>> altNames)
/*     */   {
/* 259 */     String commonName = "";
/*     */ 
/* 261 */     Iterator iterator = altNames.iterator();
/* 262 */     while (iterator.hasNext())
/*     */     {
/* 264 */       List indexAndNamePair = (List)iterator.next();
/* 265 */       Integer index = (Integer)indexAndNamePair.get(0);
/* 266 */       if (index.intValue() == 2)
/*     */       {
/* 268 */         commonName = (String)indexAndNamePair.get(1);
/* 269 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 273 */     return commonName;
/*     */   }
/*     */ 
/*     */   private void compareToResolvedName(String commonName)
/*     */     throws CertificateException
/*     */   {
/* 287 */     InetSocketAddress address = (InetSocketAddress)this.socket.getRemoteSocketAddress();
/*     */ 
/* 290 */     log.info("Verifying that the certificate's common name \"" + commonName + " matches the peer's hostname.");
/*     */ 
/* 293 */     if (address.isUnresolved())
/*     */     {
/* 295 */       throw new CertificateException("Unable to validate peer certificate: " + address + " could not be resolved to a host name.");
/*     */     }
/*     */ 
/* 300 */     if (address.getHostName().equalsIgnoreCase(commonName))
/*     */       return;
/* 302 */     throw new CertificateException("The Common Name (CN) in the server's certificate (" + commonName + ") does not match the server's resolved host name (" + address.getHostName() + ").");
/*     */   }
/*     */ 
/*     */   public void validateServerCertificateChain()
/*     */     throws CertificateException
/*     */   {
/* 319 */     this.trustManager.checkServerTrusted(this.certificates, "RSA");
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.TLSServerCertificateValidator
 * JD-Core Version:    0.5.4
 */