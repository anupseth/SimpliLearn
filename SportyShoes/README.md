# SportyShoes online shopping website

This online shopping website will interact with 2 kinds of users.

- Admin

    - The admin can login to the application with username : "admin" and password : "admin"
    
    - Admin can perform follwoing task
              
              - Change his password.
              - See list of users singedup on the application.
              - Search a particular user.
              - Add products.
              - Deleted products.
              - Edit products.
              - See Report by dates and category for orders placed by buyers/users.
              - Logout of the application
 - Customer
      
      - There are some preconfigured users in the application. 
                
                - username: "Pradeep"  password: "passw"
                - username: "Suresh"  password: "passw"
                - username: "Amisha"  password: "passw" 
      
      - User can do following task on Application
                
                - Singup to the application. A new user can register on the application.
                - Login to the application. Existing users can singin to the application.         
                - Add products to cart.
                - Execute order.
                - Logout


## Technical Details:

- We are using H2 in memory database here. Data will be lost after the application has been stopped.
- We will populate the databse with some dummy data for products and users.
- Application runs on 8080 port.
- We are using spring data jpa to access database. 


# To Run the application just download the project and run the maven command from the project directory "mvn spring-boot:run"


            
