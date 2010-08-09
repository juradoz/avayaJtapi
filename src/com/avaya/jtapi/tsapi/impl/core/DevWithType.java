 package com.avaya.jtapi.tsapi.impl.core;
 
 final class DevWithType
 {
   TSDevice device;
   boolean isTerminal;
 
   DevWithType(TSDevice _device, boolean _isTerminal)
   {
     this.device = _device;
     this.isTerminal = _isTerminal;
   }
 
   public int hashCode()
   {
     if (this.isTerminal) {
       return this.device.hashCode() + 1;
     }
     return this.device.hashCode();
   }
 
   public boolean equals(Object obj)
   {
     if (obj instanceof DevWithType)
     {
       return (this.device == ((DevWithType)obj).device) && (this.isTerminal == ((DevWithType)obj).isTerminal);
     }
 
     return false;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.DevWithType
 * JD-Core Version:    0.5.4
 */