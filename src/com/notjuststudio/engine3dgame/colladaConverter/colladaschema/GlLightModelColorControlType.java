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
 * <p>Java class for gl_light_model_color_control_type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="gl_light_model_color_control_type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SINGLE_COLOR"/&gt;
 *     &lt;enumeration value="SEPARATE_SPECULAR_COLOR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "gl_light_model_color_control_type")
@XmlEnum
public enum GlLightModelColorControlType {

    SINGLE_COLOR,
    SEPARATE_SPECULAR_COLOR;

    public String value() {
        return name();
    }

    public static GlLightModelColorControlType fromValue(String v) {
        return valueOf(v);
    }

}
