/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ final class TsapiAlternateTlinkEntry
/*     */ {
/*     */   private static final int MAX_TLINK_ALTERNATES_PER_ENTRY = 4;
/*     */   private static final int ACS_MAX_SERVICEID = 48;
/*     */   private String preferredTlinkName;
/*     */   private final List<String> alternateTlinks;
/*     */ 
/*     */   public TsapiAlternateTlinkEntry(String propertyName, String valueString)
/*     */     throws TsapiPropertiesException
/*     */   {
/* 230 */     this.alternateTlinks = new ArrayList();
/*     */ 
/* 233 */     parsePropertyName(propertyName);
/* 234 */     parseValueString(valueString);
/*     */   }
/*     */ 
/*     */   public String getPreferredTlinkName()
/*     */   {
/* 243 */     return this.preferredTlinkName;
/*     */   }
/*     */ 
/*     */   public List<String> getAlternateTlinkNames()
/*     */   {
/* 253 */     return this.alternateTlinks;
/*     */   }
/*     */ 
/*     */   private void parsePropertyName(String propertyName)
/*     */     throws TsapiPropertiesException
/*     */   {
/*     */     String tlinkName;
/*     */     try
/*     */     {
/* 280 */       if (!propertyName.regionMatches(true, 0, "Alternates", 0, 10))
/*     */       {
/* 282 */         throw new TsapiAlternateTlinkPropertyNameSyntaxException("property name must begin with \"Alternates\".");
/*     */       }
/*     */ 
/* 286 */       propertyName = propertyName.substring(10);
/*     */ 
/* 291 */       propertyName = propertyName.trim();
/*     */ 
/* 294 */       if (!propertyName.startsWith("("))
/*     */       {
/* 296 */         throw new TsapiAlternateTlinkPropertyNameSyntaxException("expected opening parenthesis after \"Alternates\".");
/*     */       }
/*     */ 
/* 300 */       propertyName = propertyName.substring(1);
/*     */ 
/* 305 */       int index = propertyName.indexOf(')');
/* 306 */       if (index == -1)
/*     */       {
/* 308 */         throw new TsapiAlternateTlinkPropertyNameSyntaxException("the preferred Tlink name must be enclosed by parentheses.");
/*     */       }
/*     */ 
/* 311 */       if (index < 1)
/*     */       {
/* 313 */         throw new TsapiAlternateTlinkPropertyNameSyntaxException("no preferred Tlink name specified.");
/*     */       }
/*     */ 
/* 317 */       tlinkName = propertyName.substring(0, index);
/*     */ 
/* 321 */       tlinkName = trimTlinkName(tlinkName);
/*     */ 
/* 324 */       propertyName = propertyName.substring(index);
/*     */ 
/* 327 */       if (!propertyName.equals(")"))
/*     */       {
/* 329 */         throw new TsapiAlternateTlinkPropertyNameSyntaxException("unexpected character(s) after closing parenthesis.");
/*     */       }
/*     */     }
/*     */     catch (IndexOutOfBoundsException e)
/*     */     {
/* 334 */       throw new TsapiPropertiesException("Error parsing property name");
/*     */     }
/*     */ 
/* 338 */     this.preferredTlinkName = new String(tlinkName);
/*     */   }
/*     */ 
/*     */   private void parseValueString(String valueString)
/*     */     throws TsapiPropertiesException
/*     */   {
/*     */     try
/*     */     {
/* 361 */       valueString = valueString.trim();
/*     */ 
/* 363 */       if (valueString.equals(""))
/*     */       {
/* 365 */         throw new TsapiAlternateTlinkPropertyValueSyntaxException("no Alternate Tlinks specified.");
/*     */       }
/*     */ 
/* 373 */       String[] tokens = valueString.split(":", 5);
/*     */ 
/* 375 */       int numTokens = tokens.length;
/*     */ 
/* 377 */       for (int i = 0; (i < 4) && (i < numTokens); ++i)
/*     */       {
/* 380 */         String token = tokens[i];
/*     */ 
/* 384 */         token = trimTlinkName(token);
/*     */ 
/* 386 */         if (token.equals(""))
/*     */         {
/* 389 */           throw new TsapiAlternateTlinkPropertyValueSyntaxException("zero-length token for Alternate Tlink name.");
/*     */         }
/*     */ 
/* 393 */         this.alternateTlinks.add(token);
/*     */       }
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/* 398 */       throw new TsapiPropertiesException("Error parsing property value");
/*     */     }
/*     */   }
/*     */ 
/*     */   private String trimTlinkName(String tlinkName)
/*     */   {
/* 418 */     tlinkName = tlinkName.trim();
/*     */ 
/* 422 */     if (tlinkName.length() > 48)
/*     */     {
/*     */       try
/*     */       {
/* 426 */         tlinkName = tlinkName.substring(0, 47);
/*     */       }
/*     */       catch (IndexOutOfBoundsException e)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 434 */     return new String(tlinkName);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiAlternateTlinkEntry
 * JD-Core Version:    0.5.4
 */