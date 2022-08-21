cd ..
javadoc -cp "lib/flatlaf-2.4.jar" -author -version -encoding UTF-8 -sourcepath src $(find . -name *.java) -d docs
cd scripts