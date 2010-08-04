/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ final class DevWithType
/*      */ {
/*      */   TSDevice device;
/*      */   boolean isTerminal;
/*      */ 
/*      */   DevWithType(TSDevice _device, boolean _isTerminal)
/*      */   {
/* 5535 */     this.device = _device;
/* 5536 */     this.isTerminal = _isTerminal;
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 5541 */     if (this.isTerminal) {
/* 5542 */       return this.device.hashCode() + 1;
/*      */     }
/* 5544 */     return this.device.hashCode();
/*      */   }
/*      */ 
/*      */   public boolean equals(Object obj)
/*      */   {
/* 5550 */     if (obj instanceof DevWithType)
/*      */     {
/* 5555 */       return (this.device == ((DevWithType)obj).device) && (this.isTerminal == ((DevWithType)obj).isTerminal);
/*      */     }
/*      */ 
/* 5564 */     return false;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.DevWithType
 * JD-Core Version:    0.5.4
 */