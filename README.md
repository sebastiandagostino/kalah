# Kalah

## Introduction

Kalah, also called Kalaha or Mancala, is a game in the mancala family invented
in the United States by William Julius Champion, Jr. in 1940. This game is
sometimes also called "Kalahari", possibly by false etymology from the Kalahari
desert in Namibia.

As the most popular and commercially available variant of mancala in the West,
Kalah is also sometimes referred to as Warri or Awari, although those names more
properly refer to the game Oware.

## The Game

This is a two player game with no AI to play in single mode.

### Board Setup

Each of the two players has his six pits in front of him. To the right of the six pits,
each player has a larger pit. At the start of the game, there are six stones in each
of the six round pits .

### Rules

#### Game Play
The player who begins picks up all the stones in any of his own pits, and sows the
stones on to the right, one in each of the following pits, including his own big pit.
No stones are put in the opponents' big pit. If the player's last stone lands in his
own big pit, he gets another turn. This can be repeated several times before it's
the other player's turn.

#### Capturing Stones
During the game the pits are emptied on both sides. 
When the last stone lands in an own empty pit, the player captures this stone and 
all stones in the opposite pit (the other players' pit) and puts them in his own 
big pit.

#### The Game Ends
The game is over as soon as one of the sides runs out of stones. The player who
still has stones in his pits keeps them and puts them in his/her big pit. The winner of
the game is the player who has the most stones in his big pit.

## Prerequisites

Java JDK 8 and Maven are required to run the project.

## Compiling

As in any Maven project, it may be required to run:

```bash
mvn clean install
```

## Running

Like any other Spring Boot Maven project, run:

```bash
mvn spring-boot:run
```

The resources local locations are:

* [Home](http://localhost:8080/)
* [Swagger](http://localhost:8080/swagger)
* [H2 DB Console](http://localhost:8080/h2-console)

For the time being, home redirects to swagger.

In order to access the H2 DB console, use this parameters:
<table>
  <tr>
    <th>Driver Class</th>
    <td>org.h2.Driver</td>
  </tr>
  <tr>
    <th>JDBC URL</th>
    <td>jdbc:h2:mem:testdb</td>
  </tr>
  <tr>
    <th>User Name</th>
    <td>sa</td>
  </tr>
  <tr>
    <th>Password</th>
    <td><blank/></td>
  </tr>
</table>

## Author

* **Sebastian D'Agostino**
