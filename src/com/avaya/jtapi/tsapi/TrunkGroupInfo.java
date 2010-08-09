 package com.avaya.jtapi.tsapi;
 
 public final class TrunkGroupInfo
 {
   public int idleTrunks;
   public int usedTrunks;
 
   public TrunkGroupInfo(int _idleTrunks, int _usedTrunks)
   {
     this.idleTrunks = _idleTrunks;
     this.usedTrunks = _usedTrunks;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TrunkGroupInfo
 * JD-Core Version:    0.5.4
 */