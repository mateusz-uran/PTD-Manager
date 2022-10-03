<a name="readme-top"></a>
<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="readme-img/logo.png" alt="Logo">
  </a>

  <h3 align="center">Professional Truck Driver System Managment</h3>

  <p align="center">
    Secured system designed to speed up and facilitate data management.
    <br />
    <br />
    <a href="http://ptdmanager-env.eba-uuwpwxj2.eu-central-1.elasticbeanstalk.com/">View Demo</a>
  </p>
  <p>
  <h4>Login as admin:</h4>
    Login: ptd-admin@o2.pl </br>
    Password: admin
  <h4>Login as user:</h4>
    Login: ptd-user@o2.pl </br>
    Password: user
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

[![Main page Screen Shot][main-page]]((http://ptdmanager-env.eba-uuwpwxj2.eu-central-1.elasticbeanstalk.com/))

[PTD Manager](http://ptdmanager-env.eba-uuwpwxj2.eu-central-1.elasticbeanstalk.com/) is system that helps professional truck driver to manage data during his trip.
Secured environment provides features for example automatic counting final results like fuel or the most important mileage. 
The end result is a formatted pdf file filled with the data that the driver provided. Application is available on PC and mobile.

Features:
* Driver can create card with unique key and store data like fuel consumption and mileage. At the end everything is automatically calculated.
* The final result is that those stored and sorted data are exported to formatted pdf file which driver can download.
* The application allows you to manage multiple drivers at the same time thanks to profiles. Each driver has his own environment and can't ingerent into data of         another driver.
* Only admin or owner can see every card and add, update, delete driver or user. 
* Thanks to custom code generator only allowed users can register to application, confirmation email is sended and then user can log in.

### Built With

Application is build on 2.7 spring-boot version and java 11, server side is communicating with mysql 8.0. Frontend is managed by thymeleaf. Vehicle photos are stored in s3 bucket and library responsible for generating and exporting PDF is iText - specifically html2pdf.

[![Spring-Boot][Spring-Boot]][Spring-url]
[![MySQL][MySQL]][MySQL-url]
[![AWS][AWS]][AWS-url]
[![Thymeleaf][Thymeleaf]][Thymeleaf-url]
[![Flyway][Flyway]][Flyway-url]
[![iText][iText]][iText-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

Application is using few external tools like S3 Bucket from Amazon, iText plugin or Email Verification.

### Prerequisites

Requirements for application to work properly: 
* MySQL server
* S3 Bucket from Amazon
* Email verification to receive request from user who wanna register

### Installation

_Here I will explain how to get all together so application can work on your machine. Whole process is quite long so I will provide links._

1. Create AWS account, there is available free tier for one year [AWS free tier]
    - then create [AWS S3 bucket]
    - the most important is to get [access and secret key] to your account. Paste the correct username and password into the application.properties file.
2. Create [GMAIL] account or provide existing account passes.
    - *(it can be another provider but then you will need to change smtp server in appliction propreties)*
3. Create mysql database
    - [Creating and Selecting a Database]
    - Paste the correct username and password into the application.properties file.
5. If everything is done correct you can start application. Hibernate will go threw all flyway migration and create tables.
    - to be able to login you need to uncomment data loader runner which is creating default user
    ```
    @Override
    public void run(final ApplicationArguments args) throws Exception {
        userRepository.save(createAdmin());
        roleRepository.saveAll(addRoles());
        addRoleToDefaultUser();
    }
    ```
 6. Run application with maven 
    ```sh
    ./mvnw clean package -DskipTests
    ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Usage
1. **Activation code requeried to register new user. It's toggled when it's used, admin can see which code where used by which user.**
  ![Code table][code_used]
  
2. **Card table shows unique card number, author data, date of creation and if it's finished. Only finished card can be exported to PDF.**
  ![Card][card]
  
3. **User and Vehicle tables shows specific information about each user and vehicle signed to them.**
  ![User][user]
  ![Vehicle][vehicle]
  
4. **Card contains trip and fuel specification. Data are automatically summed up by pressing one button.**
  ![Card spec][card_spec]
  
5. **At the end when card is done and all data are summed user, admin or owner can generate formatted PDF.**
  ![Pdf front][pdf_first]
  ![Pdf second][pdf_second]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Email - mateusz.uranowski@onet.pl

My personal blog: [https://mateusz-uran.pl/](https://mateusz-uran.pl/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


[main-page]: readme-img/main_page.png
[Spring-Boot]: https://img.shields.io/badge/Spring--Boot-black?logo=springboot&logoColor=6DB33F
[Spring-url]: https://spring.io/
[MySQL]: https://img.shields.io/badge/MySQL-3e4149?logo=mysql&logoColor=%234479A1
[MySQL-url]: https://www.mysql.com/
[AWS]: https://img.shields.io/badge/AWS-fe9900?logo=amazonaws
[AWS-url]: https://aws.amazon.com/
[Thymeleaf]: https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf
[Thymeleaf-url]: https://www.thymeleaf.org/
[Flyway]: https://img.shields.io/badge/Flyway-CC0200?logo=flyway
[Flyway-url]: https://flywaydb.org/
[iText]: https://img.shields.io/badge/iText-084975
[iText-url]: https://itextpdf.com/

[AWS free tier]: https://aws.amazon.com/free/?all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=*all&awsf.Free%20Tier%20Categories=*all
[AWS S3 bucket]: https://docs.aws.amazon.com/AmazonS3/latest/userguide/creating-bucket.html
[GMAIL]: https://support.google.com/mail/answer/56256?hl=en
[access and secret key]: https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html
[Creating and Selecting a Database]: https://dev.mysql.com/doc/refman/8.0/en/creating-database.html

[code_used]: readme-img/access_code.png
[card]: readme-img/card.png
[user]: readme-img/user.png
[vehicle]: readme-img/vehicle.png
[card_spec]: readme-img/spec_card.png
[pdf_first]: readme-img/pdf_first.png
[pdf_second]: readme-img/pdf_second.png
