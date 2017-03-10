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
 * <p>Java class for gl_logic_op_type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="gl_logic_op_type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CLEAR"/&gt;
 *     &lt;enumeration value="AND"/&gt;
 *     &lt;enumeration value="AND_REVERSE"/&gt;
 *     &lt;enumeration value="COPY"/&gt;
 *     &lt;enumeration value="AND_INVERTED"/&gt;
 *     &lt;enumeration value="NOOP"/&gt;
 *     &lt;enumeration value="XOR"/&gt;
 *     &lt;enumeration value="OR"/&gt;
 *     &lt;enumeration value="NOR"/&gt;
 *     &lt;enumeration value="EQUIV"/&gt;
 *     &lt;enumeration value="INVERT"/&gt;
 *     &lt;enumeration value="OR_REVERSE"/&gt;
 *     &lt;enumeration value="COPY_INVERTED"/&gt;
 *     &lt;enumeration value="NAND"/&gt;
 *     &lt;enumeration value="SET"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "gl_logic_op_type")
@XmlEnum
public enum GlLogicOpType {

    CLEAR,
    AND,
    AND_REVERSE,
    COPY,
    AND_INVERTED,
    NOOP,
    XOR,
    OR,
    NOR,
    EQUIV,
    INVERT,
    OR_REVERSE,
    COPY_INVERTED,
    NAND,
    SET;

    public String value() {
        return name();
    }

    public static GlLogicOpType fromValue(String v) {
        return valueOf(v);
    }

}