
package com.o2r.amazonservices.mws.reports.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReportSchedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportSchedule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReportType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Schedule" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ScheduledDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * Generated by AWS Code Generator
 * <p/>
 * Wed Feb 18 13:28:59 PST 2009
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportSchedule", propOrder = {
    "reportType",
    "schedule",
    "scheduledDate"
})
public class ReportSchedule {

    @XmlElement(name = "ReportType", required = true)
    protected String reportType;
    @XmlElement(name = "Schedule", required = true)
    protected String schedule;
    @XmlElement(name = "ScheduledDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar scheduledDate;

    /**
     * Default constructor
     * 
     */
    public ReportSchedule() {
        super();
    }

    /**
     * Value constructor
     * 
     */
    public ReportSchedule(final String reportType, final String schedule, final XMLGregorianCalendar scheduledDate) {
        this.reportType = reportType;
        this.schedule = schedule;
        this.scheduledDate = scheduledDate;
    }

    /**
     * Gets the value of the reportType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Sets the value of the reportType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportType(String value) {
        this.reportType = value;
    }

    public boolean isSetReportType() {
        return (this.reportType!= null);
    }

    /**
     * Gets the value of the schedule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Sets the value of the schedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchedule(String value) {
        this.schedule = value;
    }

    public boolean isSetSchedule() {
        return (this.schedule!= null);
    }

    /**
     * Gets the value of the scheduledDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScheduledDate() {
        return scheduledDate;
    }

    /**
     * Sets the value of the scheduledDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScheduledDate(XMLGregorianCalendar value) {
        this.scheduledDate = value;
    }

    public boolean isSetScheduledDate() {
        return (this.scheduledDate!= null);
    }

    /**
     * Sets the value of the ReportType property.
     * 
     * @param value
     * @return
     *     this instance
     */
    public ReportSchedule withReportType(String value) {
        setReportType(value);
        return this;
    }

    /**
     * Sets the value of the Schedule property.
     * 
     * @param value
     * @return
     *     this instance
     */
    public ReportSchedule withSchedule(String value) {
        setSchedule(value);
        return this;
    }

    /**
     * Sets the value of the ScheduledDate property.
     * 
     * @param value
     * @return
     *     this instance
     */
    public ReportSchedule withScheduledDate(XMLGregorianCalendar value) {
        setScheduledDate(value);
        return this;
    }
    

    /**
     * 
     * XML fragment representation of this object
     * 
     * @return XML fragment for this object. Name for outer
     * tag expected to be set by calling method. This fragment
     * returns inner properties representation only
     */
    protected String toXMLFragment() {
        StringBuffer xml = new StringBuffer();
        if (isSetReportType()) {
            xml.append("<ReportType>");
            xml.append(escapeXML(getReportType()));
            xml.append("</ReportType>");
        }
        if (isSetSchedule()) {
            xml.append("<Schedule>");
            xml.append(escapeXML(getSchedule()));
            xml.append("</Schedule>");
        }
        if (isSetScheduledDate()) {
            xml.append("<ScheduledDate>");
            xml.append(getScheduledDate() + "");
            xml.append("</ScheduledDate>");
        }
        return xml.toString();
    }

    /**
     * 
     * Escape XML special characters
     */
    private String escapeXML(String string) {
        StringBuffer sb = new StringBuffer();
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            switch (c) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\'':
                sb.append("&#039;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }



    /**
     *
     * JSON fragment representation of this object
     *
     * @return JSON fragment for this object. Name for outer
     * object expected to be set by calling method. This fragment
     * returns inner properties representation only
     *
     */
    protected String toJSONFragment() {
        StringBuffer json = new StringBuffer();
        boolean first = true;
        if (isSetReportType()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("ReportType"));
            json.append(" : ");
            json.append(quoteJSON(getReportType()));
            first = false;
        }
        if (isSetSchedule()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("Schedule"));
            json.append(" : ");
            json.append(quoteJSON(getSchedule()));
            first = false;
        }
        if (isSetScheduledDate()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("ScheduledDate"));
            json.append(" : ");
            json.append(quoteJSON(getScheduledDate() + ""));
            first = false;
        }
        return json.toString();
    }

    /**
     *
     * Quote JSON string
     */
    private String quoteJSON(String string) {
        StringBuffer sb = new StringBuffer();
        sb.append("\"");
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            switch (c) {
            case '"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '/':
                sb.append("\\/");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            default:
                if (c <  ' ') {
                    sb.append("\\u" + String.format("%03x", Integer.valueOf(c)));
                } else {
                sb.append(c);
            }
        }
        }
        sb.append("\"");
        return sb.toString();
    }


}
