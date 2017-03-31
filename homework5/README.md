The program serves as demonstration of implemented ORM system based on annotations.
At the start program read file `database.properties` that contains
    - database.url
    - database.username
    - database.password
    - database.driver
Currently it relies on H2 database. Additionally application provides `sql` files with
queries to create required for the demonstration tables and some data. They are run on
startup of the application. The database is created in orm/ folder.

As a sample program has an interface IUserRepository that contains several methods with
ORM annotation that shows CRUD operations.

As a demonstration the program will read the object from the table with ID = 1 and displays it (it is added via scripts).
Then it will add a new object with ID = 2 and read it again to prove that it was added for real.
Then it will update the object with ID = 1 and read it to prove that it was updated.
Then it will delete the object with ID = 1 and read it. Successfully deleted object will read to NULL return.
