# Welcome to Plants Vs Zombies!

In this document, simple instructions on how to play Group 5's Plants Vs Zombies v1.0 will be given.


## Launching the game

To launch the game, first ensure that you have the PlantsVZombies.jar file downloaded. Next, navigate to the .jar file's directory with your computer's terminal/command prompt, and enter the following command:

    java -jar PlantsVZombies.jar

If you have Java installed correctly, you will see a welcome message printed by the game.

![](images/launching.png?raw=true)

## Commands

Now that you've got the game started, it's time to learn about the different commands you can use to interact with the game.
The most important command is **help**, as it will let you view more information about the other commands in-game.

![](images/help.png?raw=true)

The **place <planttype> <x-position> <y-position>** command will let you place a plant at the specified coordinates on the level.

Valid plant types are *sunflower* and *peashooter*.

The **next turn** command will advance the game to the next turn.

When the game is advanced to the next turn, the current level state will be output, along with some other useful information about the turn.

![](images/turn.png?raw=true)


The **quit** and **restart** commands should be pretty self-explanatory 😊

## The level

This version of the game comes with only one level.

The level is a grid, and in each grid cell, a plant or zombie can exist.

During your first few turns of a new level, you'll want to place some plants near the left side of the grid. After a few turns, zombies will begin to enter from the right, and your plants will attack them.

The image below has a demo of what the level grid will look like with some *peashooters* and a *sunflower* placed on the left hand side, and *zombies* attacking from the right.

![](images/level.png?raw=true)

## Plant types

In this early version of the game there are two different plant types.

  * **Peashooter:**

   The peashooter is the "attack" plant. It shoots four shots per turn, and each shot does **100 damage** to any zombie it hits.

   You can only place a new peashooter every **two turns**. It costs **100 sun points** to place.

  * **Sunflower:**

   The sunflower is a plant that generates more sun points while placed. It generates **25 sun points** every two turns.

   You can only place a new sunflower every **two turns**. It costs **50 sun points** to place.

## Zombies

There's currently only one type of zombie in the game.

Zombies spawn on random rows to the far right of the level, and progress one column to the left every turn.

Zombies have **600 health**.

If a zombie is on the same tile as a plant (while still being alive), the plant will immediately die.
