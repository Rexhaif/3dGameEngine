//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.12 at 02:53:29 PM MSK 
//


package com.notjuststudio.engine3dgame.colladaConverter.colladaschema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for gles_texcombiner_operandAlpha_enums.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="gles_texcombiner_operandAlpha_enums"&gt;
 *   &lt;restriction base="{http://www.collada.org/2005/11/COLLADASchema}gl_blend_type"&gt;
 *     &lt;enumeration value="SRC_ALPHA"/&gt;
 *     &lt;enumeration value="ONE_MINUS_SRC_ALPHA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "gles_texcombiner_operandAlpha_enums")
@XmlEnum(GlBlendType.class)
public enum GlesTexcombinerOperandAlphaEnums {

    SRC_ALPHA,
    ONE_MINUS_SRC_ALPHA;

    public String value() {
        return name();
    }

    public static GlesTexcombinerOperandAlphaEnums fromValue(String v) {
        return valueOf(v);
    }

}
