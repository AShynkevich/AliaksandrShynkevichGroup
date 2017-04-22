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

2) develop simple servlet application
    - sample application
        https://github.com/artsiom-tsaryonau/AliaksandrShynkevichGroup/tree/master/homework7
    - build a war and deploy in Tomcat using manager
        -- check that it works fine (provides server message but does not show the image) using

    localhost:8080/basic-application-web

3) Download apache httpd server (I used 2.4.25 x64)
- for example from here
        http://www.apachehaus.com/cgi-bin/download.plx
- install it somewhere (I recommend some root directory like `L://`)
- configure it if needed (like adjust ServerRoot, DocumentRoot etc.)
    - install & uninstall as service in windows
        `httpd -k install/uninstall`
    - start & stop httpd
        `httpd -k start/stop`
    - though I recommend to use `httpd` as service

4) Download corresponding mod_jk (I used 1.2.39-windows-x86_64-httpd-2.4.x) for http server
- for example from here
        https://archive.apache.org/dist/tomcat/tomcat-connectors/jk/binaries/windows
- rename to `mod_jk.so` and move to `Apache24/modules`
- create file `mod_jk.conf` in `conf/` folder and add these lines there

```xml
LoadModule jk_module modules/mod_jk.so
LoadModule access_compat_module modules/mod_access_compat.so

<IfModule jk_module>
    JkWorkersFile "conf/workers.properties"
    Alias /bwa "tomcat8/webapps/basic-application-web/"
    <Directory "tomcat8/webapps/basic-application-web/">
        AllowOverride None
        Allow from all
    Require all granted
    </Directory>

    JkMount /bwa/* worker1
</IfModule>
```

- adjust `Apache22/conf/httpd.conf` and add next lines there

```xml
Include conf/mod_jk.conf
```

- create file `worker.properties` in `conf\` repository and these lines there

```
worker.list=worker1

worker.worker1.port=8009
worker.worker1.host=localhost
worker.worker1.type=ajp13
```


5) configure Apache HTTPD to provide static files for the application (image)
- web application assumes that content provided from the folder `static_content/` in Apache `htdocs`

6) check that everything works by trying to access `localhost/bwa`
- you should see the same page as on `localhost:8080` but with image
