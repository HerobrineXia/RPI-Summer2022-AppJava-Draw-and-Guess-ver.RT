# Draw and Guess - Real Time ver.

<div style="width: 60%; height: 60%">
  
  ![UI1](https://user-images.githubusercontent.com/31975605/185590628-5df8d6b9-1595-443d-8793-611b6e509646.png)
  
</div>


Draw and Guess ver. Real-Time is an online word-guessing game. In the game, we form groups of 2-8 players and the system will designate one player as the painter according to the order of connection and randomly search for a word in the dictionary. The player needs to use the mouse to draw the specific word given by the system. Meanwhile, other players will try to guess the word. At the end of the round, another player will become the painter and the system will also give a new secret word as a reciprocal for the player to score. 

<div style="width: 60%; height: 60%">
  
  ![UI-2](https://user-images.githubusercontent.com/31975605/192407710-0c5297cc-193f-4456-b561-f3821eac4de0.png)
  
</div>

Yuetian Chen was responsible for the project planning, GUI design and GUI implementation. Kevin Xia worked on the network, model and controller. Jeff Li worked on the dictionary, protocol, user manual and test manual. Everyone in our group was involved in the testing work. All of the content was implemented in Java.

## Environment

Code and scripts are tested under the following settings.

### Linux

#### Distribution

Virtualbox (Oracle VM VirtualBox VM Runner v6.1.34) used.

#### JDK

```bash
$ javac --version
javac 15.0.1
$ java --version
openjdk 15.0.1 2020-10-20
OpenJDK Runtime Environment (build 15.0.1+0-adhoc..jdk15u-jdk-15.0.1-ga)
OpenJDK 64-Bit Server VM (build 15.0.1+0-adhoc..jdk15u-jdk-15.0.1-ga, mixed mode, sharing)
```


### Windows


#### Distribution
```cmd
Version Windows 10 Home Chinese\
Version number 21H2\
OS Build 19044.1741\
Experience Windows Feature Experience Pack 120.2212.4180.0\
Device name LAPTOP-FI44MN4P\
Processor Intel(R) Core(TM) i7-10875H CPU @ 2.30GHz 2.30 GHz\
On-board RAM 16.0 GB (15.8 GB available)\
```
#### JDK

```cmd
C:\Users\ASUS>javac --version
javac 18.0.1.1

C:\Users\ASUS>java --version
java 18.0.1.1 2022-04-22
Java(TM) SE Runtime Environment (build 18.0.1.1+2-6)
Java HotSpot(TM) 64-Bit Server VM (build 18.0.1.1+2-6, mixed mode, sharing)
```

## Usage

The following command are under project scripts directory

### Linux


#### RunDrawAndGuess

```bash
sh ./run.sh
```
#### Compile Javadoc

```bash
sh ./javadoc.sh
```

### Windows

Open the cmd and nevigate to the project scripts directory

#### RunDrawAndGuess

```cmd
.\run.cmd
```

#### Compile Javadoc

```cmd
.\javadoc.cmd
```

## Project Structure

### `./res`

Where GUI Icons, fonts stored at.

### `./manuals`

It contains detailed descriptions of all application features, inputs and outputs,
formats of files, possible error messages and their meaning, and any other
information which might be of interest to an end user.

### `./lib`
External Library

It contains all tools including FlatLaf for the look and feel.

### `./Config.cfg`
Default setting

It is located at the root.

Config.cfg stored the default config for the program. Please do not manually
modify this file.


## Acknowledgement
This project was carried out in July 2022 in the CSCI 4963 Application Programming Using Java course. The intent is to demonstrate an end-to-end network communication and protocol design guidelines using Java and a highly customized development process using Java Swing.

This project is relatively sensitive to resolution and DPI. For the best experience, please experience the application in 1080p 100%-175% scale.

## Contributors

<details>
  <summary>Click me to Open/Close the contributors listing</summary>
  
- [Yuetian Chen](https://github.com/stry233) - Rensselaer Polytechnic Institute, 110 8th Street, Troy, NY, United States, 12180 (email: cheny63@rpi.edu)
- [Kewen Xia](https://github.com/HerobrineXia) - Rensselaer Polytechnic Institute, 110 8th Street, Troy, NY, United States, 12180
- [Jeff Li](https://github.com/yunqili0809) - Rensselaer Polytechnic Institute, 110 8th Street, Troy, NY, United States, 12180

</details>
<span id="nav-4"></span>
