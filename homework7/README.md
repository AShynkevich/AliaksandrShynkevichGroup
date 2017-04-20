Configure the Tomcat and Apache integration with mod_jk.so module. 
Build multi-module web application and deploy with tomcat manager application (text/script mode).
Static  publish to apache, dynamic to tomcat.
Test and write readme, how mentor can deploy it and check that it is working.

README

1) download tomcat 8.5
    - configure tomcat to have a user that can access manager-gui
        -- add these lines to `$TOMCAT_HOME/conf/tomcat-users.xml`

            <role rolename="manager-gui"/>
            <user username="admin" password="admin" roles="manager-gui"/>

2) download apache httpd server (I used 2.4.25 x64)
    - for example from here
        http://www.apachehaus.com/cgi-bin/download.plx
    - configure it if needed (like adjust ServerRoot, DocumentRoot etc.)
        -- recommend to unpack it to some root disk folder (like L://) to prevent need for path changes
            in configuration
    - install & uninstall as service in windows
        `httpd -k install/uninstall`
    - start & stop httpd
        `httpd -k start/stop`
 
3) download corresponding mod_jk (I used 1.2.39-windows-x86_64-httpd-2.4.x) for http server
    - for example from here
        https://archive.apache.org/dist/tomcat/tomcat-connectors/jk/binaries/windows
    - rename to mod_jk.so and move to Apache24/modules

    - adjust Apache22/conf/httpd.conf and add next lines there

        ```Alias /basic-web-application $TOMCAT_HOME/webapps/basic-web-application
        LoadModule    jk_module  modules/mod_jk.so
        JkWorkersFile $PATH_TO_FILE/workers.properties
        JkShmFile     $PATH_TO_FILE/mod_jk.shm
        JkLogFile     $PATH_TO_FILE/mod_jk.log
        JkLogLevel    info
        # Send requests for context /examples to worker named worker1
        JkMount  /basic-web-application/* worker1```

    - configure worker.properties by adding these lines

        ```worker.list=worker1

        worker.worker1.type=ajp13
        worker.worker1.host=localhost
        worker.worker1.port=8080
        worker.worker1.lbfactor=50
        worker.worker1.cachesize=10
        worker.worker1.cache_timeout=600
        worker.worker1.socket_keepalive=1
        worker.worker1.socket_timeout=300```


3) develop simple servlet application
    - sample application
        https://github.com/artsiom-tsaryonau/AliaksandrShynkevichGroup/tree/master/homework7
    - build a war and deploy in Tomcat using manager
        -- check that it works fine (load image and provide service message) using
            localhost:8080/basic-application-web/home

5) configure Apache HTTPd to provide static files for application (image)
    - add line to prevent Tomcat from loading from serving *.png in tomcat
        JkUnMount /basic-web-application/*.png worker1
    - check that through tomcat you cannot load images anymore

6) check that everything works	