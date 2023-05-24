# Battleship Game - Server

## Introduction
Welcome to the Battleship Game project! This is a digital implementation of the classic strategy game where players engage in a battle of wits and strategy on the high seas. The game challenges players to strategically position their fleet of ships on a grid and tactically guess the locations of their opponent's ships.

This project aims to provide an enjoyable gaming experience while showcasing the use of modern web technologies. Whether you're a fan of the original board game or simply looking for a fun and engaging online game, Battleship Game offers an immersive experience that will keep you hooked.

## Technologies Used
* [Spring](https://spring.io/projects/spring-framework) - Framework that enables running JVM
* [Gradle](https://gradle.org/) - Build automation tool
* [STOMP](https://stomp-js.github.io/stomp-websocket/) - Used for Websockets
* [Dicebear API](https://www.dicebear.com/) - For creating Avatars

## High-Level Components
 
### Game
The [Game](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/Game.java) is an essential component for the game as it defines the structure and relationships between various game elements. It includes an ID filed to uniquely identify each game. It handles the player associations and other game-related informations, such as the current state of the game.

 ### ShipPlayer
The [ShipPlayer](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/ships/ShipPlayer.java) entity plays a crucial role in managing the ships associated with the players in the game. It includes establishing a relationship between the ships and the player who owns it with a "Many-to-One" association. The component also manages the ship's positions with properties "startPosition" and "endPostion" which are important for validating the moves made by players during the game. Additionally the entity checks whether a ship has been sunk and which parts of the ship have been hit, which play an important role for the evaluation of the game's progress.
 
 ### Shot
The [Shot](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/Shot.java) is a major component for managing and tracking shots in the game. It establishes relationships between players, records shot positions and hit status, and provides the necessary structure to implement shot-related operations and logic within the game.

## Launch & Deployment

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```

## Roadmap
- Make Ships drag and drop at setup
- Different Game modes
- Different shot types (salvo, grenade, atomic bomb)
- Convert to IOS & Android native app

## Authors and Acknowledgment

### Authors
* **Kalil Subaan Buraaleh** - [kalilsub](https://github.com/kalilsub)
* **Nazek Olabi** - [Olabi98](https://github.com/Olabi98)
* **Louis Zürcher** - [LouisZuercher2](https://github.com/LouisZuercher2)
* **Qing Dai** - [qing-dai](https://github.com/qing-dai)
* **Nick Schlatter** - [Nickschlaedde](https://github.com/Nickschlaedde)



## License
This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/sopra-fs23-group-11/sopra-fs23-group-11-server/blob/main/LICENSE) file for details.
