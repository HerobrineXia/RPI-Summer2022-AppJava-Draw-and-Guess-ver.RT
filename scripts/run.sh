echo "[MSG] Compiling..."
cd ..
javac -cp ./lib/flatlaf-2.4.jar -sourcepath src src/edu/rpi/cs/csci4963/u22/cheny63/project/drawAndGuess/Launcher.java
java -classpath ./src:./lib/flatlaf-2.4.jar edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.Launcher
cd scripts