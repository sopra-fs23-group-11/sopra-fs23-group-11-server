<img src=https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-client/blob/main/src/ReadMePics/WelcomeToBattleShip.JPG />

# Battleship Game - Server

## Table of Content

- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Main Components](#main-components)
- [Launch](#launch)
- [Deployment](#deployment)
- [Roadmap](#roadmap)
- [Authors and Acknowledgment](#authors-and-acknowledgment)
- [License](#license)

## Introduction
Welcome to the Battleship Game project! This is a digital implementation of the classic strategy game where players engage in a battle of wits and strategy on the high seas. The game challenges players to strategically position their fleet of ships on a grid and tactically guess the locations of their opponent's ships.

This project aims to provide an enjoyable gaming experience while showcasing the use of modern web technologies. Whether you're a fan of the original board game or simply looking for a fun and engaging online game, Battleship Game offers an immersive experience that will keep you hooked.

[Play here.](http://sopra-fs23-group-11-client.oa.r.appspot.com/)


## Technologies Used
* [Spring](https://spring.io/projects/spring-framework) - Framework that enables running JVM
* [Gradle](https://gradle.org/) - Build automation tool
* <img src="https://user-images.githubusercontent.com/91155454/170843632-39007803-3026-4e48-bb78-93836a3ea771.png" width="16" height="16" /> [STOMP](https://stomp-js.github.io/stomp-websocket/) - Used for Websockets
* <img src="https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-client/blob/main/src/ReadMePics/DicebearAPI.JPG" width="16" height="16" />[Dicebear API](https://www.dicebear.com/) - For creating Avatars

## Main Components

The [controllers](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/tree/main/src/main/java/ch/uzh/ifi/hase/soprafs23/controller) (eg. GameController) act as the receivers of REST and WebSocket calls. They handle incoming requests and delegate the necessary tasks to the appropriate services.<br />
The service plays a crucial role in the project as they encapsulate the core game logic and ensure the integrity of the gameplay. These services are responsible for checking and validating the game rules, handling player actions including the shooting mechanism, and managing the game state.<br />
Among the services, the [gameService](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/service/GameService.java) holds a key role. It oversees the overall game flow, tracks hits and misses, and determines the outcome of the game. This service acts as the central hub for managing the gameplay logic, ensuring fair and accurate gameplay.<br />
Another essential component is the [ShipPlayer](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/ships/ShipPlayer.java) and ShipPlayerService class. These classes are responsible for efficiently joining ship and player information and coordinating the placement of ships. They work closely with the [gameService](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/service/GameService.java) to update the game state and progress accordingly.<br />
WebSockets play a vital role in real-time communication and keeping players updated about ongoing game events. They facilitate the transmission of [messages](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/tree/main/src/main/java/ch/uzh/ifi/hase/soprafs23/WebSockets/Message) from the controllers to inform players about significant occurrences, such as successful hits, ship sinkings, and the end of the game. These [messages](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/tree/main/src/main/java/ch/uzh/ifi/hase/soprafs23/WebSockets/Message) keep players engaged and provide real-time feedback on the progress of the game.<br />
Overall, the controllers, services, and WebSockets form an integral part of the Battleship project. The controllers handle incoming requests, the services ensure proper game logic and functionality, and the WebSockets enable real-time communication and updates. This collaborative approach contributes to an immersive and engaging gaming experience.


## Launch

### Prerequisites
All dependencies are handled with Gradle. <br />
Download your IDE of choice (e.g., [IntelliJ](https://www.jetbrains.com/idea/download/), [Visual Studio Code](https://code.visualstudio.com/), or [Eclipse](http://www.eclipse.org/downloads/)). Make sure Java 17 is installed on your system (for Windows, please make sure your `JAVA_HOME` environment variable is set to the correct version of Java). \

### Clone Repository
Clone the client-repository onto your local machine with the help of [Git](https://git-scm.com/downloads).

```bash 
git clone https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server.git
```
You can find the corresponding client repository [here](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-client)

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```
You can verify that the server is running by visiting `localhost:8080` in your browser.

### Test

```bash
./gradlew test
```
We also recommend using [Postman](https://www.getpostman.com) to test your API Endpoints.

## Deployment
The main branch is automatically mirrored onto Google Cloud App Engine via GitHub workflow, each time you push onto the main branch. 

### Create Releases
- [follow GitHub documentation](https://docs.github.com/en/repositories/releasing-projects-on-github/managing-releases-in-a-repository)
- Database is reset during a release with the current settings!

## Roadmap
- Different Game modes
- Different shot types (salvo, grenade, atomic bomb)
- Make game responsive for mobile screens

## Authors and Acknowledgment

### Authors
* **Kalil Subaan Buraaleh** - [kalilsub](https://github.com/kalilsub)
* **Nazek Olabi** - [Olabi98](https://github.com/Olabi98)
* **Louis Zürcher** - [LouisZuercher2](https://github.com/LouisZuercher2)
* **Qing Dai** - [qing-dai](https://github.com/qing-dai)
* **Nick Schlatter** - [Nickschlaedde](https://github.com/Nickschlaedde)

### Acknowledgments
We would like to thank our TA [Isabella](https://github.com/bellachesney) and the whole team of the course Software Engineering Lab from the University of Zurich.

## License
This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/blob/main/LICENSE) file for details.
