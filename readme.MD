# Draw and Guess - Real Time ver.

![UI1](https://user-images.githubusercontent.com/31975605/185590628-5df8d6b9-1595-443d-8793-611b6e509646.png)

## Environment

Code and scripts are tested under the following settings.

### Linux

#### Distribution

Virtualbox (Oracle VM VirtualBox VM Runner v6.1.34) used.

```bash
$ uname -a
Linux yunqili-VirtualBox 5.13.0-48-generic #54~20.04.1-Ubuntu SMP Thu Jun 2 23:37:17 UTC 2022 x86_64 x86_64 x86_64 GNU/Linux
```

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
Install Date ‎2021/‎1/‎5\
OS Build 19044.1741\
Experience Windows Feature Experience Pack 120.2212.4180.0\
Device name LAPTOP-FI44MN4P\
Processor Intel(R) Core(TM) i7-10875H CPU @ 2.30GHz 2.30 GHz\
On-board RAM 16.0 GB (15.8 GB available)\
Device ID D6C5240F-FEBE-4998-9C3C-164DD59C6359\
Product ID 00342-35835-21595-AAOEM\
System type 64-bit operating system, x64-based processor\
Pen and Touch No pen or touch input available for this monitor
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


## Others

### `res/`

Where GUI Icons, fonts stored at.
