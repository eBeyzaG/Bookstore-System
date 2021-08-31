Bookstore Storage Management System

A system for managing books using microservice architecture with OpenLiberty, JAX-RS and Microprofile. JWT is used for controlling the authorization of users. When run locally, application is available at http:/localhost:9090/login.xhtml

The project consists of three services:

BookStoreFrontend: Service for users to interact with the system. Users can add, delete or view books according to their roles.

BookStorage: Service to store the books in the system. Books have 4 properties; id, name, author name, publisher name

StorageLog: Service to store add/delete actions of users

There are three roles users can have:

admin: admins can add, delete, view books and view the log history 

mod: mods can add, delete view books but cannot view the log history

user: users can add and view books but cannot delete books or view log history


Reference

- https://openliberty.io/guides/microprofile-jwt.html
