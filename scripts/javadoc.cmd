@ECHO OFF
cd ..
javadoc -cp "lib\flatlaf-2.4.jar" -author -version -encoding UTF-8 -sourcepath src edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess -subpackages . -d docs
cd scripts
echo Done, enjoy!
pause

