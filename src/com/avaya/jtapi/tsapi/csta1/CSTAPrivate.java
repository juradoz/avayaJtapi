 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
 import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
 import java.util.ArrayList;
 import java.util.Collection;
 import org.apache.log4j.Logger;
 
 public class CSTAPrivate
 {
   private static Logger log = Logger.getLogger(CSTAPrivate.class);
   public String vendor;
   public byte[] data;
   public int tsType;
 
   public CSTAPrivate(byte[] _data)
   {
     this(_data, false);
   }
 
   public CSTAPrivate(byte[] _data, boolean waitForResponse)
   {
     this.data = _data;
     if (waitForResponse)
     {
       this.tsType = 89;
     }
     else
     {
       this.tsType = 95;
     }
   }
 
   public CSTAPrivate(String _vendor, byte[] _data, int _tsType)
   {
     this.vendor = _vendor;
     this.data = _data;
     this.tsType = _tsType;
   }
 
   public byte[] getData()
   {
     return this.data;
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("TsapiPrivate ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNIA5String.print(this.vendor, "vendor", indent));
     lines.addAll(ASNOctetString.print(this.data, "data", indent));
     lines.addAll(ASNInteger.print(this.tsType, "tsType", indent));
 
     lines.add("}");
     return lines;
   }
 
   public static void translatePrivateData(CSTAEvent event, String debugID)
   {
     try
     {
       if (event.getPrivData() instanceof CSTAPrivate)
       {
         CSTAPrivate priv = (CSTAPrivate)event.getPrivData();
 
         if ((priv.data != null) && (priv.data.length > 0))
         {
           if (LucentPrivateData.isAvayaVendor(priv.vendor))
           {
             LucentPrivateData luPriv = LucentPrivateData.create(priv, event.getEventHeader().getEventType());
 
             if (luPriv != null) {
               event.setPrivData(luPriv);
             }
           }
 
         }
         else {
           event.setPrivData(null);
         }
       }
     }
     catch (Exception e)
     {
       log.error("tsapi.translatePrivateData() failure: for " + debugID);
       log.error(e.getMessage(), e);
     }
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAPrivate
 * JD-Core Version:    0.5.4
 */