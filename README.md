# Serverless Cookbook Code Files and Resources

This is the parent repository for code-files and resources for the book 'Serverless Programming Cookbook' (author: Heartin Kanikathottu. publisher: Packt Publishing).

## How to use the code files

Completed code and AWS CLI commands for each chapter wherever applicable are present with the code code files. Each chapter has a directory of its own (e.g. Chapter 1). Inside the chapter's directory there will be subdirectory for each recipe which has code files or WS CLI commands. 

The recipe specific directory has names corresponding to that recipe's title. Consider Chapter 1 recipe titled 'Your first Lambda with AWS CLI'.  Corresponding Lambda project directory is named as 'lambda-handler-with-pojos'. You run 'mvn clean package' for generating the Lambda jar within this directory. 

Before running 'mvn clean package' for any Lambda project, you should have run 'mvn clean install' from within the common Lambda parent project available within the root folder as 'serverless-cookbook-parent-aws-java'.

All resources including AWS CLI commands are available within another subdirectory called 'resources'within the recipe specific folder same level as the Lambda project directory. 