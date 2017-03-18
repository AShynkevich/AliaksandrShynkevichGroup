To start application just run `gradle jettyRun`.

/src/main/resource - contains `wsdl` of the service `/fileSharingService`

soap-requests.requests - list of examples of soap requests to the service

Where is better to use SOAP?
    - where the system relies on the transfer protocol other than HTTP/HTTPS
        For example SMTP or JMS
    - where the system has clearly stated contract
        SOAP has built-in support for WS- extensions like security and transactions. For example
            SAML is built upon SOAP.
    - where you have to work with legacy systems
        As SOAP was a replacement for a lot of old web interfaces, most of
            systems still support it, though it is in decline in modern web