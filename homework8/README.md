To start the application you should just use that command

    java -jar {jarname}.jar

where `jarname` is the name of built `jar` archive.

Application requires some configuration. You need to create the file
`application.properties`, put it beside `jar` and write there these
properties

    spring.mail.host=
    spring.mail.port=
    spring.mail.username=
    spring.mail.password=
    mail.to=
    mail.from=

They are responsible for sending notification emails. Also you might need to do
some changes in your email account.

Application can be accessed at

    localhost:8080/books-home

Application creates folder `/books-storage` where all the files that
were uploaded through the application are stored. The folder is located
in the root directory of the current disk. For example if `jar` located in
`D:\\file\project.jar` then the folder will be created in `D:\\`
