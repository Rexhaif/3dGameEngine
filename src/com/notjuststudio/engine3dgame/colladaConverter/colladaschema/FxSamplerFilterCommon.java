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
 * <p>Java class for fx_sampler_filter_common.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="fx_sampler_filter_common"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="NONE"/&gt;
 *     &lt;enumeration value="NEAREST"/&gt;
 *     &lt;enumeration value="LINEAR"/&gt;
 *     &lt;enumeration value="NEAREST_MIPMAP_NEAREST"/&gt;
 *     &lt;enumeration value="LINEAR_MIPMAP_NEAREST"/&gt;
 *     &lt;enumeration value="NEAREST_MIPMAP_LINEAR"/&gt;
 *     &lt;enumeration value="LINEAR_MIPMAP_LINEAR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "fx_sampler_filter_common")
@XmlEnum
public enum FxSamplerFilterCommon {

    NONE,
    NEAREST,
    LINEAR,
    NEAREST_MIPMAP_NEAREST,
    LINEAR_MIPMAP_NEAREST,
    NEAREST_MIPMAP_LINEAR,
    LINEAR_MIPMAP_LINEAR;

    public String value() {
        return name();
    }

    public static FxSamplerFilterCommon fromValue(String v) {
        return valueOf(v);
    }

}