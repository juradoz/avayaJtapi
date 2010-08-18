package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentChargeAdvice extends LucentPrivateData {
	public static LucentChargeAdvice decode(final InputStream in) {
		final LucentChargeAdvice _this = new LucentChargeAdvice();
		_this.doDecode(in);

		return _this;
	}

	private CSTAConnectionID connection_asn;
	private String calledDevice_asn;
	private String chargingDevice_asn;
	private String trunkGroup;
	private String trunkMember;
	private short chargeType;
	private int charge;
	private short error;

	static final int PDU = 96;

	@Override
	public void decodeMembers(final InputStream memberStream) {
		connection_asn = CSTAConnectionID.decode(memberStream);
		calledDevice_asn = ASNIA5String.decode(memberStream);
		chargingDevice_asn = ASNIA5String.decode(memberStream);
		trunkGroup = ASNIA5String.decode(memberStream);
		trunkMember = ASNIA5String.decode(memberStream);
		chargeType = ASNEnumerated.decode(memberStream);
		charge = ASNInteger.decode(memberStream);
		error = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(connection_asn, memberStream);
		ASNIA5String.encode(calledDevice_asn, memberStream);
		ASNIA5String.encode(chargingDevice_asn, memberStream);
		ASNIA5String.encode(trunkGroup, memberStream);
		ASNIA5String.encode(trunkMember, memberStream);
		ASNEnumerated.encode(chargeType, memberStream);
		ASNInteger.encode(charge, memberStream);
		ASNEnumerated.encode(error, memberStream);
	}

	public String getCalledDevice_asn() {
		return calledDevice_asn;
	}

	public final int getCharge() {
		return charge;
	}

	public final short getChargeError() {
		return error;
	}

	public final short getChargeType() {
		return chargeType;
	}

	public String getChargingDevice_asn() {
		return chargingDevice_asn;
	}

	public CSTAConnectionID getConnection_asn() {
		return connection_asn;
	}

	public short getError() {
		return error;
	}

	@Override
	public int getPDU() {
		return 96;
	}

	public String getTrunkGroup() {
		return trunkGroup;
	}

	public String getTrunkMember() {
		return trunkMember;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentChargeAdviceEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(connection_asn, "connection",
				indent));
		lines.addAll(ASNIA5String.print(calledDevice_asn, "calledDevice",
				indent));
		lines.addAll(ASNIA5String.print(chargingDevice_asn, "chargingDevice",
				indent));
		lines.addAll(ASNIA5String.print(trunkGroup, "trunkGroup", indent));
		lines.addAll(ASNIA5String.print(trunkMember, "trunkMember", indent));
		lines.addAll(ChargeType.print(chargeType, "chargeType", indent));
		lines.addAll(ASNInteger.print(charge, "charge", indent));
		lines.addAll(ChargeError.print(error, "error", indent));

		lines.add("}");
		return lines;
	}

	public void setCalledDevice_asn(final String calledDevice_asn) {
		this.calledDevice_asn = calledDevice_asn;
	}

	public void setCharge(final int charge) {
		this.charge = charge;
	}

	public void setChargeType(final short chargeType) {
		this.chargeType = chargeType;
	}

	public void setChargingDevice_asn(final String chargingDevice_asn) {
		this.chargingDevice_asn = chargingDevice_asn;
	}

	public void setConnection_asn(final CSTAConnectionID connection_asn) {
		this.connection_asn = connection_asn;
	}

	public void setError(final short error) {
		this.error = error;
	}

	public void setTrunkGroup(final String trunkGroup) {
		this.trunkGroup = trunkGroup;
	}

	public void setTrunkMember(final String trunkMember) {
		this.trunkMember = trunkMember;
	}
}
