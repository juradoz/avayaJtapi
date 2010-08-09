 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Vector;
 
 public final class LucentTrunkInfoList extends ASNSequenceOf
 {
   CSTATrunkInfo[] array;
 
   public LucentTrunkInfoList()
   {
   }
 
   public LucentTrunkInfoList(CSTATrunkInfo[] _array)
   {
     this.array = _array;
   }
 
   public static CSTATrunkInfo[] decode(InputStream in)
   {
     LucentTrunkInfoList _this = new LucentTrunkInfoList();
     _this.doDecode(in);
     return _this.array;
   }
 
   public void doDecode(InputStream in)
   {
     super.doDecode(in);
 
     this.array = new CSTATrunkInfo[this.vec.size()];
 
     for (int i = 0; i < this.array.length; ++i)
     {
       this.array[i] = ((CSTATrunkInfo)this.vec.elementAt(i));
     }
   }
 
   public Object decodeMember(InputStream memberStream)
   {
     return CSTATrunkInfo.decode(memberStream);
   }
 
   public static void encode(CSTATrunkInfo[] _array, OutputStream out) {
     LucentTrunkInfoList _this = new LucentTrunkInfoList(_array);
     _this.doEncode(_array.length, out);
   }
   public void encodeMember(int idx, OutputStream memberStream) {
     CSTATrunkInfo.encode(this.array[idx], memberStream);
   }
 
   public static Collection<String> print(CSTATrunkInfo[] array, String name, String _indent) {
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
       lines.addAll(CSTATrunkInfo.print(array[i], null, indent));
     }
     lines.add(_indent + "}");
     return lines;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentTrunkInfoList
 * JD-Core Version:    0.5.4
 */