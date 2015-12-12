all:bin
	javac src/pizza/*.java -d bin/

execute:
	java -cp bin/ pizza.Main ${in}

bin:
	mkdir bin

clean:
	rm -rf bin
	rm -rf src/pizza/*~
	rm -rf *~
