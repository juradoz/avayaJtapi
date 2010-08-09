 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Vector;
 
 public final class CSTACallState extends ASNSequenceOf
 {
   short[] array;
 
   public CSTACallState()
   {
   }
 
   public CSTACallState(short[] _array)
   {
     this.array = _array;
   }
 
   public static short[] decode(InputStream in)
   {
     CSTACallState _this = new CSTACallState();
     _this.doDecode(in);
     return _this.array;
   }
 
   public void doDecode(InputStream in)
   {
     super.doDecode(in);
 
     this.array = new short[this.vec.size()];
 
     for (int i = 0; i < this.array.length; ++i)
     {
       this.array[i] = (short)((Integer)this.vec.elementAt(i)).intValue();
     }
   }
 
   public Object decodeMember(InputStream memberStream)
   {
     return new Integer(LocalConnectionState.decode(memberStream));
   }
 
   public static void encode(short[] array, OutputStream out)
   {
     CSTACallState _this = new CSTACallState(array);
     _this.doEncode(array.length, out);
   }
 
   public void encodeMember(int index, OutputStream memberStream)
   {
     LocalConnectionState.encode(this.array[index], memberStream);
   }
 
   public static Collection<String> print(short[] array, String name, String _indent)
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
       lines.addAll(LocalConnectionState.print(array[i], null, indent));
     }
     lines.add(_indent + "}");
     return lines;
   }
 
   public short[] getArray()
   {
     return this.array;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTACallState
 * JD-Core Version:    0.5.4
 */