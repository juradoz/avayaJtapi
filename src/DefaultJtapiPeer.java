 import com.avaya.jtapi.tsapi.TsapiPeer;
 
 public class DefaultJtapiPeer extends TsapiPeer
 {
   public final String getName()
   {
     return super.getClass().getSuperclass().getName();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     DefaultJtapiPeer
 * JD-Core Version:    0.5.4
 */