package com.avaya.jtapi.tsapi.tsapiInterface;

import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiProviderUnavailableException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;

public final class TSErrorMap {
	public static void throwACSException(int acsError)
			throws TsapiInvalidArgumentException,
			TsapiProviderUnavailableException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		String errorString = "ACS Error: " + String.valueOf(acsError);
		if (acsError == 23) {
			throw new TsapiProviderUnavailableException(1, acsError,
					errorString);
		}
		if (((acsError >= 25) && (acsError <= 36)) || (acsError == 44)
				|| ((acsError >= 49) && (acsError <= 65))
				|| ((acsError >= 111) && (acsError <= 118))) {
			throw new TsapiPrivilegeViolationException(1, acsError, 0,
					errorString);
		}
		if (acsError == 42) {
			throw new TsapiResourceUnavailableException(1, acsError, 0,
					errorString);
		}
		if (acsError == 119) {
			throw new TsapiInvalidArgumentException(1, acsError, errorString);
		}

		throw new TsapiPlatformException(1, acsError, errorString);
	}

	public static void throwCSTAException(int cstaError)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		String errorString = "CSTA Error: " + String.valueOf(cstaError);
		if (((cstaError >= 21) && (cstaError <= 29)) || (cstaError == 2)) {
			throw new TsapiInvalidStateException(2, cstaError, null, 0, 0,
					errorString);
		}
		if ((cstaError == 3) || (cstaError == 4)) {
			throw new TsapiInvalidArgumentException(2, cstaError, errorString);
		}
		if ((cstaError == 5) || (cstaError == 6) || (cstaError == 7)
				|| (cstaError == 14)) {
			throw new TsapiInvalidPartyException(2, cstaError, 0, errorString);
		}
		if ((cstaError == 8) || (cstaError == 9) || (cstaError == 10)
				|| (cstaError == 19)) {
			throw new TsapiPrivilegeViolationException(2, cstaError, 0,
					errorString);
		}
		if ((cstaError >= 31) && (cstaError <= 44)) {
			throw new TsapiResourceUnavailableException(2, cstaError, 0,
					errorString);
		}

		throw new TsapiPlatformException(2, cstaError, errorString);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TSErrorMap JD-Core Version: 0.5.4
 */