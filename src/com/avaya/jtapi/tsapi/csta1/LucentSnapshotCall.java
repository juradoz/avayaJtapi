 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Vector;
 
 public final class LucentSnapshotCall extends ASNSequenceOf
 {
   CSTASnapshotCallResponseInfo[] array;
 
   public LucentSnapshotCall()
   {
   }
 
   public LucentSnapshotCall(CSTASnapshotCallResponseInfo[] _array)
   {
     this.array = _array;
   }
 
   public static CSTASnapshotCallResponseInfo[] decode(InputStream in)
   {
     LucentSnapshotCall _this = new LucentSnapshotCall();
     _this.doDecode(in);
     return _this.array;
   }
 
   public void doDecode(InputStream in)
   {
     super.doDecode(in);
 
     this.array = new CSTASnapshotCallResponseInfo[this.vec.size()];
 
     for (int i = 0; i < this.array.length; ++i)
     {
       this.array[i] = ((CSTASnapshotCallResponseInfo)this.vec.elementAt(i));
     }
   }
 
   public Object decodeMember(InputStream memberStream)
   {
     return CSTASnapshotCallResponseInfo.decode(memberStream);
   }
 
   static void encode(CSTASnapshotCallResponseInfo[] array, OutputStream out)
   {
     LucentSnapshotCall _this = new LucentSnapshotCall(array);
     _this.doEncode(array.length, out);
   }
 
   public void encodeMember(int index, OutputStream memberStream)
   {
     CSTASnapshotCallResponseInfo.encode(this.array[index], memberStream);
   }
 
   public static Collection<String> print(CSTASnapshotCallResponseInfo[] array, String name, String _indent)
   {
     Collection lines = new ArrayList();
 
     if (array == null)
     {
       lines.add(_indent + name + " <null>");
       return lines;
     }
     if (name != null) {
       lines.add(_indent + name);
     }
     lines.add(_indent + "{");
 
     String indent = _indent + "  ";
 
     for (int i = 0; i < array.length; ++i) {
       lines.addAll(CSTASnapshotCallResponseInfo.print(array[i], null, indent));
     }
     lines.add(_indent + "}");
     return lines;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSnapshotCall
 * JD-Core Version:    0.5.4
 */