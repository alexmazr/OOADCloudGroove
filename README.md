# OOAD Final Project: CloudGroove
Alex Mazur and Ryan Oliva

Our demo video is hosted here: https://www.youtube.com/watch?v=8pSpZPVeVD4

Our microservices live under the following directories:
1. SongService
2. UserService
3. upload
4. web

The relevent code for each service can be found by navicating to `serviceName/src/main`. There you will find two folders one named `resources` and another with a package name. 
Under the package name you will find the microservices main method (or entry point) as well as a set of directories containing code. The directories are named `controller` for REST API or Web controllers, `entity` for database entities. `repository` for database queries abstractions, or `util` that might contain other classes we needed. Not every microservice has all of these folders. 

There are two ways to run this project. You may download the `cgjars` directory and run the `run.sh` script in a CLI that has a JDK installed. This will require opening a browser at localhost:8080 and an internet connection. It might take a few minutes for the project to start running it using this method. 

Alternatively you may access our project running at http://13.58.108.211:8080/. This is the recommened way to interface with our project, as it does not matter what computer and environemnt you have setup. As long as you have a modern webbrowser with an internet connection you are good to go.
