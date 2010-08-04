/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class TSTrunk
/*     */ {
/*  16 */   private static Logger log = Logger.getLogger(TSTrunk.class);
/*     */   TSProviderImpl provider;
/*     */   String name;
/*     */   String groupName;
/*     */   String memberName;
/*     */   TSCall call;
/*     */   int state;
/*     */   int type;
/*     */   TSConnection connection;
/*     */ 
/*     */   void dump(String indent)
/*     */   {
/*  20 */     log.trace(indent + "***** TRUNK DUMP *****");
/*  21 */     log.trace(indent + "TSTrunk: " + this);
/*  22 */     log.trace(indent + "TSTrunk name: " + this.name);
/*  23 */     log.trace(indent + "TSTrunk state: " + this.state);
/*  24 */     log.trace(indent + "TSTrunk call: " + this.call);
/*  25 */     log.trace(indent + "TSTrunk groupName: " + this.groupName);
/*  26 */     log.trace(indent + "TSTrunk memberName: " + this.memberName);
/*  27 */     log.trace(indent + "***** TRUNK DUMP END *****");
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  33 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getGroupName()
/*     */   {
/*  39 */     return this.groupName;
/*     */   }
/*     */ 
/*     */   public String getMemberName()
/*     */   {
/*  45 */     return this.memberName;
/*     */   }
/*     */ 
/*     */   public void setGroupName(String _name)
/*     */   {
/*  51 */     if ((_name == null) || (_name == ""))
/*     */       return;
/*  53 */     this.groupName = _name;
/*     */   }
/*     */ 
/*     */   public void setMemberName(String _name)
/*     */   {
/*  60 */     if ((_name == null) || (_name == ""))
/*     */       return;
/*  62 */     this.memberName = _name;
/*     */   }
/*     */ 
/*     */   public int getState()
/*     */   {
/*  69 */     return this.state;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/*  75 */     return this.type;
/*     */   }
/*     */ 
/*     */   public TSCall getTSCall()
/*     */   {
/*  81 */     return this.call;
/*     */   }
/*     */ 
/*     */   public TSProviderImpl getTSProviderImpl()
/*     */   {
/*  87 */     return this.provider;
/*     */   }
/*     */ 
/*     */   boolean setCall(TSCall _call, Vector<TSEvent> eventList)
/*     */   {
/*  96 */     synchronized (this)
/*     */     {
/*  98 */       if (this.call == _call)
/*     */       {
/* 100 */         return false;
/*     */       }
/*     */ 
/* 103 */       if (this.call != null)
/*     */       {
/* 105 */         this.call.removeTrunk(this, null);
/*     */       }
/* 107 */       this.call = _call;
/*     */     }
/* 109 */     setState(2, eventList);
/*     */ 
/* 115 */     this.provider.addTrunkToHash(this.name, this);
/*     */ 
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   void unsetCall(Vector<TSEvent> eventList)
/*     */   {
/* 126 */     setState(1, eventList);
/*     */   }
/*     */ 
/*     */   TSTrunk(TSProviderImpl _provider, String _name, int _type)
/*     */   {
/* 137 */     this.provider = _provider;
/* 138 */     this.name = _name;
/* 139 */     this.type = _type;
/* 140 */     this.state = 0;
/*     */ 
/* 142 */     if (this.name == null)
/*     */     {
/* 144 */       this.groupName = (this.memberName = null);
/*     */     }
/*     */     else
/*     */     {
/* 148 */       int colonPos = this.name.indexOf(':');
/* 149 */       if ((colonPos <= 0) || (colonPos >= this.name.length() - 1))
/*     */       {
/* 151 */         this.groupName = (this.memberName = null);
/*     */       }
/*     */       else
/*     */       {
/* 155 */         this.groupName = this.name.substring(0, colonPos - 1);
/* 156 */         this.memberName = this.name.substring(colonPos + 1);
/*     */       }
/*     */     }
/* 159 */     this.provider.addTrunkToHash(this.name, this);
/* 160 */     log.info("Trunk object= " + this + " being created with name " + this.name + " (group:member = " + getGroupAndMember() + ") for " + this.provider);
/*     */   }
/*     */ 
/*     */   String getGroupAndMember()
/*     */   {
/*     */     String g_m;
/* 169 */     if (this.groupName == null)
/*     */     {
/* 171 */       g_m = "-:";
/*     */     }
/*     */     else
/*     */     {
/* 175 */       g_m = this.groupName + ":";
/*     */     }
/* 177 */     if (this.memberName == null)
/*     */     {
/* 179 */       g_m = g_m + "-";
/*     */     }
/*     */     else
/*     */     {
/* 183 */       g_m = g_m + this.memberName;
/*     */     }
/* 185 */     return g_m;
/*     */   }
/*     */ 
/*     */   void setState(int _state, Vector<TSEvent> eventList)
/*     */   {
/* 193 */     synchronized (this)
/*     */     {
/* 196 */       if (this.state == _state)
/*     */       {
/* 198 */         return;
/*     */       }
/*     */ 
/* 201 */       this.state = _state;
/*     */     }
/*     */ 
/* 204 */     switch (this.state)
/*     */     {
/*     */     case 2:
/* 207 */       if (eventList == null)
/*     */         return;
/* 209 */       eventList.addElement(new TSEvent(54, this)); break;
/*     */     case 1:
/* 213 */       if (eventList != null)
/*     */       {
/* 215 */         eventList.addElement(new TSEvent(55, this));
/*     */       }
/*     */ 
/* 218 */       delete();
/*     */     }
/*     */   }
/*     */ 
/*     */   void setType(int _type)
/*     */   {
/* 231 */     this.type = _type;
/*     */   }
/*     */ 
/*     */   synchronized void delete()
/*     */   {
/* 238 */     log.info("Trunk object= " + this + " being deleted" + " for " + this.provider);
/*     */ 
/* 241 */     this.provider.deleteTrunkFromHash(this.name);
/*     */   }
/*     */ 
/*     */   public void setTSConnection(TSConnection _conn)
/*     */   {
/* 248 */     this.connection = _conn;
/*     */   }
/*     */ 
/*     */   public TSConnection getTSConnection()
/*     */   {
/* 254 */     return this.connection;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSTrunk
 * JD-Core Version:    0.5.4
 */