/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNSequence;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class LucentPrivateData extends ASNSequence
/*     */ {
/*  13 */   private static Logger log = Logger.getLogger(LucentPrivateData.class);
/*     */   public static final String LUCENT_VENDOR_STRING = "AT&T Definity G3";
/*     */   public static final String AVAYA_VENDOR_STRING = "ECS";
/*     */   public static final int ENC_DIS_HI = 0;
/*     */   public static final int ENC_DIS_LO = 2;
/*     */   int tsType;
/*     */ 
/*     */   public static boolean isAvayaVendor(String vendor_name)
/*     */   {
/*  30 */     if ("ECS".equals(vendor_name)) {
/*  31 */       return true;
/*     */     }
/*  33 */     return "AT&T Definity G3".equals(vendor_name);
/*     */   }
/*     */ 
/*     */   public static LucentPrivateData create(CSTAPrivate priv, int tsType)
/*     */   {
/*  39 */     LucentPrivateData lpriv = null;
/*     */ 
/*  41 */     if (priv != null) {
/*  42 */       ByteArrayInputStream decodeStream = new ByteArrayInputStream(priv.data);
/*     */ 
/*  44 */       if ((decodeStream.read() != 2) || (decodeStream.read() != 0))
/*     */       {
/*  47 */         return null;
/*     */       }
/*     */ 
/*  50 */       int pdunum = decodeStream.read() << 0;
/*  51 */       pdunum += (decodeStream.read() << 8);
/*     */ 
/*  57 */       switch (pdunum)
/*     */       {
/*     */       case 26:
/*  61 */         lpriv = LucentQueryTg.decode(decodeStream);
/*  62 */         break;
/*     */       case 9:
/*  65 */         lpriv = LucentSendDTMFToneConfEvent.decode(decodeStream);
/*  66 */         break;
/*     */       case 8:
/*  69 */         lpriv = LucentSendDTMFTone.decode(decodeStream);
/*  70 */         break;
/*     */       case 71:
/*  73 */         lpriv = LucentV5SendDTMFTone.decode(decodeStream);
/*  74 */         break;
/*     */       case 11:
/*  77 */         lpriv = LucentQueryAcdSplit.decode(decodeStream);
/*  78 */         break;
/*     */       case 12:
/*  81 */         lpriv = LucentQueryAcdSplitConfEvent.decode(decodeStream);
/*  82 */         break;
/*     */       case 13:
/*  85 */         lpriv = LucentQueryAgentLogin.decode(decodeStream);
/*  86 */         break;
/*     */       case 14:
/*  89 */         lpriv = LucentQueryAgentLoginConfEvent.decode(decodeStream);
/*  90 */         break;
/*     */       case 15:
/*  93 */         lpriv = LucentQueryAgentLoginResp.decode(decodeStream);
/*  94 */         break;
/*     */       case 17:
/*  97 */         lpriv = LucentQueryAgentStateConfEvent.decode(decodeStream);
/*  98 */         break;
/*     */       case 88:
/* 101 */         lpriv = LucentV5QueryAgentStateConfEvent.decode(decodeStream);
/* 102 */         break;
/*     */       case 19:
/* 105 */         lpriv = LucentCallClassifierInfo.decode(decodeStream);
/* 106 */         break;
/*     */       case 20:
/* 109 */         lpriv = LucentQueryDeviceInfoConfEvent.decode(decodeStream);
/* 110 */         break;
/*     */       case 98:
/* 113 */         lpriv = LucentV5QueryDeviceInfoConfEvent.decode(decodeStream);
/* 114 */         break;
/*     */       case 21:
/* 117 */         lpriv = LucentQueryMwiConfEvent.decode(decodeStream);
/* 118 */         break;
/*     */       case 23:
/* 121 */         lpriv = LucentQueryStationStatusConfEvent.decode(decodeStream);
/* 122 */         break;
/*     */       case 25:
/* 125 */         lpriv = LucentQueryTodConfEvent.decode(decodeStream);
/* 126 */         break;
/*     */       case 27:
/* 129 */         lpriv = LucentTrunkGroupInfo.decode(decodeStream);
/* 130 */         break;
/*     */       case 33:
/* 162 */         lpriv = LucentMonitorCallConfEvent.decode(decodeStream);
/* 163 */         break;
/*     */       case 94:
/* 166 */         lpriv = LucentV5MonitorCallConfEvent.decode(decodeStream);
/* 167 */         break;
/*     */       case 34:
/* 170 */         lpriv = LucentCallClearedEvent.decode(decodeStream);
/* 171 */         break;
/*     */       case 36:
/* 178 */         lpriv = LucentConnectionClearedEvent.decode(decodeStream);
/* 179 */         break;
/*     */       case 38:
/* 186 */         lpriv = LucentEnteredDigitsEvent.decode(decodeStream);
/* 187 */         break;
/*     */       case 40:
/* 194 */         lpriv = LucentNetworkProgressInfo.decode(decodeStream);
/* 195 */         break;
/*     */       case 101:
/* 198 */         lpriv = LucentV5NetworkProgressInfo.decode(decodeStream);
/* 199 */         break;
/*     */       case 42:
/* 206 */         lpriv = LucentRouteRequestEvent.decode(decodeStream);
/* 207 */         break;
/*     */       case 83:
/* 210 */         lpriv = LucentV5RouteRequestEvent.decode(decodeStream);
/* 211 */         break;
/*     */       case 44:
/* 214 */         lpriv = LucentPrivateRouteUsedEvent.decode(decodeStream);
/* 215 */         break;
/*     */       case 47:
/* 222 */         lpriv = LucentOriginatedEvent.decode(decodeStream);
/* 223 */         break;
/*     */       case 48:
/* 226 */         lpriv = LucentLoggedOnEvent.decode(decodeStream);
/* 227 */         break;
/*     */       case 79:
/* 230 */         lpriv = LucentLoggedOffEvent.decode(decodeStream);
/* 231 */         break;
/*     */       case 50:
/* 234 */         lpriv = LucentQueryDeviceNameConfEvent.decode(decodeStream);
/* 235 */         break;
/*     */       case 89:
/* 238 */         lpriv = LucentV5QueryDeviceNameConfEvent.decode(decodeStream);
/* 239 */         break;
/*     */       case 59:
/* 242 */         lpriv = LucentConferencedEvent.decode(decodeStream);
/* 243 */         break;
/*     */       case 78:
/* 246 */         lpriv = LucentV5ConferencedEvent.decode(decodeStream);
/* 247 */         break;
/*     */       case 60:
/* 250 */         lpriv = LucentDeliveredEvent.decode(decodeStream);
/* 251 */         break;
/*     */       case 80:
/* 254 */         lpriv = LucentV5DeliveredEvent.decode(decodeStream);
/* 255 */         break;
/*     */       case 61:
/* 258 */         lpriv = LucentEstablishedEvent.decode(decodeStream);
/* 259 */         break;
/*     */       case 81:
/* 262 */         lpriv = LucentV5EstablishedEvent.decode(decodeStream);
/* 263 */         break;
/*     */       case 95:
/* 266 */         lpriv = LucentServiceInitiatedEvent.decode(decodeStream);
/* 267 */         break;
/*     */       case 62:
/* 270 */         lpriv = LucentTransferredEvent.decode(decodeStream);
/* 271 */         break;
/*     */       case 82:
/* 274 */         lpriv = LucentV5TransferredEvent.decode(decodeStream);
/* 275 */         break;
/*     */       case 107:
/* 278 */         lpriv = LucentV6ConferencedEvent.decode(decodeStream);
/* 279 */         break;
/*     */       case 115:
/* 282 */         lpriv = LucentV6ConnectionClearedEvent.decode(decodeStream);
/* 283 */         break;
/*     */       case 117:
/* 286 */         lpriv = LucentV6DeliveredEvent.decode(decodeStream);
/* 287 */         break;
/*     */       case 118:
/* 290 */         lpriv = LucentV6EstablishedEvent.decode(decodeStream);
/* 291 */         break;
/*     */       case 119:
/* 294 */         lpriv = LucentV6OriginatedEvent.decode(decodeStream);
/* 295 */         break;
/*     */       case 105:
/* 298 */         lpriv = LucentV6RouteRequestEvent.decode(decodeStream);
/* 299 */         break;
/*     */       case 106:
/* 302 */         lpriv = LucentV6TransferredEvent.decode(decodeStream);
/* 303 */         break;
/*     */       case 125:
/* 309 */         lpriv = LucentV7QueryDeviceNameConfEvent.decode(decodeStream);
/* 310 */         break;
/*     */       case 127:
/* 313 */         lpriv = LucentV7GetAPICapsConfEvent.decode(decodeStream);
/* 314 */         break;
/*     */       case 128:
/* 317 */         lpriv = LucentV7DeliveredEvent.decode(decodeStream);
/* 318 */         break;
/*     */       case 129:
/* 321 */         lpriv = LucentV7EstablishedEvent.decode(decodeStream);
/* 322 */         break;
/*     */       case 130:
/* 325 */         lpriv = LucentQueuedEvent.decode(decodeStream);
/* 326 */         break;
/*     */       case 133:
/* 329 */         lpriv = LucentV7ConferencedEvent.decode(decodeStream);
/* 330 */         break;
/*     */       case 134:
/* 333 */         lpriv = LucentV7ConnectionClearedEvent.decode(decodeStream);
/* 334 */         break;
/*     */       case 131:
/* 337 */         lpriv = LucentV7RouteRequestEvent.decode(decodeStream);
/* 338 */         break;
/*     */       case 132:
/* 341 */         lpriv = LucentV7TransferredEvent.decode(decodeStream);
/* 342 */         break;
/*     */       case 135:
/* 345 */         lpriv = LucentDivertedEvent.decode(decodeStream);
/* 346 */         break;
/*     */       case 136:
/* 349 */         lpriv = LucentV7NetworkProgressInfo.decode(decodeStream);
/* 350 */         break;
/*     */       case 141:
/* 353 */         lpriv = LucentV8FailedEvent.decode(decodeStream);
/* 354 */         break;
/*     */       case 137:
/* 357 */         lpriv = LucentFailedEvent.decode(decodeStream);
/* 358 */         break;
/*     */       case 138:
/* 361 */         lpriv = LucentSnapshotCallInfoConfEvent.decode(decodeStream);
/* 362 */         break;
/*     */       case 64:
/* 370 */         lpriv = LucentGetAPICapsConfEvent.decode(decodeStream);
/* 371 */         break;
/*     */       case 97:
/* 374 */         lpriv = LucentV5GetAPICapsConfEvent.decode(decodeStream);
/* 375 */         break;
/*     */       case 75:
/* 378 */         lpriv = LucentSetBillRateConfEvent.decode(decodeStream);
/* 379 */         break;
/*     */       case 100:
/* 382 */         lpriv = LucentSetAdviceOfChargeConfEvent.decode(decodeStream);
/* 383 */         break;
/*     */       case 96:
/* 386 */         lpriv = LucentChargeAdvice.decode(decodeStream);
/* 387 */         break;
/*     */       case 69:
/* 390 */         lpriv = LucentSelectiveListeningRetrieve.decode(decodeStream);
/* 391 */         break;
/*     */       case 70:
/* 394 */         lpriv = LucentSelectiveListeningRetrieveConfEvent.decode(decodeStream);
/* 395 */         break;
/*     */       case 68:
/* 398 */         lpriv = LucentSelectiveListeningHoldConfEvent.decode(decodeStream);
/* 399 */         break;
/*     */       case 67:
/* 402 */         lpriv = LucentSelectiveListeningHold.decode(decodeStream);
/* 403 */         break;
/*     */       case 49:
/* 406 */         lpriv = LucentQueryDeviceName.decode(decodeStream);
/* 407 */         break;
/*     */       case 76:
/* 410 */         lpriv = LucentQueryUcid.decode(decodeStream);
/* 411 */         break;
/*     */       case 77:
/* 414 */         lpriv = LucentQueryUcidConfEvent.decode(decodeStream);
/* 415 */         break;
/*     */       case 91:
/* 418 */         lpriv = LucentTransferCallConfEvent.decode(decodeStream);
/* 419 */         break;
/*     */       case 84:
/* 422 */         lpriv = LucentConsultationCallConfEvent.decode(decodeStream);
/* 423 */         break;
/*     */       case 85:
/* 426 */         lpriv = LucentMakeCallConfEvent.decode(decodeStream);
/* 427 */         break;
/*     */       case 65:
/* 430 */         lpriv = LucentSingleStepConferenceCall.decode(decodeStream);
/* 431 */         break;
/*     */       case 66:
/* 434 */         lpriv = LucentSingleStepConferenceCallConfEvent.decode(decodeStream);
/* 435 */         break;
/*     */       case 142:
/* 438 */         lpriv = LucentSingleStepTransferCall.decode(decodeStream);
/* 439 */         break;
/*     */       case 143:
/* 442 */         lpriv = LucentSingleStepTransferCallConfEvent.decode(decodeStream);
/* 443 */         break;
/*     */       case 90:
/* 446 */         lpriv = LucentConferenceCallConfEvent.decode(decodeStream);
/* 447 */         break;
/*     */       case 86:
/* 450 */         lpriv = LucentMakePredictiveCallConfEvent.decode(decodeStream);
/* 451 */         break;
/*     */       case 102:
/* 454 */         lpriv = LucentV6SetAgentState.decode(decodeStream, null);
/* 455 */         break;
/*     */       case 103:
/* 458 */         lpriv = LucentSetAgentStateConfEvent.decode(decodeStream);
/* 459 */         break;
/*     */       case 104:
/* 462 */         lpriv = LucentV6QueryAgentStateConfEvent.decode(decodeStream);
/* 463 */         break;
/*     */       case 124:
/* 466 */         lpriv = LucentAgentModeChangeEvent.decode(decodeStream);
/* 467 */         break;
/*     */       case 121:
/* 469 */         lpriv = LucentPChallenge.decode(decodeStream);
/* 470 */         break;
/*     */       case 139:
/* 473 */         lpriv = LucentUseUnifiedDesktopLicense.decode(decodeStream);
/* 474 */         break;
/*     */       case 140:
/* 476 */         lpriv = LucentUseUnifiedDesktopLicenseConfEvent.decode(decodeStream);
/* 477 */         break;
/*     */       case 73:
/* 479 */         lpriv = LucentLinkStatusEvent.decode(decodeStream);
/* 480 */         break;
/*     */       case 10:
/*     */       case 16:
/*     */       case 18:
/*     */       case 22:
/*     */       case 24:
/*     */       case 28:
/*     */       case 29:
/*     */       case 30:
/*     */       case 31:
/*     */       case 32:
/*     */       case 35:
/*     */       case 37:
/*     */       case 39:
/*     */       case 41:
/*     */       case 43:
/*     */       case 45:
/*     */       case 46:
/*     */       case 51:
/*     */       case 52:
/*     */       case 53:
/*     */       case 54:
/*     */       case 55:
/*     */       case 56:
/*     */       case 57:
/*     */       case 58:
/*     */       case 63:
/*     */       case 72:
/*     */       case 74:
/*     */       case 87:
/*     */       case 92:
/*     */       case 93:
/*     */       case 99:
/*     */       case 108:
/*     */       case 109:
/*     */       case 110:
/*     */       case 111:
/*     */       case 112:
/*     */       case 113:
/*     */       case 114:
/*     */       case 116:
/*     */       case 120:
/*     */       case 122:
/*     */       case 123:
/*     */       case 126:
/*     */       default:
/* 484 */         log.info(" data PDU " + pdunum + " not decoded");
/* 485 */         return null;
/*     */       }
/*     */ 
/* 492 */       lpriv.tsType = tsType;
/*     */ 
/* 494 */       if (log.isDebugEnabled()) {
/* 495 */         for (String str : lpriv.print()) {
/* 496 */           log.debug(str);
/*     */         }
/*     */       }
/*     */     }
/* 500 */     return lpriv;
/*     */   }
/*     */ 
/*     */   public void encodeLookahead(LucentLookaheadInfo lookInfo, OutputStream memberStream)
/*     */   {
/* 509 */     LucentLookaheadInfo.encode(lookInfo, memberStream);
/*     */   }
/*     */ 
/*     */   public LucentLookaheadInfo decodeLookahead(InputStream memberStream)
/*     */   {
/* 514 */     return LucentLookaheadInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeOCI(LucentOriginalCallInfo callInfo, OutputStream memberStream)
/*     */   {
/* 519 */     LucentOriginalCallInfo.encode(callInfo, memberStream);
/*     */   }
/*     */ 
/*     */   public LucentOriginalCallInfo decodeOCI(InputStream memberStream)
/*     */   {
/* 524 */     return LucentOriginalCallInfo.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public CSTAPrivate makeTsapiPrivate()
/*     */   {
/* 530 */     if (log.isDebugEnabled()) {
/* 531 */       for (String str : print()) {
/* 532 */         log.debug(str);
/*     */       }
/*     */     }
/* 535 */     ByteArrayOutputStream encodeStream = new ByteArrayOutputStream();
/* 536 */     int pdunum = getPDU();
/*     */ 
/* 538 */     encodeStream.write(2);
/* 539 */     encodeStream.write(0);
/*     */ 
/* 541 */     encodeStream.write(pdunum >> 0 & 0xFF);
/* 542 */     encodeStream.write(pdunum >> 8 & 0xFF);
/*     */     try
/*     */     {
/* 545 */       encode(encodeStream);
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 549 */     return new CSTAPrivate(encodeStream.toByteArray(), true);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentPrivateData
 * JD-Core Version:    0.5.4
 */