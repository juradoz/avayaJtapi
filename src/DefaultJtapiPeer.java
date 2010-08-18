import com.avaya.jtapi.tsapi.TsapiPeer;

public class DefaultJtapiPeer extends TsapiPeer {
	@Override
	public final String getName() {
		return super.getClass().getSuperclass().getName();
	}
}
