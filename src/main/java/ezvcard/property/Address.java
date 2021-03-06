package ezvcard.property;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.Warning;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.VCardParameters;
import ezvcard.util.StringUtils;

/*
 Copyright (c) 2012-2015, Michael Angstadt
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met: 

 1. Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer. 
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution. 

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those
 of the authors and should not be interpreted as representing official policies, 
 either expressed or implied, of the FreeBSD Project.
 */

/**
 * <p>
 * Defines a mailing address.
 * </p>
 * 
 * <p>
 * <b>Code sample (creating)</b>
 * </p>
 * 
 * <pre class="brush:java">
 * VCard vcard = new VCard();
 * 
 * Address adr = new Address();
 * adr.setStreetAddress(&quot;123 Main St.&quot;);
 * adr.setLocality(&quot;Austin&quot;);
 * adr.setRegion(&quot;TX&quot;);
 * adr.setPostalCode(&quot;12345&quot;);
 * adr.setCountry(&quot;USA&quot;);
 * adr.addType(AddressType.WORK);
 * 
 * //optionally, set the text to print on the mailing label
 * adr.setLabel(&quot;123 Main St.\nAustin, TX 12345\nUSA&quot;);
 * 
 * vcard.addAddress(adr);
 * </pre>
 * 
 * <p>
 * <b>Code sample (retrieving)</b>
 * </p>
 * 
 * <pre class="brush:java">
 * VCard vcard = ...
 * for (Address adr : vcard.getAddresses()){
 *   String street = adr.getStreetAddress();
 *   String city = adr.getLocality();
 *   //etc.
 * }
 * </pre>
 * 
 * <p>
 * <b>Only part of the street address is being returned!</b>
 * </p>
 * <p>
 * This usually means that the vCard you parsed contains unescaped comma
 * characters. To get the full address, use the {@link #getStreetAddressFull}
 * method.
 * </p>
 * 
 * <p>
 * <b>Property name:</b> {@code ADR}
 * </p>
 * <p>
 * <b>Supported versions:</b> {@code 2.1, 3.0, 4.0}
 * </p>
 * @author Michael Angstadt
 */
public class Address extends VCardProperty implements HasAltId {
	private List<String> poBoxes = new ArrayList<String>(1);
	private List<String> extendedAddresses = new ArrayList<String>(1);
	private List<String> streetAddresses = new ArrayList<String>(1);
	private List<String> localities = new ArrayList<String>(1);
	private List<String> regions = new ArrayList<String>(1);
	private List<String> postalCodes = new ArrayList<String>(1);
	private List<String> countries = new ArrayList<String>(1);

	/**
	 * Gets the P.O. (post office) box.
	 * @return the P.O. box or null if not set
	 */
	public String getPoBox() {
		return first(poBoxes);
	}

	/**
	 * Gets all P.O. (post office) boxes that are assigned to this address. An
	 * address is unlikely to have more than one, but it's possible nonetheless.
	 * @return the P.O. boxes
	 */
	public List<String> getPoBoxes() {
		return poBoxes;
	}

	/**
	 * Sets the P.O. (post office) box.
	 * @param poBox the P.O. box or null to remove
	 */
	public void setPoBox(String poBox) {
		set(poBoxes, poBox);
	}

	/**
	 * Sets the P.O. (post office) boxes. An address is unlikely to have more
	 * than one, but it's possible nonetheless.
	 * @param poBoxes the P.O. boxes
	 */
	public void setPoBoxes(List<String> poBoxes) {
		this.poBoxes = poBoxes;
	}

	/**
	 * Gets the extended address.
	 * @return the extended address (e.g. "Suite 200") or null if not set
	 */
	public String getExtendedAddress() {
		return first(extendedAddresses);
	}

	/**
	 * Gets all extended addresses that are assigned to this address. An address
	 * is unlikely to have more than one, but it's possible nonetheless.
	 * @return the extended addresses
	 */
	public List<String> getExtendedAddresses() {
		return extendedAddresses;
	}

	/**
	 * Gets the extended address. Use this method when the ADR property of the
	 * vCard you are parsing contains unescaped comma characters.
	 * @return the extended address or null if not set
	 */
	public String getExtendedAddressFull() {
		return extendedAddresses.isEmpty() ? null : StringUtils.join(extendedAddresses, ",");
	}

	/**
	 * Sets the extended address.
	 * @param extendedAddress the extended address (e.g. "Suite 200") or null to
	 * remove
	 */
	public void setExtendedAddress(String extendedAddress) {
		set(extendedAddresses, extendedAddress);
	}

	/**
	 * Sets the extended addresses. An address is unlikely to have more than
	 * one, but it's possible nonetheless.
	 * @param extendedAddresses the extended addresses
	 */
	public void setExtendedAddresses(List<String> extendedAddresses) {
		this.extendedAddresses = extendedAddresses;
	}

	/**
	 * Gets the street address
	 * @return the street address (e.g. "123 Main St")
	 */
	public String getStreetAddress() {
		return first(streetAddresses);
	}

	/**
	 * Gets all street addresses that are assigned to this address. An address
	 * is unlikely to have more than one, but it's possible nonetheless.
	 * @return the street addresses
	 */
	public List<String> getStreetAddresses() {
		return streetAddresses;
	}

	/**
	 * Gets the street address. Use this method when the ADR property of the
	 * vCard you are parsing contains unescaped comma characters.
	 * @return the street address or null if not set
	 */
	public String getStreetAddressFull() {
		return streetAddresses.isEmpty() ? null : StringUtils.join(streetAddresses, ",");
	}

	/**
	 * Sets the street address.
	 * @param streetAddress the street address (e.g. "123 Main St") or null to
	 * remove
	 */
	public void setStreetAddress(String streetAddress) {
		set(streetAddresses, streetAddress);
	}

	/**
	 * Sets the street addresses. An address is unlikely to have more than one,
	 * but it's possible nonetheless.
	 * @param streetAddresses the street addresses
	 */
	public void setStreetAddresses(List<String> streetAddresses) {
		this.streetAddresses = streetAddresses;
	}

	/**
	 * Gets the locality (city)
	 * @return the locality (e.g. "Boston") or null if not set
	 */
	public String getLocality() {
		return first(localities);
	}

	/**
	 * Gets all localities that are assigned to this address. An address is
	 * unlikely to have more than one, but it's possible nonetheless.
	 * @return the localities
	 */
	public List<String> getLocalities() {
		return localities;
	}

	/**
	 * Sets the locality (city).
	 * @param locality the locality or null to remove
	 */
	public void setLocality(String locality) {
		set(localities, locality);
	}

	/**
	 * Sets the localities. An address is unlikely to have more than one, but
	 * it's possible nonetheless.
	 * @param localities the localities
	 */
	public void setLocalities(List<String> localities) {
		this.localities = localities;
	}

	/**
	 * Gets the region (state).
	 * @return the region (e.g. "Texas") or null if not set
	 */
	public String getRegion() {
		return first(regions);
	}

	/**
	 * Gets all regions that are assigned to this address. An address is
	 * unlikely to have more than one, but it's possible nonetheless.
	 * @return the regions
	 */
	public List<String> getRegions() {
		return regions;
	}

	/**
	 * Sets the region (state).
	 * @param region the region (e.g. "Texas") or null to remove
	 */
	public void setRegion(String region) {
		set(regions, region);
	}

	/**
	 * Sets the regions. An address is unlikely to have more than one, but it's
	 * possible nonetheless.
	 * @param regions the regions
	 */
	public void setRegions(List<String> regions) {
		this.regions = regions;
	}

	/**
	 * Gets the postal code (zip code).
	 * @return the postal code (e.g. "90210") or null if not set
	 */
	public String getPostalCode() {
		return first(postalCodes);
	}

	/**
	 * Gets all postal codes that are assigned to this address. An address is
	 * unlikely to have more than one, but it's possible nonetheless.
	 * @return the postal codes
	 */
	public List<String> getPostalCodes() {
		return postalCodes;
	}

	/**
	 * Sets the postal code (zip code).
	 * @param postalCode the postal code (e.g. "90210") or null to remove
	 */
	public void setPostalCode(String postalCode) {
		set(postalCodes, postalCode);
	}

	/**
	 * Sets the postal codes. An address is unlikely to have more than one, but
	 * it's possible nonetheless.
	 * @param postalCodes the postal codes
	 */
	public void setPostalCodes(List<String> postalCodes) {
		this.postalCodes = postalCodes;
	}

	/**
	 * Gets the country.
	 * @return the country (e.g. "USA") or null if not set
	 */
	public String getCountry() {
		return first(countries);
	}

	/**
	 * Gets all countries that are assigned to this address. An address is
	 * unlikely to have more than one, but it's possible nonetheless.
	 * @return the countries
	 */
	public List<String> getCountries() {
		return countries;
	}

	/**
	 * Sets the country.
	 * @param country the country (e.g. "USA") or null to remove
	 */
	public void setCountry(String country) {
		set(countries, country);
	}

	/**
	 * Sets the countries. An address is unlikely to have more than one, but
	 * it's possible nonetheless.
	 * @param countries the countries
	 */
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	/**
	 * Gets all the TYPE parameters.
	 * @return the TYPE parameters or empty set if there are none
	 */
	public Set<AddressType> getTypes() {
		Set<String> values = parameters.getTypes();
		Set<AddressType> types = new HashSet<AddressType>(values.size());
		for (String value : values) {
			types.add(AddressType.get(value));
		}
		return types;
	}

	/**
	 * Adds a TYPE parameter.
	 * @param type the TYPE parameter to add
	 */
	public void addType(AddressType type) {
		parameters.addType(type.getValue());
	}

	/**
	 * Removes a TYPE parameter.
	 * @param type the TYPE parameter to remove
	 */
	public void removeType(AddressType type) {
		parameters.removeType(type.getValue());
	}

	@Override
	public String getLanguage() {
		return super.getLanguage();
	}

	@Override
	public void setLanguage(String language) {
		super.setLanguage(language);
	}

	/**
	 * Gets the label of the address.
	 * @return the label or null if it doesn't have one
	 */
	public String getLabel() {
		return parameters.getLabel();
	}

	/**
	 * Sets the label of the address.
	 * @param label the label or null to remove
	 */
	public void setLabel(String label) {
		parameters.setLabel(label);
	}

	/**
	 * Gets the global positioning coordinates that are associated with this
	 * address.
	 * <p>
	 * <b>Supported versions:</b> {@code 4.0}
	 * </p>
	 * @return the latitude (index 0) and longitude (index 1) or null if not set
	 * or null if the parameter value was in an incorrect format
	 * @see VCardParameters#getGeo
	 */
	public double[] getGeo() {
		return parameters.getGeo();
	}

	/**
	 * Sets the global positioning coordinates that are associated with this
	 * address.
	 * <p>
	 * <b>Supported versions:</b> {@code 4.0}
	 * </p>
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @see VCardParameters#setGeo
	 */
	public void setGeo(double latitude, double longitude) {
		parameters.setGeo(latitude, longitude);
	}

	@Override
	public List<Integer[]> getPids() {
		return super.getPids();
	}

	@Override
	public void addPid(int localId, int clientPidMapRef) {
		super.addPid(localId, clientPidMapRef);
	}

	@Override
	public void removePids() {
		super.removePids();
	}

	@Override
	public Integer getPref() {
		return super.getPref();
	}

	@Override
	public void setPref(Integer pref) {
		super.setPref(pref);
	}

	//@Override
	public String getAltId() {
		return parameters.getAltId();
	}

	//@Override
	public void setAltId(String altId) {
		parameters.setAltId(altId);
	}

	/**
	 * Gets the timezone that's associated with this address.
	 * <p>
	 * <b>Supported versions:</b> {@code 4.0}
	 * </p>
	 * @return the timezone (e.g. "America/New_York") or null if it doesn't
	 * exist
	 */
	public String getTimezone() {
		return parameters.getTimezone();
	}

	/**
	 * Sets the timezone that's associated with this address.
	 * <p>
	 * <b>Supported versions:</b> {@code 4.0}
	 * </p>
	 * @param timezone the timezone (e.g. "America/New_York") or null to remove
	 */
	public void setTimezone(String timezone) {
		parameters.setTimezone(timezone);
	}

	@Override
	protected void _validate(List<Warning> warnings, VCardVersion version, VCard vcard) {
		for (AddressType type : getTypes()) {
			if (type == AddressType.PREF) {
				//ignore because it is converted to a PREF parameter for 4.0 vCards
				continue;
			}

			if (!type.isSupported(version)) {
				warnings.add(new Warning(9, type.getValue()));
			}
		}
	}

	@Override
	protected Map<String, Object> toStringValues() {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put("poBoxes", poBoxes);
		values.put("extendedAddresses", extendedAddresses);
		values.put("streetAddresses", streetAddresses);
		values.put("localities", localities);
		values.put("regions", regions);
		values.put("postalCodes", postalCodes);
		values.put("countries", countries);
		return values;
	}

	private static String first(List<String> list) {
		return list.isEmpty() ? null : list.get(0);
	}

	private static void set(List<String> list, String value) {
		list.clear();
		if (value != null) {
			list.add(value);
		}
	}
}