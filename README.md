## Technologies Used

<ul>
  <li>Java - 21</li>
  <li>Spring Boot - 3.5.0</li>
  <li>Spring Boot Starter Web</li>
  <li>Spring Boot Starter Data JPA</li>
  <li>H2 Database</li>
  <li>Lombok</li>
</ul>

## Step by Step to Run the API

<ul>
    <li>  

Ensure you have a csv file inside [directory](src/main/resources/data) "src/main/resources/data" as per the requirements specified [CSV File Specification](#CSV-File-Specification)
    </li>
    <li> 
      
 Run the class: [GoldenraspberryawardsApplication](src/main/java/com/outsera/goldenraspberryawards/GoldenraspberryawardsApplication.java) "src/main/java/com/outsera/goldenraspberryawards/GoldenraspberryawardsApplication.java"
    </li>
    <li> 

Run the endpoint: http://localhost:8080/api/producers/award-interval -  GET  
    </li>
</ul>

## CSV File Specification

<ul>
    <li>File name should be movielist.csv</li>
    <li>The first line should contain the following strings: "year;title;studios;producers;winner" </li>
    <li>The next lines should contain the following example: 1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes</li>
    Note: the winner column it is not required
</ul>