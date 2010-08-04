/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
/*     */ import java.util.Vector;
import org.apache.log4j.Logger;
/*     */ 
/*     */ final class UnsolicitedSnapshotCallConfHandler
/*     */   implements SnapshotCallExtraConfHandler
/*     */ {
/*  25 */   private static Logger log = Logger.getLogger(UnsolicitedSnapshotCallConfHandler.class);
/*     */   TSEventHandler eventHandler;
/*     */   int eventType;
/*     */   int cause;
/*     */   CSTAExtendedDeviceID subjectDeviceID;
/*     */   TSDevice subjectDevice;
/*     */   CSTAConnectionID connID;
/*     */   TSCall call;
/*     */   Object privateData;
/*     */   CSTAExtendedDeviceID callingDeviceID;
/*     */   CSTAExtendedDeviceID calledDeviceID;
/*     */   CSTAExtendedDeviceID lastRedirectionDeviceID;
/*     */   int connState;
/*     */   int termConnState;
/*     */   TSDevice connectionDevice;
/*     */ 
/*     */   UnsolicitedSnapshotCallConfHandler(TSEventHandler _eventHandler, int _eventType, int _cause, CSTAExtendedDeviceID _subjectDeviceID, TSDevice _subjectDevice, CSTAConnectionID _connID, TSCall _call, Object _privateData, CSTAExtendedDeviceID _callingDeviceID, CSTAExtendedDeviceID _calledDeviceID, CSTAExtendedDeviceID _lastRedirectionDeviceID, int _connState, int _termConnState, TSDevice _connectionDevice)
/*     */   {
/*  49 */     this.eventHandler = _eventHandler;
/*  50 */     this.eventType = _eventType;
/*  51 */     this.cause = _cause;
/*  52 */     this.subjectDeviceID = _subjectDeviceID;
/*  53 */     this.subjectDevice = _subjectDevice;
/*  54 */     this.connID = _connID;
/*  55 */     this.call = _call;
/*  56 */     this.privateData = _privateData;
/*  57 */     this.callingDeviceID = _callingDeviceID;
/*  58 */     this.calledDeviceID = _calledDeviceID;
/*  59 */     this.lastRedirectionDeviceID = _lastRedirectionDeviceID;
/*  60 */     this.connState = _connState;
/*  61 */     this.termConnState = _termConnState;
/*  62 */     this.connectionDevice = _connectionDevice;
/*     */   }
/*     */ 
/*     */   public Object handleConf(boolean rc, Vector<TSEvent> eventList, Object _privateData)
/*     */   {
/*  67 */     if (this.call.getTSState() == 34)
/*     */     {
/*  69 */       return null;
/*     */     }
/*     */ 
/*  72 */     if (this.call.getNeedRedoSnapshotCall())
/*     */     {
/*  74 */       this.call.setNeedRedoSnapshotCall(false);
/*  75 */       log.info("redo snapshot call");
/*  76 */       this.call.doSnapshot(this.connID, this, false);
/*  77 */       return null;
/*     */     }
/*     */ 
/*  80 */     this.call.setSnapshotCallConfPending(false);
/*     */ 
/*  82 */     log.debug("UnsolicitedSnapshotCallConfHandler " + this + " handling conf for call " + this.call + ", eventType " + this.eventType + ", subjectDevice " + this.subjectDevice + ", connID " + this.connID + ", rc " + rc + ", cause " + this.cause + ", privateData " + this.privateData + ", provider " + this.call.getTSProviderImpl());
/*     */ 
/*  92 */     if (this.privateData == null) {
/*  93 */       this.privateData = _privateData;
/*     */     }
/*     */ 
/*  99 */     if (eventList == null)
/*     */     {
/* 101 */       eventList = new Vector();
/*     */     }
/*     */     TSConnection connection;
/*     */     int oldConnState;
/*     */     int oldTermConnState;
/* 103 */     if (rc)
/*     */     {
/* 105 */       if (eventList.size() == 0)
/*     */       {
/* 108 */         this.call.getSnapshot(eventList);
/*     */       }
/*     */ 
/* 111 */       connection = this.eventHandler.provider.createTerminalConnection(this.connID, this.subjectDevice, null, this.connectionDevice);
/* 112 */       oldConnState = connection.getCallControlConnState();
/* 113 */       oldTermConnState = connection.getCallControlTermConnState();
/*     */ 
/* 115 */       boolean isTermConn = connection.isTerminalConnection();
/* 116 */       TSConnection connToCheck = null;
/* 117 */       TSConnection tcToCheck = null;
/*     */ 
/* 119 */       if (isTermConn)
/*     */       {
/* 122 */         connToCheck = connection.getTSConn();
/* 123 */         tcToCheck = connection;
/*     */       }
/*     */       else
/*     */       {
/* 127 */         connToCheck = connection;
/*     */       }
/*     */ 
/* 133 */       TSEvent nEv = null;
/*     */ 
/* 135 */       boolean foundConn = false;
/* 136 */       boolean foundTC = false;
/*     */ 
/* 138 */       for (int j = 0; j < eventList.size(); ++j)
/*     */       {
/* 140 */         nEv = (TSEvent)eventList.elementAt(j);
/*     */ 
/* 142 */         if ((connToCheck != nEv.getEventTarget()) || (nEv.getEventType() != 6)) {
/*     */           continue;
/*     */         }
/* 145 */         foundConn = true;
/* 146 */         if ((this.connState != 83) || (oldConnState == 83) || (connToCheck.getCallControlConnState() == 83)) {
/*     */           break;
/*     */         }
/*     */ 
/* 150 */         log.debug("adding ConnAlertingEv to events derived from SnapshotCallConf for call " + this.call);
/*     */ 
/* 152 */         if (j + 1 >= eventList.size())
/*     */         {
/* 154 */           eventList.addElement(new TSEvent(9, connToCheck));
/* 155 */           eventList.addElement(new TSEvent(26, connToCheck)); break;
/*     */         }
/*     */ 
/* 159 */         eventList.insertElementAt(new TSEvent(9, connToCheck), j + 1);
/* 160 */         eventList.insertElementAt(new TSEvent(26, connToCheck), j + 1); break;
/*     */       }
/*     */ 
/* 166 */       if (isTermConn)
/*     */       {
/* 168 */         for (int j = 0; j < eventList.size(); ++j)
/*     */         {
/* 170 */           nEv = (TSEvent)eventList.elementAt(j);
/*     */ 
/* 172 */           if ((tcToCheck != nEv.getEventTarget()) || (nEv.getEventType() != 13)) {
/*     */             continue;
/*     */           }
/* 175 */           foundTC = true;
/* 176 */           if ((this.termConnState != 97) || (oldTermConnState == 97) || (tcToCheck.getCallControlTermConnState() == 97)) {
/*     */             break;
/*     */           }
/*     */ 
/* 180 */           if (j + 1 >= eventList.size())
/*     */           {
/* 182 */             eventList.addElement(new TSEvent(15, tcToCheck));
/* 183 */             eventList.addElement(new TSEvent(35, tcToCheck)); break;
/*     */           }
/*     */ 
/* 187 */           eventList.insertElementAt(new TSEvent(15, tcToCheck), j + 1);
/* 188 */           eventList.insertElementAt(new TSEvent(35, tcToCheck), j + 1); break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 196 */       if (isTermConn)
/*     */       {
/* 198 */         if (!foundConn)
/*     */         {
/* 200 */           connToCheck.setConnectionState(this.connState, null);
/* 201 */           if (!foundTC)
/*     */           {
/* 203 */             tcToCheck.setTermConnState(this.termConnState, null);
/*     */           }
/* 205 */           Vector snapEventList = new Vector();
/* 206 */           connToCheck.getSnapshot(snapEventList);
/* 207 */           int index = 0;
/* 208 */           if (eventList.size() == 0)
/*     */           {
/* 210 */             eventList = snapEventList;
/*     */           }
/*     */           else
/*     */           {
/* 214 */             if (((TSEvent)eventList.elementAt(0)).getEventType() == 4)
/*     */             {
/* 216 */               ++index;
/*     */             }
/* 218 */             for (int i = 0; i < snapEventList.size(); ++i)
/*     */             {
/* 220 */               eventList.insertElementAt((TSEvent) snapEventList.elementAt(i), index);
/* 221 */               ++index;
/*     */             }
/*     */           }
/*     */         }
/* 225 */         else if (!foundTC)
/*     */         {
/* 227 */           tcToCheck.setTermConnState(this.termConnState, null);
/* 228 */           Vector snapEventList = new Vector();
/* 229 */           tcToCheck.getSnapshot(snapEventList);
/* 230 */           int index = 0;
/* 231 */           if (eventList.size() == 0)
/*     */           {
/* 233 */             eventList = snapEventList;
/*     */           }
/*     */           else
/*     */           {
/* 237 */             if (((TSEvent)eventList.elementAt(0)).getEventType() == 4)
/*     */             {
/* 239 */               ++index;
/*     */             }
/* 241 */             for (int i = 0; i < snapEventList.size(); ++i)
/*     */             {
/* 243 */               eventList.insertElementAt((TSEvent) snapEventList.elementAt(i), index);
/* 244 */               ++index;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 249 */       else if (!foundConn)
/*     */       {
/* 251 */         connToCheck.setConnectionState(this.connState, null);
/* 252 */         Vector snapEventList = new Vector();
/* 253 */         connToCheck.getSnapshot(snapEventList);
/* 254 */         int index = 0;
/* 255 */         if (eventList.size() == 0)
/*     */         {
/* 257 */           eventList = snapEventList;
/*     */         }
/*     */         else
/*     */         {
/* 261 */           if (((TSEvent)eventList.elementAt(0)).getEventType() == 4)
/*     */           {
/* 263 */             ++index;
/*     */           }
/* 265 */           for (int i = 0; i < snapEventList.size(); ++i)
/*     */           {
/* 267 */             eventList.insertElementAt((TSEvent) snapEventList.elementAt(i), index);
/* 268 */             ++index;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 281 */       for (int j = 0; j < eventList.size(); ++j)
/*     */       {
/* 283 */         nEv = (TSEvent)eventList.elementAt(j);
/*     */ 
/* 285 */         if ((connToCheck != nEv.getEventTarget()) || 
/* 287 */           (!shouldOverrideConnEventCSTACauseValue(nEv))) {
/*     */           continue;
/*     */         }
/*     */ 
/* 291 */         log.debug("overriding cause for TSEvent " + nEv + " (" + nEv.getEventType() + "," + nEv.getEventTarget() + " from eventType " + this.eventType + " with new cause " + this.cause);
/*     */ 
/* 297 */         nEv.setSnapshotCstaCause((short)this.cause);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 302 */       connection = this.eventHandler.provider.createTerminalConnection(this.connID, this.subjectDevice, eventList, this.connectionDevice);
/* 303 */       oldConnState = connection.getCallControlConnState();
/* 304 */       oldTermConnState = connection.getCallControlTermConnState();
/* 305 */       connection.setConnectionState(this.connState, eventList);
/* 306 */       connection.setTermConnState(this.termConnState, eventList);
/*     */     }
/*     */ 
/* 311 */     this.eventHandler.finishConnEvents(null, this.eventType, this.cause, 110, this.subjectDeviceID, this.subjectDevice, connection, this.call, this.privateData, this.callingDeviceID, this.calledDeviceID, this.lastRedirectionDeviceID, this.connState, oldConnState, oldTermConnState, eventList);
/*     */ 
/* 316 */     return this.privateData;
/*     */   }
/*     */ 
/*     */   boolean shouldOverrideConnEventCSTACauseValue(TSEvent eventToModify)
/*     */   {
/* 350 */     boolean result = false;
/*     */ 
/* 352 */     switch (eventToModify.getEventType())
/*     */     {
/*     */     case 6:
/* 366 */       result = eventToModify.getSnapshotCstaCause() == -1;
/* 367 */       break;
/*     */     case 9:
/*     */     case 23:
/*     */     case 26:
/* 372 */       result = this.eventType == 57;
/* 373 */       break;
/*     */     case 10:
/*     */     case 27:
/* 377 */       result = this.eventType == 56;
/* 378 */       break;
/*     */     case 7:
/*     */     case 21:
/* 382 */       result = this.eventType == 59;
/* 383 */       break;
/*     */     case 11:
/*     */     case 24:
/*     */     case 28:
/* 388 */       result = this.eventType == 60;
/* 389 */       break;
/*     */     case 22:
/* 392 */       result = this.eventType == 62;
/* 393 */       break;
/*     */     case 25:
/* 396 */       result = this.eventType == 64;
/* 397 */       break;
/*     */     case 8:
/*     */     case 12:
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*     */     case 16:
/*     */     case 17:
/*     */     case 18:
/*     */     case 19:
/*     */     case 20:
/*     */     default:
/* 400 */       result = false;
/*     */     }
/*     */ 
/* 403 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.UnsolicitedSnapshotCallConfHandler
 * JD-Core Version:    0.5.4
 */