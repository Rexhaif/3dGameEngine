//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.12 at 02:53:29 PM MSK 
//


package com.notjuststudio.engine3dgame.colladaConverter.colladaschema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}asset" minOccurs="0"/&gt;
 *         &lt;element name="technique_common"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;choice&gt;
 *                   &lt;element name="ambient"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="directional"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="point"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
 *                             &lt;element name="constant_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
 *                             &lt;element name="linear_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
 *                             &lt;element name="quadratic_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="spot"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
 *                             &lt;element name="constant_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
 *                             &lt;element name="linear_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
 *                             &lt;element name="quadratic_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
 *                             &lt;element name="falloff_angle" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
 *                             &lt;element name="falloff_exponent" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/choice&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}technique" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}extra" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "asset",
    "techniqueCommon",
    "techniques",
    "extras"
})
@XmlRootElement(name = "light")
public class Light {

    protected Asset asset;
    @XmlElement(name = "technique_common", required = true)
    protected TechniqueCommon techniqueCommon;
    @XmlElement(name = "technique")
    protected List<Technique> techniques;
    @XmlElement(name = "extra")
    protected List<Extra> extras;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;

    /**
     * 
     * 						The light element may contain an asset element.
     * 						
     * 
     * @return
     *     possible object is
     *     {@link Asset }
     *     
     */
    public Asset getAsset() {
        return asset;
    }

    /**
     * Sets the value of the asset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Asset }
     *     
     */
    public void setAsset(Asset value) {
        this.asset = value;
    }

    /**
     * Gets the value of the techniqueCommon property.
     * 
     * @return
     *     possible object is
     *     {@link TechniqueCommon }
     *     
     */
    public TechniqueCommon getTechniqueCommon() {
        return techniqueCommon;
    }

    /**
     * Sets the value of the techniqueCommon property.
     * 
     * @param value
     *     allowed object is
     *     {@link TechniqueCommon }
     *     
     */
    public void setTechniqueCommon(TechniqueCommon value) {
        this.techniqueCommon = value;
    }

    /**
     * 
     * 						This element may contain any number of non-common profile techniques.
     * 						Gets the value of the techniques property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the techniques property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTechniques().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Technique }
     * 
     * 
     */
    public List<Technique> getTechniques() {
        if (techniques == null) {
            techniques = new ArrayList<Technique>();
        }
        return this.techniques;
    }

    /**
     * 
     * 						The extra element may appear any number of times.
     * 						Gets the value of the extras property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extras property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtras().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extra }
     * 
     * 
     */
    public List<Extra> getExtras() {
        if (extras == null) {
            extras = new ArrayList<Extra>();
        }
        return this.extras;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;choice&gt;
     *         &lt;element name="ambient"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="directional"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="point"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
     *                   &lt;element name="constant_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
     *                   &lt;element name="linear_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
     *                   &lt;element name="quadratic_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="spot"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
     *                   &lt;element name="constant_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
     *                   &lt;element name="linear_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
     *                   &lt;element name="quadratic_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
     *                   &lt;element name="falloff_angle" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
     *                   &lt;element name="falloff_exponent" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/choice&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "spot",
        "point",
        "directional",
        "ambient"
    })
    public static class TechniqueCommon {

        protected Spot spot;
        protected Point point;
        protected Directional directional;
        protected Ambient ambient;

        /**
         * Gets the value of the spot property.
         * 
         * @return
         *     possible object is
         *     {@link Spot }
         *     
         */
        public Spot getSpot() {
            return spot;
        }

        /**
         * Sets the value of the spot property.
         * 
         * @param value
         *     allowed object is
         *     {@link Spot }
         *     
         */
        public void setSpot(Spot value) {
            this.spot = value;
        }

        /**
         * Gets the value of the point property.
         * 
         * @return
         *     possible object is
         *     {@link Point }
         *     
         */
        public Point getPoint() {
            return point;
        }

        /**
         * Sets the value of the point property.
         * 
         * @param value
         *     allowed object is
         *     {@link Point }
         *     
         */
        public void setPoint(Point value) {
            this.point = value;
        }

        /**
         * Gets the value of the directional property.
         * 
         * @return
         *     possible object is
         *     {@link Directional }
         *     
         */
        public Directional getDirectional() {
            return directional;
        }

        /**
         * Sets the value of the directional property.
         * 
         * @param value
         *     allowed object is
         *     {@link Directional }
         *     
         */
        public void setDirectional(Directional value) {
            this.directional = value;
        }

        /**
         * Gets the value of the ambient property.
         * 
         * @return
         *     possible object is
         *     {@link Ambient }
         *     
         */
        public Ambient getAmbient() {
            return ambient;
        }

        /**
         * Sets the value of the ambient property.
         * 
         * @param value
         *     allowed object is
         *     {@link Ambient }
         *     
         */
        public void setAmbient(Ambient value) {
            this.ambient = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "color"
        })
        public static class Ambient {

            @XmlElement(required = true)
            protected TargetableFloat3 color;

            /**
             * Gets the value of the color property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat3 }
             *     
             */
            public TargetableFloat3 getColor() {
                return color;
            }

            /**
             * Sets the value of the color property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat3 }
             *     
             */
            public void setColor(TargetableFloat3 value) {
                this.color = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "color"
        })
        public static class Directional {

            @XmlElement(required = true)
            protected TargetableFloat3 color;

            /**
             * Gets the value of the color property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat3 }
             *     
             */
            public TargetableFloat3 getColor() {
                return color;
            }

            /**
             * Sets the value of the color property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat3 }
             *     
             */
            public void setColor(TargetableFloat3 value) {
                this.color = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
         *         &lt;element name="constant_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
         *         &lt;element name="linear_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
         *         &lt;element name="quadratic_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "color",
            "constantAttenuation",
            "linearAttenuation",
            "quadraticAttenuation"
        })
        public static class Point {

            @XmlElement(required = true)
            protected TargetableFloat3 color;
            @XmlElement(name = "constant_attenuation", defaultValue = "1.0")
            protected TargetableFloat constantAttenuation;
            @XmlElement(name = "linear_attenuation", defaultValue = "0.0")
            protected TargetableFloat linearAttenuation;
            @XmlElement(name = "quadratic_attenuation", defaultValue = "0.0")
            protected TargetableFloat quadraticAttenuation;

            /**
             * Gets the value of the color property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat3 }
             *     
             */
            public TargetableFloat3 getColor() {
                return color;
            }

            /**
             * Sets the value of the color property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat3 }
             *     
             */
            public void setColor(TargetableFloat3 value) {
                this.color = value;
            }

            /**
             * Gets the value of the constantAttenuation property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat }
             *     
             */
            public TargetableFloat getConstantAttenuation() {
                return constantAttenuation;
            }

            /**
             * Sets the value of the constantAttenuation property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat }
             *     
             */
            public void setConstantAttenuation(TargetableFloat value) {
                this.constantAttenuation = value;
            }

            /**
             * Gets the value of the linearAttenuation property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat }
             *     
             */
            public TargetableFloat getLinearAttenuation() {
                return linearAttenuation;
            }

            /**
             * Sets the value of the linearAttenuation property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat }
             *     
             */
            public void setLinearAttenuation(TargetableFloat value) {
                this.linearAttenuation = value;
            }

            /**
             * Gets the value of the quadraticAttenuation property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat }
             *     
             */
            public TargetableFloat getQuadraticAttenuation() {
                return quadraticAttenuation;
            }

            /**
             * Sets the value of the quadraticAttenuation property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat }
             *     
             */
            public void setQuadraticAttenuation(TargetableFloat value) {
                this.quadraticAttenuation = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="color" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat3"/&gt;
         *         &lt;element name="constant_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
         *         &lt;element name="linear_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
         *         &lt;element name="quadratic_attenuation" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
         *         &lt;element name="falloff_angle" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
         *         &lt;element name="falloff_exponent" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "color",
            "constantAttenuation",
            "linearAttenuation",
            "quadraticAttenuation",
            "falloffAngle",
            "falloffExponent"
        })
        public static class Spot {

            @XmlElement(required = true)
            protected TargetableFloat3 color;
            @XmlElement(name = "constant_attenuation", defaultValue = "1.0")
            protected TargetableFloat constantAttenuation;
            @XmlElement(name = "linear_attenuation", defaultValue = "0.0")
            protected TargetableFloat linearAttenuation;
            @XmlElement(name = "quadratic_attenuation", defaultValue = "0.0")
            protected TargetableFloat quadraticAttenuation;
            @XmlElement(name = "falloff_angle", defaultValue = "180.0")
            protected TargetableFloat falloffAngle;
            @XmlElement(name = "falloff_exponent", defaultValue = "0.0")
            protected TargetableFloat falloffExponent;

            /**
             * Gets the value of the color property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat3 }
             *     
             */
            public TargetableFloat3 getColor() {
                return color;
            }

            /**
             * Sets the value of the color property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat3 }
             *     
             */
            public void setColor(TargetableFloat3 value) {
                this.color = value;
            }

            /**
             * Gets the value of the constantAttenuation property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat }
             *     
             */
            public TargetableFloat getConstantAttenuation() {
                return constantAttenuation;
            }

            /**
             * Sets the value of the constantAttenuation property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat }
             *     
             */
            public void setConstantAttenuation(TargetableFloat value) {
                this.constantAttenuation = value;
            }

            /**
             * Gets the value of the linearAttenuation property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat }
             *     
             */
            public TargetableFloat getLinearAttenuation() {
                return linearAttenuation;
            }

            /**
             * Sets the value of the linearAttenuation property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat }
             *     
             */
            public void setLinearAttenuation(TargetableFloat value) {
                this.linearAttenuation = value;
            }

            /**
             * Gets the value of the quadraticAttenuation property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat }
             *     
             */
            public TargetableFloat getQuadraticAttenuation() {
                return quadraticAttenuation;
            }

            /**
             * Sets the value of the quadraticAttenuation property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat }
             *     
             */
            public void setQuadraticAttenuation(TargetableFloat value) {
                this.quadraticAttenuation = value;
            }

            /**
             * Gets the value of the falloffAngle property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat }
             *     
             */
            public TargetableFloat getFalloffAngle() {
                return falloffAngle;
            }

            /**
             * Sets the value of the falloffAngle property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat }
             *     
             */
            public void setFalloffAngle(TargetableFloat value) {
                this.falloffAngle = value;
            }

            /**
             * Gets the value of the falloffExponent property.
             * 
             * @return
             *     possible object is
             *     {@link TargetableFloat }
             *     
             */
            public TargetableFloat getFalloffExponent() {
                return falloffExponent;
            }

            /**
             * Sets the value of the falloffExponent property.
             * 
             * @param value
             *     allowed object is
             *     {@link TargetableFloat }
             *     
             */
            public void setFalloffExponent(TargetableFloat value) {
                this.falloffExponent = value;
            }

        }

    }

}
