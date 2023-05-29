delivery date string
email string
lineItems array of Json Objects

whole document is a json object

Steps:
1.) Convert json object to java objects
2.) Process the java objects
3.) Convert the java objects back to json objects
4.) Insert to MongoDB

HTTP Request: http://localhost:8080/api/order

To deploy to railway mongodb:
On VS Code:
1.) railway login
2.) railway init

3.) Add mongodb service
a. Get connection string from railway > MongoDB > Connect > Mongo Connection URL and put in application.properties file
b. If need to import tables into mongodb on railway
• Open Studio 3T
• New Connection
• Paste Mongo Connection URL
• Give a Connection Name
• Connect
• Select Add Database by right click PracTestxxx on the left pane and give it a name
• Select Import Collection right click the database name and give it a name
• Add source by choosing the json files
• Click run to start import

4.) railway up

For insert many:
In postman: Post request
[{
"name": "Jason",
"email": "jasonljy90@gmail.com",
"deliveryDate": "2021-02-01",
"lineItem": [
{
"item" : "Diapers",
"quantity" : 24
}, {
"item" : "Milk",
"quantity" : 4
}
]
},
{
"name": "Toby",
"email": "toby90@gmail.com",
"deliveryDate": "2022-02-01",
"lineItem": [
{
"item" : "Pepper",
"quantity" : 4
}, {
"item" : "Coffee",
"quantity" : 2
}
]
}
]

#Email Verification

1. User create an account
2. Create the account and disable it
3. Send email to customer with a OTP to verify their account
4. If OTP is valid, enable the account
5. If invalid OTP, tell user token is OTP and generate a new token

Local Redis
1.) set REDISHOST=localhost
2.) set REDISPORT=6379
3.) On Command Prompt -> wsl
4.) On Command Prompt -> redis-server (to start the local redis server)
5.) On another Command Prompt Window -> redis-cli
6.) keys \* (to see if saved successfully) (flushall to clear database)
7.) get {keys} (to see value of saved data)

Here are the commands to retrieve key value(s):
• if value is of type string -> GET <key>
• if value is of type hash -> HGET or HMGET or HGETALL <key>
• if value is of type lists -> lrange <key> <start> <end>
• if value is of type sets -> smembers <key>
• if value is of type sorted sets -> ZRANGEBYSCORE <key> <min> <max>
• if value is of type stream -> xread count <count> streams <key> <ID>. https://redis.io/commands/xread
Use the TYPE command to check the type of value a key is mapping to:
• type <key>

```

```

Project Links and Things to do:

Signup/Login Verification: (Done)
https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/

OTP: (Done)
https://www.youtube.com/watch?v=C1GfXO81d5s

Compare Date: (Done)
https://www.callicoder.com/how-to-compare-date-time-java/

Generate secure random password: (Done)
https://www.baeldung.com/java-generate-secure-password

Live Chat:
https://www.baeldung.com/spring-kafka
https://www.youtube.com/watch?v=U17DtHLOsTU&list=PLGRDMO4rOGcNLwoack4ZiTyewUcF6y6BU
https://kafka.apache.org/quickstart
https://docs.spring.io/spring-kafka/reference/html/#introduction
https://mvnrepository.com/artifact/org.springframework.kafka/spring-kafka/3.0.5

KAFKA Steps:
1.) Go to Kafka home folder windows
cd C:\kafka

2.) Start ZooKeeper Server First (By default zookeeper runs on port 2181)
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

3.) Open new command prompt
cd C:\kafka

4.) Start Kafka Server
.\bin\windows\kafka-server-start.bat .\config\server.properties

5.) Open new command prompt
cd C:\kafka

6.) Create Topic
.\bin\windows\kafka-topics.bat --create --topic second-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

7.) Run Producer
.\bin\windows\kafka-console-producer.bat --topic second-topic --bootstrap-server localhost:9092

8.) Open new command prompt
cd C:\kafka

9.) Run Consumer
.\bin\windows\kafka-console-consumer.bat --topic second-topic --bootstrap-server localhost:9092

10.) Type messages on Producer and press Enter to send, Consumer will receive the message

Stripe Payment API:
https://www.baeldung.com/java-stripe-api

Deployment:
https://docs.cloudfoundry.org/buildpacks/java/getting-started-deploying-apps/gsg-spring.html

Local Redis
1.) set REDISHOST=localhost
2.) set REDISPORT=6379
3.) On Command Prompt -> wsl
4.) On Command Prompt -> redis-server (to start the local redis server)
5.) On another Command Prompt Window -> redis-cli
6.) keys \* (to see if saved successfully) (flushall to clear database)
7.) get {keys} (to see value of saved data)

To Run Program:

Kafka
1.) Go to Kafka home folder windows
cd C:\kafka

2.) Start ZooKeeper Server First (By default zookeeper runs on port 2181)
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

3.) Open new command prompt
cd C:\kafka

4.) Start Kafka Server
.\bin\windows\kafka-server-start.bat .\config\server.properties

5.) mvn spring-boot:run

6.) On Browser http://localhost:8080/api/kafka/publish?message=hello world

7.) To see if message sent successfully

8.) Open new command prompt
cd C:\kafka

9.) .\bin\windows\kafka-console-consumer.bat --topic tester --from-beginning --bootstrap-server localhost:9092

Countries Info API:
https://restcountries.com/#rest-countries

currencies
capital
region
subregion
languages
area
maps>googleMaps
population
flags>png

Travel Guide Api
https://developers.amadeus.com/my-apps/TravelGuideApp?userId=jasonljy90@gmail.com
KEY: CPKNN7rRAjnCeJxu0CT70Ac8bnS6tAGO
SECRET: S6esAxDsnLIsAhG4
TOKEN: 2zq5rP6vK6CQ7oXdt4aYRIK49NZY

CountryInfo.java
countryCode.put("japan", "JPY");
countryCode.put("albania", "ALB");
countryCode.put("algeria", "DZA");
countryCode.put("american Samoa", "ASM");
countryCode.put("andorra", "AND");
countryCode.put("angola", "AGO");
countryCode.put("anguilla", "AIA");
countryCode.put("antarctica", "ATA");
countryCode.put("antigua and Barbuda", "ATG");
countryCode.put("argentina", "ARG");
countryCode.put("armenia", "ARM");
countryCode.put("aruba", "ABW");
countryCode.put("australia", "AUS");
countryCode.put("austria", "AUT");
countryCode.put("bahamas", "BHS");
countryCode.put("bahrain", "BHR");
countryCode.put("bangladesh", "BGD");
countryCode.put("barbados", "BRB");
countryCode.put("belarus", "BLR");
countryCode.put("belgium", "BEL");
countryCode.put("belize", "BLZ");
countryCode.put("benin", "BEN");
countryCode.put("bermuda", "BMU");
countryCode.put("bhutan", "BTN");
countryCode.put("bolivia", "BOL");
countryCode.put("bonaire", "BES");
countryCode.put("bosnia and Herzegovina", "BIH");
countryCode.put("botswana", "BWA");
countryCode.put("bouvet Island", "BVT");
countryCode.put("brazil", "BRA");
countryCode.put("british Indian Ocean Territory", "IOT");
countryCode.put("brunei Darussalam", "BRN");
countryCode.put("bulgaria", "BGR");
countryCode.put("burkina Faso", "BFA");
countryCode.put("burundi", "BDI");
countryCode.put("cabo Verde", "CPV");
countryCode.put("cambodia", "KHM");
countryCode.put("cameroon", "CMR");
countryCode.put("canada", "CAN");
countryCode.put("cayman Islands", "CYM");
countryCode.put("central African Republic", "CAF");
countryCode.put("chad", "TCD");
countryCode.put("chile", "CHL");
countryCode.put("china", "CHN");
countryCode.put("christmas Island", "CXR");
countryCode.put("colombia", "COL");
countryCode.put("comoros", "COM");
countryCode.put("congo", "COD");

return countryCode.get(countryName.toLowerCase());

https://developers.amadeus.com/blog/flight-booking-application-java-spring-react-1
