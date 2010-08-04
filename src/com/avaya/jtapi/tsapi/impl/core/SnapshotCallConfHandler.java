/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSUniversalFailureConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallResponseInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAUniversalFailureConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSnapshotCallInfoConfEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.TSErrorMap;
/*      */ import java.util.Vector;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ final class SnapshotCallConfHandler
/*      */   implements ConfHandler
/*      */ {
/* 6266 */   private static Logger log = Logger.getLogger(SnapshotCallConfHandler.class);
/*      */   TSCall call;
/* 6268 */   Vector<SnapshotCallExtraConfHandler> extraHandlerVector = null;
/*      */   boolean synchronous;
/* 6270 */   boolean handled = false;
/* 6271 */   boolean rc = false;
/*      */ 
/*      */   SnapshotCallConfHandler(TSCall _call, SnapshotCallExtraConfHandler _extraHandler, boolean _synchronous)
/*      */   {
/* 6275 */     this.call = _call;
/* 6276 */     this.synchronous = _synchronous;
/* 6277 */     if (!this.synchronous)
/*      */     {
/* 6280 */       synchronized (this.call.callbackAndTypeVector)
/*      */       {
/* 6282 */         this.call.futureAsynchronousSnapshotHandler = this;
/*      */       }
/*      */     }
/* 6285 */     addExtraHandler(_extraHandler);
/*      */   }
/*      */ 
/*      */   void addExtraHandler(SnapshotCallExtraConfHandler _extraHandler)
/*      */   {
/* 6290 */     if (_extraHandler == null)
/*      */     {
/* 6292 */       return;
/*      */     }
/*      */ 
/* 6295 */     if (this.extraHandlerVector == null)
/*      */     {
/* 6297 */       this.extraHandlerVector = new Vector();
/*      */     }
/*      */ 
/* 6300 */     this.extraHandlerVector.addElement(_extraHandler);
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/*      */     try
/*      */     {
/* 6308 */       synchronized (this.call.callbackAndTypeVector)
/*      */       {
/* 6310 */         if (!this.synchronous)
/*      */         {
/* 6312 */           this.call.futureAsynchronousSnapshotHandler = null;
/*      */         }
/* 6314 */         this.call.currentSnapshotHandler = this;
/*      */       }
/* 6316 */       this.handled = true;
/*      */       try
/*      */       {
/* 6319 */         if (event == null)
/*      */         {
/* 6321 */           throw new TsapiPlatformException(4, 0, "no conf event");
/*      */         }
/*      */ 
/* 6324 */         if (event.getEvent() instanceof CSTAUniversalFailureConfEvent)
/*      */         {
/* 6326 */           TSErrorMap.throwCSTAException(((CSTAUniversalFailureConfEvent)event.getEvent()).getError());
/*      */         }
/* 6328 */         if (event.getEvent() instanceof ACSUniversalFailureConfEvent)
/*      */         {
/* 6330 */           TSErrorMap.throwACSException(((ACSUniversalFailureConfEvent)event.getEvent()).getError());
/*      */         }
/* 6332 */         if (!(event.getEvent() instanceof CSTASnapshotCallConfEvent))
/*      */         {
/* 6334 */           throw new TsapiPlatformException(4, 1, "expected CSTASnapshotCallConfEvent");
/*      */         }
/*      */       }
/*      */       catch (TsapiInvalidStateException e)
/*      */       {
/* 6339 */         this.call.setState(34, null);
/* 6340 */         this.call.endCVDObservers(100, null);
/* 6341 */         this.rc = true;
/* 6342 */         return;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 6346 */         this.rc = false;
/* 6347 */         return;
/*      */       }
/*      */ 
/* 6355 */       Vector newConns = new Vector();
/*      */ 
/* 6357 */       CSTASnapshotCallResponseInfo[] info = ((CSTASnapshotCallConfEvent)event.getEvent()).getSnapshotData();
/*      */ 
/* 6359 */       if (info == null) {
/* 6360 */         this.rc = false;
/* 6361 */         return;
/*      */       }
/*      */ 
/* 6364 */       TSConnection connection = null;
/* 6365 */       TSDevice device = null;
/* 6366 */       CSTAExtendedDeviceID extDevID = null;
/*      */ 
/* 6368 */       for (int i = 0; i < info.length; ++i)
/*      */       {
/*      */         try
/*      */         {
/* 6374 */           extDevID = info[i].getDeviceOnCall();
/*      */ 
/* 6376 */           device = this.call.getTSProviderImpl().createDevice(info[i].getDeviceOnCall(), info[i].getCallIdentifier());
/*      */ 
/* 6378 */           if (device == null)
/*      */           {
/*      */             break;
/*      */           }
/*      */ 
/* 6384 */           if (device.isForExternalDeviceMatchingLocalExtensionNumber(extDevID)) {
/* 6385 */             connection = this.call.getTSProviderImpl().createConnection(info[i].getCallIdentifier(), device, null);
/* 6386 */             connection.setDoNotExpectConnectionClearedEvent(true);
/*      */           }
/*      */           else {
/* 6389 */             connection = this.call.getTSProviderImpl().createTerminalConnection(info[i].getCallIdentifier(), device, null, device);
/*      */           }
/*      */ 
/* 6395 */           int oldConnState = connection.getCallControlConnState();
/* 6396 */           int oldTermConnState = connection.getCallControlTermConnState();
/*      */ 
/* 6398 */           if ((oldConnState == 89) || (oldTermConnState == 102))
/*      */           {
/* 6401 */             log.info("SnapshotCallConfHandler.handleConf(): recreating connection " + connection + "; oldConnState=" + oldConnState + ", oldTermConnState=" + oldTermConnState);
/*      */ 
/* 6405 */             connection.delete();
/* 6406 */             this.call.getTSProviderImpl().dumpConn(info[i].getCallIdentifier());
/*      */ 
/* 6408 */             connection = this.call.getTSProviderImpl().createTerminalConnection(info[i].getCallIdentifier(), device, null, device);
/*      */           }
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/* 6413 */           this.rc = false;
/* 6414 */           return;
/*      */         }
/* 6416 */         connection.setStateFromLocalConnState(info[i].getLocalConnectionState());
/*      */ 
/* 6419 */         if (!newConns.contains(connection))
/*      */         {
/* 6421 */           newConns.addElement(connection);
/*      */         }
/*      */ 
/* 6425 */         device.addConnection(connection);
/*      */       }
/*      */ 
/* 6430 */       this.call.replaceConnections(newConns, null);
/*      */ 
/* 6432 */       Vector connections = this.call.getConnections();
/*      */ 
/* 6435 */       boolean found = false;
/* 6436 */       if (this.call.confController != null)
/*      */       {
/* 6439 */         synchronized (connections)
/*      */         {
/* 6441 */           for (int i = 0; i < connections.size(); ++i)
/*      */           {
/* 6443 */             TSConnection conn = (TSConnection)connections.elementAt(i);
/* 6444 */             Vector termConns = conn.getTermConns();
/* 6445 */             if ((termConns == null) || (!termConns.contains(this.call.confController)))
/*      */               continue;
/* 6447 */             found = true;
/* 6448 */             break;
/*      */           }
/*      */         }
/*      */ 
/* 6452 */         if (!found)
/* 6453 */           this.call.confController = null;
/*      */       }
/* 6455 */       found = false;
/* 6456 */       if (this.call.xferController != null)
/*      */       {
/* 6459 */         synchronized (connections)
/*      */         {
/* 6461 */           for (int i = 0; i < connections.size(); ++i)
/*      */           {
/* 6463 */             TSConnection conn = (TSConnection)connections.elementAt(i);
/* 6464 */             Vector termConns = conn.getTermConns();
/* 6465 */             if ((termConns == null) || (!termConns.contains(this.call.xferController)))
/*      */               continue;
/* 6467 */             found = true;
/* 6468 */             break;
/*      */           }
/*      */         }
/*      */ 
/* 6472 */         if (!found)
/* 6473 */           this.call.xferController = null;
/*      */       }
/* 6475 */       this.rc = true;
/*      */ 
/* 6488 */       Object privateData = event.getPrivData();
/*      */ 
/* 6491 */       if (privateData instanceof LucentSnapshotCallInfoConfEvent) {
/* 6492 */         LucentSnapshotCallInfoConfEvent luV7PrivData = (LucentSnapshotCallInfoConfEvent)privateData;
/* 6493 */         this.call.setDeviceHistory(luV7PrivData.getDeviceHistory());
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 6498 */       Vector eventList = null;
/*      */       try
/*      */       {
/* 6501 */         if (this.extraHandlerVector != null)
/*      */         {
/* 6503 */           eventList = new Vector();
/* 6504 */           Object privateData = null;
/* 6505 */           for (int i = 0; i < this.extraHandlerVector.size(); ++i)
/*      */           {
/*      */             try
/*      */             {
/* 6509 */               SnapshotCallExtraConfHandler extraHandler = (SnapshotCallExtraConfHandler)this.extraHandlerVector.elementAt(i);
/*      */ 
/* 6511 */               Object pd = extraHandler.handleConf(this.rc, eventList, privateData);
/* 6512 */               if (pd != null)
/* 6513 */                 privateData = pd;
/*      */             } catch (Exception e) {
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       finally {
/* 6520 */         synchronized (this.call.callbackAndTypeVector)
/*      */         {
/* 6522 */           this.call.currentSnapshotHandler = null;
/*      */         }
/* 6524 */         if ((eventList != null) && (eventList.size() == 0))
/*      */         {
/* 6526 */           eventList = null;
/*      */         }
/* 6528 */         this.call.doCallbackSnapshots(eventList, 110);
/* 6529 */         synchronized (this)
/*      */         {
/* 6531 */           super.notify();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.SnapshotCallConfHandler
 * JD-Core Version:    0.5.4
 */