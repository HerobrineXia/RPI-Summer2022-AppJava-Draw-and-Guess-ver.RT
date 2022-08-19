@ECHO OFF
echo "Compiling..."
javac -cp .\lib\flatlaf-2.4.jar -sourcepath src src/edu/rpi/cs/csci4963/u22/cheny63/project/drawAndGuess/Launcher.java
echo "Compilation complete. Generating Javadoc..."
cd .\src
javadoc -author -version -encoding UTF-8 -sourcepath src edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess -d .\docs
