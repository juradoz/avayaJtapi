 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequence;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.InputStream;
 import java.io.OutputStream;
 import org.apache.log4j.Logger;
 
 public abstract class LucentPrivateData extends ASNSequence
 {
   private static Logger log = Logger.getLogger(LucentPrivateData.class);
   public static final String LUCENT_VENDOR_STRING = "AT&T Definity G3";
   public static final String AVAYA_VENDOR_STRING = "ECS";
   public static final int ENC_DIS_HI = 0;
   public static final int ENC_DIS_LO = 2;
   int tsType;
 
   public static boolean isAvayaVendor(String vendor_name)
   {
     if ("ECS".equals(vendor_name)) {
       return true;
     }
     return "AT&T Definity G3".equals(vendor_name);
   }
 
   public static LucentPrivateData create(CSTAPrivate priv, int tsType)
   {
     LucentPrivateData lpriv = null;
 
     if (priv != null) {
       ByteArrayInputStream decodeStream = new ByteArrayInputStream(priv.data);
 
       if ((decodeStream.read() != 2) || (decodeStream.read() != 0))
       {
         return null;
       }
 
       int pdunum = decodeStream.read() << 0;
       pdunum += (decodeStream.read() << 8);
 
       switch (pdunum)
       {
       case 26:
         lpriv = LucentQueryTg.decode(decodeStream);
         break;
       case 9:
         lpriv = LucentSendDTMFToneConfEvent.decode(decodeStream);
         break;
       case 8:
         lpriv = LucentSendDTMFTone.decode(decodeStream);
         break;
       case 71:
         lpriv = LucentV5SendDTMFTone.decode(decodeStream);
         break;
       case 11:
         lpriv = LucentQueryAcdSplit.decode(decodeStream);
         break;
       case 12:
         lpriv = LucentQueryAcdSplitConfEvent.decode(decodeStream);
         break;
       case 13:
         lpriv = LucentQueryAgentLogin.decode(decodeStream);
         break;
       case 14:
         lpriv = LucentQueryAgentLoginConfEvent.decode(decodeStream);
         break;
       case 15:
         lpriv = LucentQueryAgentLoginResp.decode(decodeStream);
         break;
       case 17:
         lpriv = LucentQueryAgentStateConfEvent.decode(decodeStream);
         break;
       case 88:
         lpriv = LucentV5QueryAgentStateConfEvent.decode(decodeStream);
         break;
       case 19:
         lpriv = LucentCallClassifierInfo.decode(decodeStream);
         break;
       case 20:
         lpriv = LucentQueryDeviceInfoConfEvent.decode(decodeStream);
         break;
       case 98:
         lpriv = LucentV5QueryDeviceInfoConfEvent.decode(decodeStream);
         break;
       case 21:
         lpriv = LucentQueryMwiConfEvent.decode(decodeStream);
         break;
       case 23:
         lpriv = LucentQueryStationStatusConfEvent.decode(decodeStream);
         break;
       case 25:
         lpriv = LucentQueryTodConfEvent.decode(decodeStream);
         break;
       case 27:
         lpriv = LucentTrunkGroupInfo.decode(decodeStream);
         break;
       case 33:
         lpriv = LucentMonitorCallConfEvent.decode(decodeStream);
         break;
       case 94:
         lpriv = LucentV5MonitorCallConfEvent.decode(decodeStream);
         break;
       case 34:
         lpriv = LucentCallClearedEvent.decode(decodeStream);
         break;
       case 36:
         lpriv = LucentConnectionClearedEvent.decode(decodeStream);
         break;
       case 38:
         lpriv = LucentEnteredDigitsEvent.decode(decodeStream);
         break;
       case 40:
         lpriv = LucentNetworkProgressInfo.decode(decodeStream);
         break;
       case 101:
         lpriv = LucentV5NetworkProgressInfo.decode(decodeStream);
         break;
       case 42:
         lpriv = LucentRouteRequestEvent.decode(decodeStream);
         break;
       case 83:
         lpriv = LucentV5RouteRequestEvent.decode(decodeStream);
         break;
       case 44:
         lpriv = LucentPrivateRouteUsedEvent.decode(decodeStream);
         break;
       case 47:
         lpriv = LucentOriginatedEvent.decode(decodeStream);
         break;
       case 48:
         lpriv = LucentLoggedOnEvent.decode(decodeStream);
         break;
       case 79:
         lpriv = LucentLoggedOffEvent.decode(decodeStream);
         break;
       case 50:
         lpriv = LucentQueryDeviceNameConfEvent.decode(decodeStream);
         break;
       case 89:
         lpriv = LucentV5QueryDeviceNameConfEvent.decode(decodeStream);
         break;
       case 59:
         lpriv = LucentConferencedEvent.decode(decodeStream);
         break;
       case 78:
         lpriv = LucentV5ConferencedEvent.decode(decodeStream);
         break;
       case 60:
         lpriv = LucentDeliveredEvent.decode(decodeStream);
         break;
       case 80:
         lpriv = LucentV5DeliveredEvent.decode(decodeStream);
         break;
       case 61:
         lpriv = LucentEstablishedEvent.decode(decodeStream);
         break;
       case 81:
         lpriv = LucentV5EstablishedEvent.decode(decodeStream);
         break;
       case 95:
         lpriv = LucentServiceInitiatedEvent.decode(decodeStream);
         break;
       case 62:
         lpriv = LucentTransferredEvent.decode(decodeStream);
         break;
       case 82:
         lpriv = LucentV5TransferredEvent.decode(decodeStream);
         break;
       case 107:
         lpriv = LucentV6ConferencedEvent.decode(decodeStream);
         break;
       case 115:
         lpriv = LucentV6ConnectionClearedEvent.decode(decodeStream);
         break;
       case 117:
         lpriv = LucentV6DeliveredEvent.decode(decodeStream);
         break;
       case 118:
         lpriv = LucentV6EstablishedEvent.decode(decodeStream);
         break;
       case 119:
         lpriv = LucentV6OriginatedEvent.decode(decodeStream);
         break;
       case 105:
         lpriv = LucentV6RouteRequestEvent.decode(decodeStream);
         break;
       case 106:
         lpriv = LucentV6TransferredEvent.decode(decodeStream);
         break;
       case 125:
         lpriv = LucentV7QueryDeviceNameConfEvent.decode(decodeStream);
         break;
       case 127:
         lpriv = LucentV7GetAPICapsConfEvent.decode(decodeStream);
         break;
       case 128:
         lpriv = LucentV7DeliveredEvent.decode(decodeStream);
         break;
       case 129:
         lpriv = LucentV7EstablishedEvent.decode(decodeStream);
         break;
       case 130:
         lpriv = LucentQueuedEvent.decode(decodeStream);
         break;
       case 133:
         lpriv = LucentV7ConferencedEvent.decode(decodeStream);
         break;
       case 134:
         lpriv = LucentV7ConnectionClearedEvent.decode(decodeStream);
         break;
       case 131:
         lpriv = LucentV7RouteRequestEvent.decode(decodeStream);
         break;
       case 132:
         lpriv = LucentV7TransferredEvent.decode(decodeStream);
         break;
       case 135:
         lpriv = LucentDivertedEvent.decode(decodeStream);
         break;
       case 136:
         lpriv = LucentV7NetworkProgressInfo.decode(decodeStream);
         break;
       case 141:
         lpriv = LucentV8FailedEvent.decode(decodeStream);
         break;
       case 137:
         lpriv = LucentFailedEvent.decode(decodeStream);
         break;
       case 138:
         lpriv = LucentSnapshotCallInfoConfEvent.decode(decodeStream);
         break;
       case 64:
         lpriv = LucentGetAPICapsConfEvent.decode(decodeStream);
         break;
       case 97:
         lpriv = LucentV5GetAPICapsConfEvent.decode(decodeStream);
         break;
       case 75:
         lpriv = LucentSetBillRateConfEvent.decode(decodeStream);
         break;
       case 100:
         lpriv = LucentSetAdviceOfChargeConfEvent.decode(decodeStream);
         break;
       case 96:
         lpriv = LucentChargeAdvice.decode(decodeStream);
         break;
       case 69:
         lpriv = LucentSelectiveListeningRetrieve.decode(decodeStream);
         break;
       case 70:
         lpriv = LucentSelectiveListeningRetrieveConfEvent.decode(decodeStream);
         break;
       case 68:
         lpriv = LucentSelectiveListeningHoldConfEvent.decode(decodeStream);
         break;
       case 67:
         lpriv = LucentSelectiveListeningHold.decode(decodeStream);
         break;
       case 49:
         lpriv = LucentQueryDeviceName.decode(decodeStream);
         break;
       case 76:
         lpriv = LucentQueryUcid.decode(decodeStream);
         break;
       case 77:
         lpriv = LucentQueryUcidConfEvent.decode(decodeStream);
         break;
       case 91:
         lpriv = LucentTransferCallConfEvent.decode(decodeStream);
         break;
       case 84:
         lpriv = LucentConsultationCallConfEvent.decode(decodeStream);
         break;
       case 85:
         lpriv = LucentMakeCallConfEvent.decode(decodeStream);
         break;
       case 65:
         lpriv = LucentSingleStepConferenceCall.decode(decodeStream);
         break;
       case 66:
         lpriv = LucentSingleStepConferenceCallConfEvent.decode(decodeStream);
         break;
       case 142:
         lpriv = LucentSingleStepTransferCall.decode(decodeStream);
         break;
       case 143:
         lpriv = LucentSingleStepTransferCallConfEvent.decode(decodeStream);
         break;
       case 90:
         lpriv = LucentConferenceCallConfEvent.decode(decodeStream);
         break;
       case 86:
         lpriv = LucentMakePredictiveCallConfEvent.decode(decodeStream);
         break;
       case 102:
         lpriv = LucentV6SetAgentState.decode(decodeStream, null);
         break;
       case 103:
         lpriv = LucentSetAgentStateConfEvent.decode(decodeStream);
         break;
       case 104:
         lpriv = LucentV6QueryAgentStateConfEvent.decode(decodeStream);
         break;
       case 124:
         lpriv = LucentAgentModeChangeEvent.decode(decodeStream);
         break;
       case 121:
         lpriv = LucentPChallenge.decode(decodeStream);
         break;
       case 139:
         lpriv = LucentUseUnifiedDesktopLicense.decode(decodeStream);
         break;
       case 140:
         lpriv = LucentUseUnifiedDesktopLicenseConfEvent.decode(decodeStream);
         break;
       case 73:
         lpriv = LucentLinkStatusEvent.decode(decodeStream);
         break;
       case 10:
       case 16:
       case 18:
       case 22:
       case 24:
       case 28:
       case 29:
       case 30:
       case 31:
       case 32:
       case 35:
       case 37:
       case 39:
       case 41:
       case 43:
       case 45:
       case 46:
       case 51:
       case 52:
       case 53:
       case 54:
       case 55:
       case 56:
       case 57:
       case 58:
       case 63:
       case 72:
       case 74:
       case 87:
       case 92:
       case 93:
       case 99:
       case 108:
       case 109:
       case 110:
       case 111:
       case 112:
       case 113:
       case 114:
       case 116:
       case 120:
       case 122:
       case 123:
       case 126:
       default:
         log.info(" data PDU " + pdunum + " not decoded");
         return null;
       }
 
       lpriv.tsType = tsType;
 
       if (log.isDebugEnabled()) {
         for (String str : lpriv.print()) {
           log.debug(str);
         }
       }
     }
     return lpriv;
   }
 
   public void encodeLookahead(LucentLookaheadInfo lookInfo, OutputStream memberStream)
   {
     LucentLookaheadInfo.encode(lookInfo, memberStream);
   }
 
   public LucentLookaheadInfo decodeLookahead(InputStream memberStream)
   {
     return LucentLookaheadInfo.decode(memberStream);
   }
 
   public void encodeOCI(LucentOriginalCallInfo callInfo, OutputStream memberStream)
   {
     LucentOriginalCallInfo.encode(callInfo, memberStream);
   }
 
   public LucentOriginalCallInfo decodeOCI(InputStream memberStream)
   {
     return LucentOriginalCallInfo.decode(memberStream);
   }
 
   public CSTAPrivate makeTsapiPrivate()
   {
     if (log.isDebugEnabled()) {
       for (String str : print()) {
         log.debug(str);
       }
     }
     ByteArrayOutputStream encodeStream = new ByteArrayOutputStream();
     int pdunum = getPDU();
 
     encodeStream.write(2);
     encodeStream.write(0);
 
     encodeStream.write(pdunum >> 0 & 0xFF);
     encodeStream.write(pdunum >> 8 & 0xFF);
     try
     {
       encode(encodeStream);
     }
     catch (Exception e) {
     }
     return new CSTAPrivate(encodeStream.toByteArray(), true);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentPrivateData
 * JD-Core Version:    0.5.4
 */