/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class TsapiAlternateTlinkEntriesList
/*     */ {
/*     */   private static final int MAX_TLINK_ALTERNATE_ENTRIES = 16;
/*  45 */   private static TsapiAlternateTlinkEntriesList _instance = null;
/*     */   private final List<TsapiAlternateTlinkEntry> entries;
/*     */ 
/*     */   public static synchronized TsapiAlternateTlinkEntriesList Instance()
/*     */   {
/*  60 */     if (_instance == null)
/*     */     {
/*  62 */       _instance = new TsapiAlternateTlinkEntriesList();
/*     */     }
/*     */ 
/*  65 */     return _instance;
/*     */   }
/*     */ 
/*     */   private TsapiAlternateTlinkEntriesList()
/*     */   {
/*  77 */     this.entries = new ArrayList();
/*     */   }
/*     */ 
/*     */   public synchronized void addAlternateTlinkEntry(String propertyName, String valueString)
/*     */     throws TsapiPropertiesException
/*     */   {
/*     */     try
/*     */     {
/* 101 */       if (this.entries.size() >= 16)
/*     */       {
/* 103 */         throw new TsapiPropertiesException("Ignoring property \"" + propertyName + "\": the maximum number of Alternate Tlink entries (" + 16 + ") has already been processed.");
/*     */       }
/*     */ 
/* 112 */       this.entries.add(new TsapiAlternateTlinkEntry(propertyName, valueString));
/*     */     }
/*     */     catch (TsapiPropertySyntaxException e)
/*     */     {
/* 116 */       throw new TsapiPropertiesException("Error processing property \"" + propertyName + "\": " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized int getAlternateTlinkIndex(String preferredTlinkName, String tlinkName)
/*     */   {
/*     */     try
/*     */     {
/* 146 */       Iterator entryIterator = this.entries.iterator();
/*     */ 
/* 148 */       while (entryIterator.hasNext())
/*     */       {
/* 150 */         TsapiAlternateTlinkEntry entry = (TsapiAlternateTlinkEntry)entryIterator.next();
/* 151 */         if (entry.getPreferredTlinkName().compareToIgnoreCase(preferredTlinkName) == 0)
/*     */         {
/* 157 */           Iterator alternatesIterator = entry.getAlternateTlinkNames().iterator();
/*     */ 
/* 160 */           for (int index = 0; alternatesIterator.hasNext(); ++index)
/*     */           {
/* 162 */             String alternate = (String)alternatesIterator.next();
/* 163 */             if (alternate.compareToIgnoreCase(tlinkName) == 0)
/*     */             {
/* 167 */               return index;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 179 */     return -1;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiAlternateTlinkEntriesList
 * JD-Core Version:    0.5.4
 */