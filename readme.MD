# Draw and Gues - Real Time ver.


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
$ javac -version
javac 1.8.0_312
$ java --version
openjdk version "1.8.0_312"
OpenJDK Runtime Environment (build 1.8.0_312-8u312-b07-0ubuntu1~20.04-b07)
OpenJDK 64-Bit Server VM (build 25.312-b07, mixed mode)
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

The following command are under project root directory

### Linux

#### Compile

```bash
sh scripts/compile.sh
```

#### RunDrawAndGuess

```bash
sh scripts/run.sh
```

### Windows

Open the cmd and nevigate to the project root directory

#### Compile

```cmd
.\scripts\compile.cmd
```

#### RunDrawAndGuess

```cmd
.\scripts\runGUI.cmd
```


## Others

### `tests/`

Where examples/tests files stored. Read `manual.pdf` and `tests.pdf` for more info
