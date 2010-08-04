/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CSTAPrivate
/*     */ {
/*  27 */   private static Logger log = Logger.getLogger(CSTAPrivate.class);
/*     */   public String vendor;
/*     */   public byte[] data;
/*     */   public int tsType;
/*     */ 
/*     */   public CSTAPrivate(byte[] _data)
/*     */   {
/*  42 */     this(_data, false);
/*     */   }
/*     */ 
/*     */   public CSTAPrivate(byte[] _data, boolean waitForResponse)
/*     */   {
/*  60 */     this.data = _data;
/*  61 */     if (waitForResponse)
/*     */     {
/*  63 */       this.tsType = 89;
/*     */     }
/*     */     else
/*     */     {
/*  67 */       this.tsType = 95;
/*     */     }
/*     */   }
/*     */ 
/*     */   public CSTAPrivate(String _vendor, byte[] _data, int _tsType)
/*     */   {
/*  74 */     this.vendor = _vendor;
/*  75 */     this.data = _data;
/*  76 */     this.tsType = _tsType;
/*     */   }
/*     */ 
/*     */   public byte[] getData()
/*     */   {
/*  84 */     return this.data;
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  90 */     Collection lines = new ArrayList();
/*     */ 
/*  92 */     lines.add("TsapiPrivate ::=");
/*  93 */     lines.add("{");
/*     */ 
/*  95 */     String indent = "  ";
/*     */ 
/*  97 */     lines.addAll(ASNIA5String.print(this.vendor, "vendor", indent));
/*  98 */     lines.addAll(ASNOctetString.print(this.data, "data", indent));
/*  99 */     lines.addAll(ASNInteger.print(this.tsType, "tsType", indent));
/*     */ 
/* 101 */     lines.add("}");
/* 102 */     return lines;
/*     */   }
/*     */ 
/*     */   public static void translatePrivateData(CSTAEvent event, String debugID)
/*     */   {
/*     */     try
/*     */     {
/* 117 */       if (event.getPrivData() instanceof CSTAPrivate)
/*     */       {
/* 119 */         CSTAPrivate priv = (CSTAPrivate)event.getPrivData();
/*     */ 
/* 121 */         if ((priv.data != null) && (priv.data.length > 0))
/*     */         {
/* 123 */           if (LucentPrivateData.isAvayaVendor(priv.vendor))
/*     */           {
/* 125 */             LucentPrivateData luPriv = LucentPrivateData.create(priv, event.getEventHeader().getEventType());
/*     */ 
/* 132 */             if (luPriv != null) {
/* 133 */               event.setPrivData(luPriv);
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */         else {
/* 139 */           event.setPrivData(null);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 145 */       log.error("tsapi.translatePrivateData() failure: for " + debugID);
/* 146 */       log.error(e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAPrivate
 * JD-Core Version:    0.5.4
 */