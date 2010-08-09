 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNInteger;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentQueryTodConfEvent extends LucentPrivateData
 {
   int year;
   int month;
   int day;
   int hour;
   int minute;
   int second;
   static final int PDU = 25;
 
   public static LucentQueryTodConfEvent decode(InputStream in)
   {
     LucentQueryTodConfEvent _this = new LucentQueryTodConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.year = ASNInteger.decode(memberStream);
     this.month = ASNInteger.decode(memberStream);
     this.day = ASNInteger.decode(memberStream);
     this.hour = ASNInteger.decode(memberStream);
     this.minute = ASNInteger.decode(memberStream);
     this.second = ASNInteger.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryTodConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(ASNInteger.print(this.year, "year", indent));
     lines.addAll(ASNInteger.print(this.month, "month", indent));
     lines.addAll(ASNInteger.print(this.day, "day", indent));
     lines.addAll(ASNInteger.print(this.hour, "hour", indent));
     lines.addAll(ASNInteger.print(this.minute, "minute", indent));
     lines.addAll(ASNInteger.print(this.second, "second", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 25;
   }
 
   public int getDay()
   {
     return this.day;
   }
 
   public void setDay(int day)
   {
     this.day = day;
   }
 
   public int getHour()
   {
     return this.hour;
   }
 
   public void setHour(int hour)
   {
     this.hour = hour;
   }
 
   public int getMinute()
   {
     return this.minute;
   }
 
   public void setMinute(int minute)
   {
     this.minute = minute;
   }
 
   public int getMonth()
   {
     return this.month;
   }
 
   public void setMonth(int month)
   {
     this.month = month;
   }
 
   public int getSecond()
   {
     return this.second;
   }
 
   public void setSecond(int second)
   {
     this.second = second;
   }
 
   public int getYear()
   {
     return this.year;
   }
 
   public void setYear(int year)
   {
     this.year = year;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryTodConfEvent
 * JD-Core Version:    0.5.4
 */