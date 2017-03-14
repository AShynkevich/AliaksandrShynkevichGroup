
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

    private final static QName _DeleteFileResponse_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "deleteFileResponse");
    private final static QName _DeleteFile_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "deleteFile");
    private final static QName _CreateFileResponse_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "createFileResponse");
    private final static QName _ReadFile_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "readFile");
    private final static QName _ListFilesResponse_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "listFilesResponse");
    private final static QName _ReadFileResponse_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "readFileResponse");
    private final static QName _ListFiles_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "listFiles");
    private final static QName _CreateFile_QNAME = new QName("http://soap.webservice.mentoring.lab.epam.com/", "createFile");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.epam.lab.mentoring.webservice.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateFileResponse }
     * 
     */
    public CreateFileResponse createCreateFileResponse() {
        return new CreateFileResponse();
    }

    /**
     * Create an instance of {@link CreateFile }
     * 
     */
    public CreateFile createCreateFile() {
        return new CreateFile();
    }

    /**
     * Create an instance of {@link ReadFile }
     * 
     */
    public ReadFile createReadFile() {
        return new ReadFile();
    }

    /**
     * Create an instance of {@link DeleteFile }
     * 
     */
    public DeleteFile createDeleteFile() {
        return new DeleteFile();
    }

    /**
     * Create an instance of {@link ReadFileResponse }
     * 
     */
    public ReadFileResponse createReadFileResponse() {
        return new ReadFileResponse();
    }

    /**
     * Create an instance of {@link ListFiles }
     * 
     */
    public ListFiles createListFiles() {
        return new ListFiles();
    }

    /**
     * Create an instance of {@link ListFilesResponse }
     * 
     */
    public ListFilesResponse createListFilesResponse() {
        return new ListFilesResponse();
    }

    /**
     * Create an instance of {@link DeleteFileResponse }
     * 
     */
    public DeleteFileResponse createDeleteFileResponse() {
        return new DeleteFileResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "deleteFileResponse")
    public JAXBElement<DeleteFileResponse> createDeleteFileResponse(DeleteFileResponse value) {
        return new JAXBElement<DeleteFileResponse>(_DeleteFileResponse_QNAME, DeleteFileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "deleteFile")
    public JAXBElement<DeleteFile> createDeleteFile(DeleteFile value) {
        return new JAXBElement<DeleteFile>(_DeleteFile_QNAME, DeleteFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "createFileResponse")
    public JAXBElement<CreateFileResponse> createCreateFileResponse(CreateFileResponse value) {
        return new JAXBElement<CreateFileResponse>(_CreateFileResponse_QNAME, CreateFileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadFile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "readFile")
    public JAXBElement<ReadFile> createReadFile(ReadFile value) {
        return new JAXBElement<ReadFile>(_ReadFile_QNAME, ReadFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "listFilesResponse")
    public JAXBElement<ListFilesResponse> createListFilesResponse(ListFilesResponse value) {
        return new JAXBElement<ListFilesResponse>(_ListFilesResponse_QNAME, ListFilesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadFileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "readFileResponse")
    public JAXBElement<ReadFileResponse> createReadFileResponse(ReadFileResponse value) {
        return new JAXBElement<ReadFileResponse>(_ReadFileResponse_QNAME, ReadFileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFiles }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "listFiles")
    public JAXBElement<ListFiles> createListFiles(ListFiles value) {
        return new JAXBElement<ListFiles>(_ListFiles_QNAME, ListFiles.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.mentoring.lab.epam.com/", name = "createFile")
    public JAXBElement<CreateFile> createCreateFile(CreateFile value) {
        return new JAXBElement<CreateFile>(_CreateFile_QNAME, CreateFile.class, null, value);
    }

}
