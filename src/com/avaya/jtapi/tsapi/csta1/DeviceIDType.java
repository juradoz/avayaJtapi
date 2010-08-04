/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class DeviceIDType extends ASNEnumerated
/*     */ {
/*     */   public static final short DEVICE_IDENTIFIER = 0;
/*     */   public static final short IMPLICIT_PUBLIC = 20;
/*     */   public static final short EXPLICIT_PUBLIC_UNKNOWN = 30;
/*     */   public static final short EXPLICIT_PUBLIC_INTERNATIONAL = 31;
/*     */   public static final short EXPLICIT_PUBLIC_NATIONAL = 32;
/*     */   public static final short EXPLICIT_PUBLIC_NETWORK_SPECIFIC = 33;
/*     */   public static final short EXPLICIT_PUBLIC_SUBSCRIBER = 34;
/*     */   public static final short EXPLICIT_PUBLIC_ABBREVIATED = 35;
/*     */   public static final short IMPLICIT_PRIVATE = 40;
/*     */   public static final short EXPLICIT_PRIVATE_UNKNOWN = 50;
/*     */   public static final short EXPLICIT_PRIVATE_LEVEL3_REGIONAL_NUMBER = 51;
/*     */   public static final short EXPLICIT_PRIVATE_LEVEL2_REGIONAL_NUMBER = 52;
/*     */   public static final short EXPLICIT_PRIVATE_LEVEL1_REGIONAL_NUMBER = 53;
/*     */   public static final short EXPLICIT_PRIVATE_PTN_SPECIFIC_NUMBER = 54;
/*     */   public static final short EXPLICIT_PRIVATE_LOCAL_NUMBER = 55;
/*     */   public static final short EXPLICIT_PRIVATE_ABBREVIATED = 56;
/*     */   public static final short OTHER_PLAN = 60;
/*     */   public static final short TRUNK_IDENTIFIER = 70;
/*     */   public static final short TRUNK_GROUP_IDENTIFIER = 71;
/*     */ 
/*     */   static Collection<String> print(short value, String name, String indent)
/*     */   {
/*     */     String str;
/*  33 */     switch (value)
/*     */     {
/*     */     case 0:
/*  36 */       str = "DEVICE_IDENTIFIER";
/*  37 */       break;
/*     */     case 20:
/*  40 */       str = "IMPLICIT_PUBLIC";
/*  41 */       break;
/*     */     case 30:
/*  44 */       str = "EXPLICIT_PUBLIC_UNKNOWN";
/*  45 */       break;
/*     */     case 31:
/*  48 */       str = "EXPLICIT_PUBLIC_INTERNATIONAL";
/*  49 */       break;
/*     */     case 32:
/*  52 */       str = "EXPLICIT_PUBLIC_NATIONAL";
/*  53 */       break;
/*     */     case 33:
/*  56 */       str = "EXPLICIT_PUBLIC_NETWORK_SPECIFIC";
/*  57 */       break;
/*     */     case 34:
/*  60 */       str = "EXPLICIT_PUBLIC_SUBSCRIBER";
/*  61 */       break;
/*     */     case 35:
/*  64 */       str = "EXPLICIT_PUBLIC_ABBREVIATED";
/*  65 */       break;
/*     */     case 40:
/*  68 */       str = "IMPLICIT_PRIVATE";
/*  69 */       break;
/*     */     case 50:
/*  72 */       str = "EXPLICIT_PRIVATE_UNKNOWN";
/*  73 */       break;
/*     */     case 51:
/*  76 */       str = "EXPLICIT_PRIVATE_LEVEL3_REGIONAL_NUMBER";
/*  77 */       break;
/*     */     case 52:
/*  80 */       str = "EXPLICIT_PRIVATE_LEVEL2_REGIONAL_NUMBER";
/*  81 */       break;
/*     */     case 53:
/*  84 */       str = "EXPLICIT_PRIVATE_LEVEL1_REGIONAL_NUMBER";
/*  85 */       break;
/*     */     case 54:
/*  88 */       str = "EXPLICIT_PRIVATE_PTN_SPECIFIC_NUMBER";
/*  89 */       break;
/*     */     case 55:
/*  92 */       str = "EXPLICIT_PRIVATE_LOCAL_NUMBER";
/*  93 */       break;
/*     */     case 56:
/*  96 */       str = "EXPLICIT_PRIVATE_ABBREVIATED";
/*  97 */       break;
/*     */     case 60:
/* 100 */       str = "OTHER_PLAN";
/* 101 */       break;
/*     */     case 70:
/* 104 */       str = "TRUNK_IDENTIFIER";
/* 105 */       break;
/*     */     case 71:
/* 108 */       str = "TRUNK_GROUP_IDENTIFIER";
/* 109 */       break;
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/*     */     case 8:
/*     */     case 9:
/*     */     case 10:
/*     */     case 11:
/*     */     case 12:
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*     */     case 16:
/*     */     case 17:
/*     */     case 18:
/*     */     case 19:
/*     */     case 21:
/*     */     case 22:
/*     */     case 23:
/*     */     case 24:
/*     */     case 25:
/*     */     case 26:
/*     */     case 27:
/*     */     case 28:
/*     */     case 29:
/*     */     case 36:
/*     */     case 37:
/*     */     case 38:
/*     */     case 39:
/*     */     case 41:
/*     */     case 42:
/*     */     case 43:
/*     */     case 44:
/*     */     case 45:
/*     */     case 46:
/*     */     case 47:
/*     */     case 48:
/*     */     case 49:
/*     */     case 57:
/*     */     case 58:
/*     */     case 59:
/*     */     case 61:
/*     */     case 62:
/*     */     case 63:
/*     */     case 64:
/*     */     case 65:
/*     */     case 66:
/*     */     case 67:
/*     */     case 68:
/*     */     case 69:
/*     */     default:
/* 112 */       str = "?? " + value + " ??";
/*     */     }
/*     */ 
/* 116 */     return print(value, str, name, indent);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.DeviceIDType
 * JD-Core Version:    0.5.4
 */