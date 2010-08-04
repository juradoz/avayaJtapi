/*     */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannel;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannelReadHandler;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayInputStream;
/*     */ import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelSocketInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import javax.net.SocketFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TsapiChannelOio
/*     */   implements TsapiChannel
/*     */ {
/*  49 */   private static Logger log = Logger.getLogger(TsapiChannelOio.class);
/*     */   private OioChannelThread thread;
/*     */   private TsapiChannelReadHandler handler;
/*     */   private Socket sock;
/*     */   private IntelSocketInputStream in;
/*     */   private OutputStream out;
/*     */   private static GenericBrowser browser;
/*     */ 
/*     */   public TsapiChannelOio(InetSocketAddress addr, SocketFactory sf)
/*     */     throws IOException
/*     */   {
/*  77 */     log.info("browser: " + browser.getName());
/*     */ 
/*  79 */     this.sock = trySocket(addr, sf);
/*  80 */     this.in = new IntelSocketInputStream(this.sock.getInputStream());
/*  81 */     this.out = this.sock.getOutputStream();
/*  82 */     this.thread = new OioChannelThread("GetEventThread");
/*  83 */     this.thread.start();
/*     */   }
/*     */ 
/*     */   public void write(ByteArrayOutputStream msg)
/*     */     throws IOException
/*     */   {
/*  95 */     msg.writeTo(this.out);
/*     */   }
/*     */ 
/*     */   public void setReadHandler(TsapiChannelReadHandler _handler)
/*     */   {
/* 107 */     this.handler = _handler;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */     try
/*     */     {
/* 120 */       this.thread.stopRunning();
/* 121 */       this.out.flush();
/* 122 */       this.sock.close();
/*     */     } catch (IOException e) {
/* 124 */       log.error("Exception when closing TsapiChannel: " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public InetSocketAddress getInetSocketAddress()
/*     */   {
/* 137 */     return (InetSocketAddress)this.sock.getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   public static InputStream getProperties()
/*     */   {
/* 149 */     return browser.findProperties();
/*     */   }
/*     */ 
/*     */   private static Socket trySocket(InetSocketAddress addr, SocketFactory sf)
/*     */   {
/*     */     try
/*     */     {
/* 156 */       return browser.trySocket(addr, sf);
/*     */     }
/*     */     catch (UnknownHostException e)
/*     */     {
/* 160 */       throw new TsapiPlatformException(4, 0, "address <" + addr + "> not found");
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 165 */       throw new TsapiPlatformException(4, 0, "connection to address <" + addr + "> timed out");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void determineBrowser()
/*     */   {
/*     */     try
/*     */     {
/* 175 */       Class.forName("netscape.security.PrivilegeManager");
/*     */ 
/* 177 */       Class.forName("netscape.security.AppletSecurityException");
/* 178 */       Class.forName("netscape.applet.AppletClassLoader");
/* 179 */       browser = (GenericBrowser)Class.forName("com.avaya.jtapi.tsapi.NsBr").newInstance();
/* 180 */       return;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       try
/*     */       {
/* 187 */         Class.forName("com.ms.security.PermissionID");
/* 188 */         browser = (GenericBrowser)Class.forName("com.avaya.jtapi.tsapi.IeBr").newInstance();
/* 189 */         return;
/*     */       }
/*     */       catch (Exception e2)
/*     */       {
/* 195 */         browser = new GenericBrowser();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static GenericBrowser getBrowser()
/*     */   {
/* 229 */     return browser;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  62 */     determineBrowser();
/*     */   }
/*     */ 
/*     */   private class OioChannelThread extends Thread
/*     */   {
/* 199 */     private boolean keepRunning = true;
/*     */ 
/*     */     public OioChannelThread(String name) {
/* 202 */       super(name);
/*     */     }
/*     */ 
/*     */     public void run() {
/* 206 */       while (this.keepRunning)
/*     */         try
/*     */         {
/* 209 */           int msgLen = TsapiChannelOio.this.in.readInt();
/* 210 */           byte[] msgBuf = new byte[msgLen];
/* 211 */           TsapiChannelOio.this.in.readFully(msgBuf);
/* 212 */           IntelByteArrayInputStream msg = new IntelByteArrayInputStream(msgBuf);
/* 213 */           TsapiChannelOio.this.handler.handleRead(msg);
/*     */         } catch (IOException e) {
/* 215 */           TsapiChannelOio.this.handler.handleException(e);
/*     */         }
/*     */     }
/*     */ 
/*     */     public void stopRunning()
/*     */     {
/* 221 */       this.keepRunning = false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiChannelOio
 * JD-Core Version:    0.5.4
 */