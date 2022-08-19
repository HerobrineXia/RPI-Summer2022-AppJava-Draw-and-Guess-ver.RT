@ECHO OFF
@ECHO OFF
cd ..
echo "Compiling..."
javac -cp .\lib\flatlaf-2.4.jar -sourcepath src src/edu/rpi/cs/csci4963/u22/cheny63/project/drawAndGuess/Launcher.java -d bin

echo "Press enter to let the application start"
PAUSE >nul
java -classpath .\lib;.\lib\flatlaf-2.4.jar edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.Launcher
cd scripts