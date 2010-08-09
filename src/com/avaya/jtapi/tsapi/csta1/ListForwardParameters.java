 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Vector;
 
 public final class ListForwardParameters extends ASNSequenceOf
 {
   CSTAForwardingInfo[] array;
 
   public ListForwardParameters()
   {
   }
 
   public ListForwardParameters(CSTAForwardingInfo[] _array)
   {
     this.array = _array;
   }
 
   public static CSTAForwardingInfo[] decode(InputStream in)
   {
     ListForwardParameters _this = new ListForwardParameters();
     _this.doDecode(in);
     return _this.array;
   }
 
   public void doDecode(InputStream in)
   {
     super.doDecode(in);
 
     this.array = new CSTAForwardingInfo[this.vec.size()];
 
     for (int i = 0; i < this.array.length; ++i)
     {
       this.array[i] = ((CSTAForwardingInfo)this.vec.elementAt(i));
     }
   }
 
   public Object decodeMember(InputStream memberStream)
   {
     return CSTAForwardingInfo.decode(memberStream);
   }
 
   public static void encode(CSTAForwardingInfo[] array, OutputStream out)
   {
     ListForwardParameters _this = new ListForwardParameters(array);
     _this.doEncode(array.length, out);
   }
 
   public void encodeMember(int index, OutputStream memberStream)
   {
     CSTAForwardingInfo.encode(this.array[index], memberStream);
   }
 
   public static Collection<String> print(CSTAForwardingInfo[] array, String name, String _indent)
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
       lines.addAll(CSTAForwardingInfo.print(array[i], null, indent));
     }
     lines.add(_indent + "}");
     return lines;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ListForwardParameters
 * JD-Core Version:    0.5.4
 */