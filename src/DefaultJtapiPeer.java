import com.avaya.jtapi.tsapi.TsapiPeer;

public class DefaultJtapiPeer extends TsapiPeer {
	public final String getName() {
		return getClass().getSuperclass().getName();
	}
}