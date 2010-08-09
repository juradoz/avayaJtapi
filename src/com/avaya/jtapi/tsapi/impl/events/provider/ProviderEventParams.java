package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.MetaEvent;
import javax.telephony.Provider;

public class ProviderEventParams {
	private final Provider provider;
	private final int id;
	private final int cause;
	private final MetaEvent metaEvent;
	private final Object source;
	private final Object privateData;

	public ProviderEventParams(Provider provider, int id, int cause,
			MetaEvent metaEvent, Object source, Object privateData) {
		this.provider = provider;
		this.id = id;
		this.cause = cause;
		this.metaEvent = metaEvent;
		this.source = source;
		this.privateData = privateData;
	}

	public int getCause() {
		return cause;
	}

	public int getId() {
		return id;
	}

	public MetaEvent getMetaEvent() {
		return metaEvent;
	}

	public Object getPrivateData() {
		return privateData;
	}

	public Provider getProvider() {
		return provider;
	}

	public Object getSource() {
		return source;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.provider.ProviderEventParams JD-Core
 * Version: 0.5.4
 */