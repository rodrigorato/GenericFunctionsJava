#!/bin/bash

TESTS_DIR=./tests_outputs/

# Compile as described in the project guide
gradle compileJava build

# Run tests with Gradle and save output to file
for t in src/main/java/ist/meic/pa/GenericFunctions/examples/Test*.java; do
    test_name=$(basename $t .java)
    test_letter=$(echo $test_name | sed -r 's/Test//g')

    gradle clean run -PrunArgs='ist.meic.pa.GenericFunctions.examples.'$test_name -q > $TESTS_DIR$test_letter.my_out

    echo "Diff on "$test_name":"
    colordiff --strip-trailing-cr $TESTS_DIR$test_letter.my_out $TESTS_DIR$test_letter.out

done

