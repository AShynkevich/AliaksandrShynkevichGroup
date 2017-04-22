# Task

Configure the Tomcat and Apache integration with mod_jk.so module. Build multi-module web application and deploy with tomcat manager application (text/script mode).
Static  publish to apache, dynamic to tomcat. Test and write readme, how mentor can deploy it and check that it is working.

# README

1) Download tomcat 8.5
- configure tomcat to have a user that can access manager-gui by adding these lines to `$TOMCAT_HOME/conf/tomcat-users.xml`

```xml
<role rolename="manager-gui"/>
<user username="admin" password="admin" roles="manager-gui"/>
```

2) Download apache httpd server (I used 2.4.25 x64)
- for example from here
        http://www.apachehaus.com/cgi-bin/download.plx
- configure it if needed (like adjust ServerRoot, DocumentRoot etc.)
 - I recommend to unpack it to some root disk folder (like `L://`) to prevent need for path changes in configuration
    - install & uninstall as service in windows

        `httpd -k install/uninstall`
    - start & stop httpd

        `httpd -k start/stop`
- I suggest to install `tomcat` to root folder of apache server (for example `Apache24/`)

3) Download corresponding mod_jk (I used 1.2.39-windows-x86_64-httpd-2.4.x) for http server
- for example from here
        https://archive.apache.org/dist/tomcat/tomcat-connectors/jk/binaries/windows
- rename to `mod_jk.so` and move to `Apache24/modules`
- adjust `Apache22/conf/httpd.conf` and add next lines there

```xml
LoadModule    jk_module  modules/mod_jk.so
<IfModule mod_jk.c>
    JkWorkersFile $PATH_TO_FILE/workers.properties
    JkMount /basic-web-application/* worker1
</IfModule>
```

- configure worker.properties by adding these lines

```
worker.list=worker1

worker.ajp13.port=8009
worker.ajp13.host=localhost
worker.ajp13.type=ajp13
```

3) develop simple servlet application
    - sample application
        https://github.com/artsiom-tsaryonau/AliaksandrShynkevichGroup/tree/master/homework7
    - build a war and deploy in Tomcat using manager
        -- check that it works fine (provides server message but does not show the image) using
        
            `localhost:8080/basic-application-web/home`

5) configure Apache HTTPd to provide static files for application (image)
- web application assumes that content provided from the folder `static_content/`
- go to `conf/extra/httpd-vhosts.conf` and configure virtual host to point to tomcat

6) check that everything works
