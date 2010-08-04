/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import java.net.InetSocketAddress;
/*      */ import java.util.Collection;
/*      */ 
/*      */ final class ConnectStringData
/*      */ {
/*      */   public final String serverId;
/*      */   public final String loginId;
/*      */   public final String password;
/*      */   public final Collection<InetSocketAddress> telephonyServers;
/*      */   public final String url;
/*      */ 
/*      */   ConnectStringData(String serverId, String loginId, String password, Collection<InetSocketAddress> telephonyServers, String url)
/*      */   {
/* 3854 */     this.serverId = serverId;
/* 3855 */     this.loginId = loginId;
/* 3856 */     this.password = password;
/* 3857 */     this.telephonyServers = telephonyServers;
/* 3858 */     this.url = url;
/*      */   }
/*      */ 
/*      */   public String toString() {
/* 3862 */     return this.serverId + ";loginID=" + this.loginId + ";passwd=" + this.password + ";servers=" + this.telephonyServers;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.ConnectStringData
 * JD-Core Version:    0.5.4
 */