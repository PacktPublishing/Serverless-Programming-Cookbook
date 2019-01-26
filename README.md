# Serverless Cookbook Code Files and Resources

This is the parent repository for code-files and resources for the book 'Serverless Programming Cookbook' (author: Heartin Kanikathottu. publisher: Packt Publishing).

## How to use the code files

Completed code and AWS CLI commands for each chapter wherever applicable are present with the code files. 

Each chapter has a directory of its own (e.g. Chapter 1). 

Inside the chapter's directory there will be subdirectories for each recipe which has code files and/or AWS CLI commands. The recipe specific directory has names corresponding to the recipe's title. For example, directory for the Chapter 1 recipe titled 'Your first Lambda' is 'your-first-lambda'.  

Inside the recipe's directory there will be directory for storing all resources including the AWS CLI commands called 'resources'. 

The recipe's directory also contains a subdirectory for each Lambda project. You need to run 'mvn clean package' for generating the Lambda jar from within this directory. You can also run the S3 upload command for the generated Lambda JAR from this directory. The remainder of the CLI commands may be run from the recipe's root folder or the 'resources' folder.

Before running 'mvn clean package' for any Lambda project, you should run 'mvn clean install' from within the common Lambda parent project's directory 'serverless-cookbook-parent-aws-java', if not already done.

## Special handling for Windows operating system.

Within AWS CLI commands file, long commands are split into multiple lines for better readability using the '\\' symbol. If you are using a Windows machine you can use the '^' symbol instead of the  '\' symbol in the code files or make a single line command without the  '\' symbol.
