package com.avaya.jtapi.tsapi.impl.core;

abstract interface IDomainCall
{
  public abstract int getDomainCallID();

  public abstract void notifyCallAdded(IDomainDevice paramIDomainDevice);

  public abstract void notifyCallRemoved(IDomainDevice paramIDomainDevice);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.IDomainCall
 * JD-Core Version:    0.5.4
 */