# Car-Warehouse-Database-System

Car Showroom

This offline assignment is an extension to the Car Warehouse Database System.

The car warehouse database loads a text file from the system, uses a list to maintain the Car 

information, and stores the list's contents back to the file. This will be implemented in a separate 

server and the clients (mentioned below) will communicate with them through Java networking 

concepts (Socket and ServerSocket). The Car warehouse database in the server will also have preloaded user login information (mentioned below).

There will be two types of user/client to the system - manufacture and viewer. The manufacturers 

will log in to the system with their username and password. The viewers will log in to the system 

with a default username of ‘viewer’ and no password. Each Car will have some extra fields, such 

as quantity and price. The graphical login screen must be designed with JavaFX.

The manufacturer will have the following functionalities available:

- View all cars

- Add a car

- Edit a car

- Delete a car

There can be multiple manufacturers running. So, the car information must be up to date for all 

the manufacturers. The graphical home screen of the manufacturer and all the functionalities 

mentioned above must be designed with JavaFX.

The viewer will have the following functionalities available:

- View all cars

- Search car by registration number 

- Search car by make and model 

- Buy a car (only if the quantity is available as the quantity will be reduced with buying)

There can be multiple viewers running as well. So, the car information must be up to date for all 

the viewers so that viewers don’t end up buying a car that is not available for selling. The graphical 

home screen of the viewer and all the functionalities mentioned above must be designed with 

JavaFX.

The clients will have no information about the cars on their own. All their functionalities require 

sending requests over the network, including the login. The proper use of threading is also 

essential. 

Bonus:

- Add image option for Car

- User management through a separate admin user

- Use a standard database and JDBC in the server instead of file and list
