#!/bin/bash

# Look for source files in priority: .c, .cpp, .java
for file in *; do
    case "$file" in
        *.c)
            echo "Compiling C source: $file"
            gcc "$file" -o SerialChecker
            chmod +x SerialChecker
            echo "Output: SerialChecker"
            exit 0
            ;;
        *.cpp)
            echo "Compiling C++ source (C++17): $file"
            g++ -std=c++17 "$file" -o SerialChecker
            chmod +x SerialChecker
            echo "Output: SerialChecker"
            exit 0
            ;;
        *.java)
            echo "Compiling Java source: $file"
            javac "$file"
            if [[ -f "SerialChecker.class" ]]; then
                chmod +x SerialChecker.class
                echo "Output: SerialChecker.class"
            else
                echo "Compilation done, but SerialChecker.class not found. Check the class name in the source file."
            fi
            exit 0
            ;;
    esac
done

echo "No source file with .c, .cpp, or .java extension found."
