# devops-playground
Project for learning more about CI/CD pipelines and other devops/aws related services

## How to run the Java Sprint Boot backend
- Startup a codespace on the branch of your choice
- Run command in terminal: ./mvnw spring-boot:run
- Make port public
- Open "Ports" tab in the console
- Click on the forwarded address under port 8080 (default port)

## Roadmap
- [x] Set up basic backend using Java Springboot
- [x] Set up a db for storing data, potentially with MongoDB 
- Create some unit tests
- Create basic CI/CD pipeline using Github Actions which will include running unit tests and building docker image
- Set up an ECS cluster on AWS to deploy our application's docker image
- Automate docker image deployment via CI/CD pipeline
- Set up a VM instance using AWS EC2
- Create a script to execute within EC2 instance
- Create AWS Lambda function to execute script within EC2 instance and receive return value
- Use AWS SES to send emails

## Technologies
- Java SpringBoot
- AWS
- GitHub Actions
- MongoDB
