package ezvcard.io.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ezvcard.VCard;
import ezvcard.io.scribe.ScribeIndex;
import ezvcard.io.scribe.VCardPropertyScribe;
import ezvcard.property.ProductId;
import ezvcard.property.VCardProperty;

/*
 Copyright (c) 2012-2016, Michael Angstadt
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
 * Serializes jCards within the jackson-databind framework.
 * @author Buddy Gorven
 */
@JsonFormat
public class JCardSerializer extends StdSerializer<VCard> implements ContextualSerializer {
	private static final long serialVersionUID = -856795690626261178L;

	private ScribeIndex index = new ScribeIndex();
	private boolean addProdId = true;
	private boolean versionStrict = true;

	public JCardSerializer() {
		super(VCard.class);
	}

	@Override
	public void serialize(VCard value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		@SuppressWarnings("resource")
		JCardWriter writer = new JCardWriter(gen);
		writer.setAddProdId(isAddProdId());
		writer.setVersionStrict(isVersionStrict());
		writer.setScribeIndex(getScribeIndex());

		writer.write(value);
	}

	public JCardSerializer createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
		if (property == null) {
			return this;
		}

		JCardFormat annotation = property.getAnnotation(JCardFormat.class);
		if (annotation == null) {
			return this;
		}

		JCardSerializer result = new JCardSerializer();
		result.setAddProdId(annotation.addProdId());
		result.setVersionStrict(annotation.versionStrict());
		result.setScribeIndex(getScribeIndex());
		return result;
	}

	/**
	 * Gets whether a {@link ProductId} property will be added to the vCard that
	 * marks it as having been generated by this library.
	 * @return true if the property will be added, false if not (defaults to
	 * true)
	 */
	public boolean isAddProdId() {
		return addProdId;
	}

	/**
	 * Sets whether to add a {@link ProductId} property to the vCard that marks
	 * it as having been generated by this library.
	 * @param addProdId true to add the property, false not to (defaults to
	 * true)
	 */
	public void setAddProdId(boolean addProdId) {
		this.addProdId = addProdId;
	}

	/**
	 * Gets whether properties that do not support jCard will be excluded from
	 * the written vCard. jCard only supports properties defined in the vCard
	 * version 4.0 specification.
	 * @return true if the properties will be excluded, false if not (defaults
	 * to true)
	 */
	public boolean isVersionStrict() {
		return versionStrict;
	}

	/**
	 * Sets whether properties that do not support jCard will be excluded from
	 * the written vCard. jCard only supports properties defined in the vCard
	 * version 4.0 specification.
	 * @param versionStrict true to exclude such properties, false not to
	 * (defaults to true)
	 */
	public void setVersionStrict(boolean versionStrict) {
		this.versionStrict = versionStrict;
	}

	/**
	 * <p>
	 * Registers a property scribe. This is the same as calling:
	 * </p>
	 * <p>
	 * {@code getScribeIndex().register(scribe)}
	 * </p>
	 * @param scribe the scribe to register
	 */
	public void registerScribe(VCardPropertyScribe<? extends VCardProperty> scribe) {
		index.register(scribe);
	}

	/**
	 * Gets the scribe index.
	 * @return the scribe index
	 */
	public ScribeIndex getScribeIndex() {
		return index;
	}

	/**
	 * Sets the scribe index.
	 * @param index the scribe index
	 */
	public void setScribeIndex(ScribeIndex index) {
		this.index = index;
	}
}
