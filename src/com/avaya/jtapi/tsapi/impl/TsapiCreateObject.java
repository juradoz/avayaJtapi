/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSAgent;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ 
/*     */ public final class TsapiCreateObject
/*     */ {
/*     */   public static Object getTsapiObject(Object _object, boolean isAddress)
/*     */   {
/*  25 */     Object retval = null;
/*  26 */     TsapiTrace.traceEntry("getTsapiObject[Object _object, boolean isAddress]", null);
/*  27 */     if (_object instanceof TSProviderImpl)
/*     */     {
/*  29 */       TSProviderImpl TSProviderImpl = (TSProviderImpl)_object;
/*  30 */       if (TSProviderImpl.isLucentV7())
/*     */       {
/*  32 */         retval = new LucentV7ProviderImpl(TSProviderImpl);
/*     */       }
/*  34 */       else if (TSProviderImpl.isLucentV5())
/*     */       {
/*  36 */         retval = new LucentV5ProviderImpl(TSProviderImpl);
/*     */       }
/*  38 */       else if (TSProviderImpl.isLucent())
/*     */       {
/*  40 */         retval = new LucentProviderImpl(TSProviderImpl);
/*     */       }
/*     */       else
/*     */       {
/*  44 */         retval = new TsapiProvider(TSProviderImpl);
/*     */       }
/*     */     }
/*  47 */     else if (_object instanceof TSCall)
/*     */     {
/*  49 */       TSCall tsCall = (TSCall)_object;
/*  50 */       if (tsCall.getTSProviderImpl().isLucentV7())
/*     */       {
/*  52 */         retval = new LucentV7CallImpl(tsCall);
/*     */       }
/*  54 */       else if (tsCall.getTSProviderImpl().isLucentV5())
/*     */       {
/*  56 */         retval = new LucentV5CallImpl(tsCall);
/*     */       }
/*  58 */       else if (tsCall.getTSProviderImpl().isLucent())
/*     */       {
/*  60 */         retval = new LucentCallEx2Impl(tsCall);
/*     */       }
/*     */       else
/*     */       {
/*  64 */         retval = new TsapiCall(tsCall);
/*     */       }
/*     */     }
/*  67 */     else if (_object instanceof TSConnection)
/*     */     {
/*  69 */       TSConnection tsConn = (TSConnection)_object;
/*  70 */       if (tsConn.getTSProviderImpl().isLucentV6())
/*     */       {
/*  72 */         if (isAddress)
/*     */         {
/*  74 */           if (tsConn.getTSDevice().getDeviceType() == 1)
/*  75 */             retval = new LucentV5ACDManagerConnectionImpl(tsConn);
/*  76 */           else if (tsConn.getTSDevice().getDeviceType() == 2)
/*  77 */             retval = new LucentV5ACDConnectionImpl(tsConn);
/*     */           else {
/*  79 */             retval = new LucentV6ConnectionImpl(tsConn);
/*     */           }
/*     */         }
/*     */         else {
/*  83 */           retval = new LucentV5TerminalConnectionExImpl(tsConn);
/*     */         }
/*     */       }
/*  86 */       else if (tsConn.getTSProviderImpl().isLucentV5())
/*     */       {
/*  88 */         if (isAddress)
/*     */         {
/*  90 */           if (tsConn.getTSDevice().getDeviceType() == 1)
/*  91 */             retval = new LucentV5ACDManagerConnectionImpl(tsConn);
/*  92 */           else if (tsConn.getTSDevice().getDeviceType() == 2)
/*  93 */             retval = new LucentV5ACDConnectionImpl(tsConn);
/*     */           else {
/*  95 */             retval = new LucentV5ConnectionImpl(tsConn);
/*     */           }
/*     */         }
/*     */         else {
/*  99 */           retval = new LucentV5TerminalConnectionExImpl(tsConn);
/*     */         }
/*     */       }
/* 102 */       else if (tsConn.getTSProviderImpl().isLucent())
/*     */       {
/* 104 */         if (isAddress)
/*     */         {
/* 106 */           if (tsConn.getTSDevice().getDeviceType() == 1)
/* 107 */             retval = new LucentACDManagerConnectionImpl(tsConn);
/* 108 */           else if (tsConn.getTSDevice().getDeviceType() == 2)
/* 109 */             retval = new LucentACDConnectionImpl(tsConn);
/*     */           else {
/* 111 */             retval = new LucentConnectionImpl(tsConn);
/*     */           }
/*     */         }
/*     */         else {
/* 115 */           retval = new LucentTerminalConnectionImpl(tsConn);
/*     */         }
/*     */ 
/*     */       }
/* 121 */       else if (isAddress)
/*     */       {
/* 123 */         if (tsConn.getTSDevice().getDeviceType() == 1)
/* 124 */           retval = new TsapiACDManagerConnection(tsConn);
/* 125 */         else if (tsConn.getTSDevice().getDeviceType() == 2)
/* 126 */           retval = new TsapiACDConnection(tsConn);
/*     */         else {
/* 128 */           retval = new TsapiConnection(tsConn);
/*     */         }
/*     */       }
/*     */       else {
/* 132 */         retval = new TsapiTerminalConnection(tsConn);
/*     */       }
/*     */ 
/*     */     }
/* 136 */     else if (_object instanceof TSDevice)
/*     */     {
/* 138 */       TSDevice tsDevice = (TSDevice)_object;
/* 139 */       if (tsDevice.getTSProviderImpl().isLucentV7())
/*     */       {
/* 141 */         if (isAddress)
/*     */         {
/* 143 */           if (tsDevice.getDeviceType() == 1)
/*     */           {
/* 145 */             if (tsDevice.getTSProviderImpl().getMonitorCallsViaDevice() == true)
/* 146 */               retval = new LucentV7ACDManagerAddressImpl(tsDevice);
/*     */             else
/* 148 */               retval = new LucentACDManagerAddressImpl(tsDevice);
/*     */           }
/* 150 */           else if (tsDevice.getDeviceType() == 2)
/* 151 */             retval = new LucentACDAddressImpl(tsDevice);
/*     */           else {
/* 153 */             retval = new LucentAddressImpl(tsDevice);
/*     */           }
/*     */ 
/*     */         }
/* 159 */         else if (tsDevice.isTerminal())
/* 160 */           retval = new LucentV5TerminalExImpl(tsDevice);
/*     */         else {
/* 162 */           retval = null;
/*     */         }
/*     */       }
/* 165 */       else if (tsDevice.getTSProviderImpl().isLucentV5())
/*     */       {
/* 167 */         if (isAddress)
/*     */         {
/* 169 */           if (tsDevice.getDeviceType() == 1)
/* 170 */             retval = new LucentACDManagerAddressImpl(tsDevice);
/* 171 */           else if (tsDevice.getDeviceType() == 2)
/* 172 */             retval = new LucentACDAddressImpl(tsDevice);
/*     */           else {
/* 174 */             retval = new LucentAddressImpl(tsDevice);
/*     */           }
/*     */ 
/*     */         }
/* 180 */         else if (tsDevice.isTerminal())
/* 181 */           retval = new LucentV5TerminalExImpl(tsDevice);
/*     */         else {
/* 183 */           retval = null;
/*     */         }
/*     */       }
/* 186 */       else if (tsDevice.getTSProviderImpl().isLucent())
/*     */       {
/* 188 */         if (isAddress)
/*     */         {
/* 190 */           if (tsDevice.getDeviceType() == 1)
/* 191 */             retval = new LucentACDManagerAddressImpl(tsDevice);
/* 192 */           else if (tsDevice.getDeviceType() == 2)
/* 193 */             retval = new LucentACDAddressImpl(tsDevice);
/*     */           else {
/* 195 */             retval = new LucentAddressImpl(tsDevice);
/*     */           }
/*     */ 
/*     */         }
/* 201 */         else if (tsDevice.isTerminal())
/* 202 */           retval = new LucentTerminalImpl(tsDevice);
/*     */         else {
/* 204 */           retval = null;
/*     */         }
/*     */ 
/*     */       }
/* 209 */       else if (isAddress)
/*     */       {
/* 211 */         if (tsDevice.getDeviceType() == 1)
/* 212 */           retval = new TsapiACDManagerAddress(tsDevice);
/* 213 */         else if (tsDevice.getDeviceType() == 2)
/* 214 */           retval = new TsapiACDAddress(tsDevice);
/*     */         else {
/* 216 */           retval = new TsapiAddress(tsDevice);
/*     */         }
/*     */ 
/*     */       }
/* 222 */       else if (tsDevice.isTerminal())
/* 223 */         retval = new TsapiTerminal(tsDevice);
/*     */       else {
/* 225 */         retval = null;
/*     */       }
/*     */ 
/*     */     }
/* 229 */     else if (_object instanceof TSAgent)
/*     */     {
/* 231 */       TSAgent tsAgent = (TSAgent)_object;
/* 232 */       if (tsAgent.getTSProviderImpl().isLucentV7())
/*     */       {
/* 234 */         retval = new LucentV7AgentImpl(tsAgent);
/*     */       }
/* 236 */       else if (tsAgent.getTSProviderImpl().isLucentV6())
/*     */       {
/* 238 */         retval = new LucentV6AgentImpl(tsAgent);
/*     */       }
/* 240 */       else if (tsAgent.getTSProviderImpl().isLucent())
/*     */       {
/* 242 */         retval = new LucentAgentImpl(tsAgent);
/*     */       }
/*     */       else
/*     */       {
/* 246 */         retval = new TsapiAgent(tsAgent);
/*     */       }
/*     */     }
/* 249 */     else if (_object instanceof TSRouteSession)
/*     */     {
/* 251 */       TSRouteSession tsSession = (TSRouteSession)_object;
/* 252 */       if (tsSession.getTSProviderImpl().isLucentV7())
/*     */       {
/* 254 */         retval = new LucentV7RouteSessionImpl(tsSession);
/*     */       }
/* 256 */       else if (tsSession.getTSProviderImpl().isLucentV5())
/*     */       {
/* 258 */         retval = new LucentV5RouteSessionImpl(tsSession);
/*     */       }
/* 260 */       else if (tsSession.getTSProviderImpl().isLucent())
/*     */       {
/* 262 */         retval = new LucentRouteSessionImpl(tsSession);
/*     */       }
/*     */       else
/*     */       {
/* 266 */         retval = new TsapiRouteSession(tsSession);
/*     */       }
/*     */     }
/* 269 */     else if (_object instanceof TSTrunk)
/*     */     {
/* 271 */       TSTrunk tsTrunk = (TSTrunk)_object;
/* 272 */       if (tsTrunk.getTSProviderImpl().isLucentV6())
/*     */       {
/* 274 */         retval = new LucentTrunkImpl(tsTrunk);
/*     */       }
/*     */       else
/*     */       {
/* 278 */         retval = new TsapiTrunkImpl(tsTrunk);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 283 */       retval = null;
/*     */     }
/* 285 */     TsapiTrace.traceExit("getTsapiObject[Object _object, boolean isAddress]", null);
/* 286 */     return retval;
/*     */   }
/*     */ 
/*     */   TsapiCreateObject()
/*     */   {
/* 292 */     TsapiTrace.traceConstruction(this, TsapiCreateObject.class);
/*     */   }
/*     */ 
/*     */   protected void finalize() throws Throwable
/*     */   {
/* 297 */     super.finalize();
/* 298 */     TsapiTrace.traceDestruction(this, TsapiCreateObject.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiCreateObject
 * JD-Core Version:    0.5.4
 */