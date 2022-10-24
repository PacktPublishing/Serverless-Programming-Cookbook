# Serverless Programming Cookbook

<a href="https://www.packtpub.com/application-development/serverless-programming-cookbook?utm_source=github&utm_medium=repository&utm_campaign=9781788623797 "><img src="https://d255esdrn735hr.cloudfront.net/sites/default/files/imagecache/ppv4_main_book_cover/9781788623797-%20Copy.png" alt="Serverless Programming Cookbook" height="256px" align="right"></a>

This is the code repository for [Serverless Programming Cookbook](https://www.packtpub.com/application-development/serverless-programming-cookbook?utm_source=github&utm_medium=repository&utm_campaign=9781788623797 ), published by Packt.

**Practical solutions to building serverless applications using Java and AWS	**

## What is this book about?
<span class="sugar_field" id="description">Managing physical servers will be a thing of the past once you’re able to harness the power of serverless computing. If you’re already prepped with the basics of serverless computing, Serverless Programming Cookbook will help you take the next step ahead. This recipe-based guide provides solutions to problems you might face while building serverless applications. </span>

This book covers the following exciting features:
Serverless computing in AWS and explore services with other clouds 
Develop full-stack apps with API Gateway, Cognito, Lambda and DynamoDB 
Web hosting with S3, CloudFront, Route 53 and AWS Certificate Manager 
SQS and SNS for effective communication between microservices 
Monitoring and troubleshooting with CloudWatch logs and metrics 
Explore Kinesis Streams, Amazon ML models and Alexa Skills Kit 

If you feel this book is for you, get your [copy](https://www.amazon.com/dp/1788623797) today!

<a href="https://www.packtpub.com/?utm_source=github&utm_medium=banner&utm_campaign=GitHubBanner"><img src="https://raw.githubusercontent.com/PacktPublishing/GitHub/master/GitHub.png" 
alt="https://www.packtpub.com/" border="5" /></a>

## Instructions and Navigations
All of the code is organized into folders. For example, Chapter02.

The code will look like the following:
```
Resources:
 MyFirstRestAPI:
 Type: AWS::ApiGateway::RestApi
 Properties:
 Name: Greeting API
```

**Following is what you need for this book:**
For developers looking for practical solutions to common problems while building a serverless application, this book provides helpful recipes. To get started with this intermediate-level book, knowledge of basic programming is a must.	

With the following software and hardware list you can run all code files present in the book (Chapter 1-10).
### Software and Hardware List
| Chapter | Software required | OS required |
| -------- | ------------------------------------ | ----------------------------------- |
| 1 | AWS account | Windows, Mac OS X, and Linux (Any) |
| 10 | Azure account, IBM cloud account, Google cloud account | Windows, Mac OS X, and Linux (Any) |

## How to use the code files

Completed code and AWS CLI commands for each chapter wherever applicable are present with the code files. 

Each chapter has a directory of its own (e.g. Chapter 1). 

Inside the chapter's directory there will be subdirectories for each recipe which has code files and/or AWS CLI commands. 
The recipe specific directory has names corresponding to the recipe's title. 
For example, directory for the Chapter 1 recipe titled 'Your first Lambda' is 'your-first-lambda'.  

Inside the recipe's directory there will be directory for storing all resources including the AWS CLI commands called 'resources'. 

The recipe's directory also contains a subdirectory for each Lambda project. 
You need to run 'mvn clean package' for generating the Lambda jar from within this directory. 
You can also run the S3 upload command for the generated Lambda JAR from this directory. 
The remainder of the CLI commands may be run from the recipe's root folder or the 'resources' folder.

Before running 'mvn clean package' for any Lambda project, you should run 'mvn clean install' from within the common Lambda parent project's directory 'serverless-cookbook-parent-aws-java', if not already done.

## Special handling for Windows operating system.

Within AWS CLI commands file, long commands are split into multiple lines for better readability using the '\\' symbol. 
If you are using a Windows machine you can use the '^' symbol instead of the  '\' symbol in the code files or make a single line command without the  '\' symbol. 

Also check the alternative solutions section below.

## Alternative Solutions

Code examples within the book follows the AWS documentation style and is tested primarily on Mac operating system. 
It should also work on most Unix based operating systems such as Linux. 
For people using other operating systems such as Windows operating system, we will include alternative solutions within each recipe where we feel it is needed. 

If you find difficulty in executing any command then you may refer to the alternative_solutions folder within the resources directory for a recipe. 
You may also raise a issue on this GitHub repository page if you cannot find an alternative solution. 
However, please check all previous issues before raising a new issue.


### Related products
* Hands-On Serverless Applications with Go [[Packt]](https://www.packtpub.com/application-development/hands-serverless-applications-go?utm_source=github&utm_medium=repository&utm_campaign=9781789134612 ) [[Amazon]](https://www.amazon.com/dp/1789134617)

* Hands-On Serverless Applications with Kotlin [[Packt]](https://www.packtpub.com/application-development/hands-serverless-applications-kotlin?utm_source=github&utm_medium=repository&utm_campaign=) [[Amazon]](https://www.amazon.com/dp/1788993705)


## Get to Know the Author
**Heartin Kanikathottu**
is a senior software engineer and blogger with around 11 years of IT experience. He is currently working as a Senior Member of Technical Staff at VMware. He has previously worked with companies including Software AG, SAP Ariba and TCS. He has a masters degree in cloud computing and bachelors degree in computer science. He has completed 10 professional certifications on the areas of cloud computing, coding and design from companies including AWS, Pivotal, Oracle, Microsoft, IBM and Sun. He likes to share his technical knowledge through his blogs such as Heartin.tech, CloudMaterials.com and JavaJee.com. He also likes to mentor juniors and take technical sessions at work, meetups and conferences.


### Suggestions and Feedback
[Click here](https://docs.google.com/forms/d/e/1FAIpQLSdy7dATC6QmEL81FIUuymZ0Wy9vH1jHkvpY57OiMeKGqib_Ow/viewform) if you have any feedback or suggestions.
### Download a free PDF

 <i>If you have already purchased a print or Kindle version of this book, you can get a DRM-free PDF version at no cost.<br>Simply click on the link to claim your free PDF.</i>
<p align="center"> <a href="https://packt.link/free-ebook/9781788623797">https://packt.link/free-ebook/9781788623797 </a> </p>