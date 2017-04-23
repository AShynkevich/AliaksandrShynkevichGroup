# Task

Configure the Tomcat and Apache integration with mod_jk.so module. Build multi-module web application and deploy with tomcat manager application (text/script mode).
Static  publish to apache, dynamic to tomcat. Test and write readme, how mentor can deploy it and check that it is working.

# README

1) Download tomcat 7.0.77
- configure tomcat to have a user that can access manager-gui by adding these lines to `$TOMCAT_HOME/conf/tomcat-users.xml`

```xml
<role rolename="manager-gui"/>
<user username="admin" password="admin" roles="manager-gui"/>
```

2) develop simple servlet application

- sample application
    https://github.com/AShynkevich/AliaksandrShynkevichGroup/tree/master/homework7

- build a war and deploy in Tomcat using manager
    - check that it works fine (provides server message but does not show the image) using http://localhost:8080/basic-application-web

3) Download apache httpd server (I used 2.4.25 x64)
- for example from here
        http://www.apachehaus.com/cgi-bin/download.plx
- install it somewhere (I recommend some root directory like `L://`)
- configure it if needed (like adjust ServerRoot, DocumentRoot etc.)
    - install & uninstall as service in windows (run command line as administrator)
        `httpd -k install/uninstall`
    - start & stop httpd (run command line as administrator)
        `httpd -k start/stop`
    - though I recommend to use `httpd` as service throught `apache monitor`
- put `images\` folder from the application to `htdocs\` folder of `Apache24`

4) Download corresponding mod_jk (I used 1.2.40-windows-x86_64-httpd-2.4.x) for http server
- for example from here
        https://archive.apache.org/dist/tomcat/tomcat-connectors/jk/binaries/windows
- rename (if needed) to `mod_jk.so` and move to `Apache24/modules`
- adjust `Apache22/conf/httpd.conf` and add next lines there

```xml
LoadModule jk_module modules/mod_jk.so
JkWorkersFile conf/workers.properties
```

- uncomment lines `Include conf/extra/httpd-vhosts.conf` and `LoadModule access_compat_module modules/mod_access_compat.so`
- create file `workers.properties` in `conf\` repository and these lines there

```
worker.list=worker1

worker.worker1.port=8009
worker.worker1.host=localhost
worker.worker1.type=ajp13
```

- open file `conf/extra/httpd-vhosts.conf` and add these lines there

```xml
<VirtualHost *:80>
    DocumentRoot "${SRVROOT}/htdocs"
    <Directory "${SRVROOT}/htdocs/images">
        Options Indexes FollowSymLinks
        AllowOverride None
        Require all granted
    </Directory>
    JkMount /basic-application-web/ worker1
    JkMount /basic-application-web/messaging worker1
</VirtualHost>
```
- uncomment `#ServerName` in that block

```xml
<VirtualHost _default_:80>
 DocumentRoot "${SRVROOT}/htdocs"
 #ServerName www.example.com:80
</VirtualHost>
```

5) check that everything works by trying to access http://localhost/basic-application-web/
- it will show the page with image, while http://localhost:8080/basic-application-web won't show the image
