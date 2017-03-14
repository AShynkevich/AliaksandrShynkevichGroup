
package com.epam.lab.mentoring.webservice.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.epam.lab.mentoring.webservice.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListResources_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "listResources");
    private final static QName _ListResourcesResponse_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "listResourcesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.epam.lab.mentoring.webservice.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListResources }
     * 
     */
    public ListResources createListResources() {
        return new ListResources();
    }

    /**
     * Create an instance of {@link ListResourcesResponse }
     * 
     */
    public ListResourcesResponse createListResourcesResponse() {
        return new ListResourcesResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListResources }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "listResources")
    public JAXBElement<ListResources> createListResources(ListResources value) {
        return new JAXBElement<ListResources>(_ListResources_QNAME, ListResources.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "listResourcesResponse")
    public JAXBElement<ListResourcesResponse> createListResourcesResponse(ListResourcesResponse value) {
        return new JAXBElement<ListResourcesResponse>(_ListResourcesResponse_QNAME, ListResourcesResponse.class, null, value);
    }

}
