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

	public ProviderEventParams(final Provider provider, final int id,
			final int cause, final MetaEvent metaEvent, final Object source,
			final Object privateData) {
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
