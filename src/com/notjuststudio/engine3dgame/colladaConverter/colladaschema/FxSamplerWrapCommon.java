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
 * <p>Java class for fx_sampler_wrap_common.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="fx_sampler_wrap_common"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="NONE"/&gt;
 *     &lt;enumeration value="WRAP"/&gt;
 *     &lt;enumeration value="MIRROR"/&gt;
 *     &lt;enumeration value="CLAMP"/&gt;
 *     &lt;enumeration value="BORDER"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "fx_sampler_wrap_common")
@XmlEnum
public enum FxSamplerWrapCommon {

    NONE,
    WRAP,
    MIRROR,
    CLAMP,
    BORDER;

    public String value() {
        return name();
    }

    public static FxSamplerWrapCommon fromValue(String v) {
        return valueOf(v);
    }

}