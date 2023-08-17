# ussd-master

`USSD  for User Registration`
This project contains a Spring Boot REST API for handling USSD-based user registration. The API allows users to register by responding to USSD prompts and provides a multi-step registration process.

`Project Overview`
This USSD Controller handles user registration through a series of steps using the USSD protocol. Users can register by responding to prompts presented on their mobile devices. The registration process includes selecting a preferred language, providing personal information, and confirming the entered details.

`Features`
Multi-step registration process.
Language selection between English and Kinyarwanda.
Prompt-based user interaction via USSD.
Data storage in a database (user information and USSD session details).
Error handling for invalid inputs and session management.

`Technologies Used`
Java
Spring Boot
Spring Data JPA
RESTful API
USSD protocol
PostgreSQL Database 

`Getting Started`
Clone this repository to your local machine.
Configure the MySQL database settings in the application.properties file.
Build and run the application.
Access the USSD API endpoints to initiate and complete the registration process.

`Usage`
Start a USSD session by sending a POST request to /ussd endpoint with a JSON payload containing sessionId, phoneNumber, input, and newRequest parameters.
Follow the USSD prompts to complete the registration process.
Respond to the prompts by entering the corresponding numbers or text.
Registration progress and user data are stored in the database.
