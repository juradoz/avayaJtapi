 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.TsapiTrunk;
 import com.avaya.jtapi.tsapi.csta1.CSTATrunkInfo;
 import com.avaya.jtapi.tsapi.impl.core.TSCall;
 import com.avaya.jtapi.tsapi.impl.core.TSConnection;
 import com.avaya.jtapi.tsapi.impl.core.TSEvent;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import java.util.Vector;
 
 public class LucentTrunkInfoMapItem
 {
   private TSConnection tsConnection;
   private TSTrunk tsTrunk;
 
   private LucentTrunkInfoMapItem(CSTATrunkInfo info, TSProviderImpl provider)
   {
     String trunkName = TsapiTrunk.makeTrunkName(info.getTrunkGroup(), info.getTrunkMember());
     if (trunkName != null)
     {
       this.tsTrunk = provider.createTSTrunk(trunkName);
       if (this.tsTrunk != null)
       {
         this.tsTrunk.setGroupName(info.getTrunkGroup());
         this.tsTrunk.setMemberName(info.getTrunkMember());
       }
     }
     this.tsConnection = provider.getConnection(info.getConnection_asn());
     TsapiTrace.traceConstruction(this, LucentTrunkInfoMapItem.class);
   }
 
   public static LucentTrunkInfoMapItem[] createLucentTrunkInfoMapItemArray(CSTATrunkInfo[] input_array, TSProviderImpl prov)
   {
     TsapiTrace.traceEntry("createLucentTrunkInfoMapItemArray[LucentTrunkInfo[] input_array, TSProviderImpl prov]", LucentTrunkInfoMapItem.class);
 
     LucentTrunkInfoMapItem[] items = new LucentTrunkInfoMapItem[input_array.length];
     for (int i = 0; i < items.length; ++i)
     {
       items[i] = createLucentTrunkInfo(input_array[i], prov);
     }
     TsapiTrace.traceExit("createLucentTrunkInfoMapItemArray[LucentTrunkInfo[] input_array, TSProviderImpl prov]", LucentTrunkInfoMapItem.class);
     return items;
   }
 
   public static LucentTrunkInfoMapItem createLucentTrunkInfo(CSTATrunkInfo info, TSProviderImpl prov)
   {
     TsapiTrace.traceEntry("createLucentTrunkInfo[LucentTrunkInfo info, TSProviderImpl prov]", LucentTrunkInfoMapItem.class);
     LucentTrunkInfoMapItem _this = new LucentTrunkInfoMapItem(info, prov);
     TsapiTrace.traceExit("createLucentTrunkInfo[LucentTrunkInfo info, TSProviderImpl prov]", LucentTrunkInfoMapItem.class);
     return _this;
   }
 
   public void interLinkConnectionCallAndTrunk(Vector<TSEvent> eventList)
   {
     TsapiTrace.traceEntry("interLinkConnectionCallAndTrunk[Vector<TSEvent> eventList]", this);
     if ((this.tsTrunk != null) && (this.tsConnection != null))
     {
       this.tsConnection.setTrunk(this.tsTrunk);
 
       this.tsTrunk.setTSConnection(this.tsConnection);
 
       TSCall call = this.tsConnection.getTSCall();
       if (call != null)
       {
         call.addTrunk(this.tsTrunk, eventList);
       }
     }
     TsapiTrace.traceExit("interLinkConnectionCallAndTrunk[Vector<TSEvent> eventList]", this);
   }
 
   public TSConnection getTSConnection()
   {
     TsapiTrace.traceEntry("getTSConnection[]", this);
     TsapiTrace.traceExit("getTSConnection[]", this);
     return this.tsConnection;
   }
 
   public TSTrunk getTSTrunk()
   {
     TsapiTrace.traceEntry("getTSTrunk[]", this);
     TsapiTrace.traceExit("getTSTrunk[]", this);
     return this.tsTrunk;
   }
 
   protected void finalize() throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentTrunkInfoMapItem.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentTrunkInfoMapItem
 * JD-Core Version:    0.5.4
 */