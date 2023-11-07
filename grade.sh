CPATH=".;lib/hamcrest-core-1.3.jar;lib/junit-4.13.2.jar"

# remove previous submission
rm -rf grading-area

git clone $1 grading-area &>/dev/null

# move our tester into the zone
cp -r grading-base/. grading-area/

cd grading-area

# check that the grading area has a ListExamples file
if ! [[ -f "ListExamples.java" ]]
then
  echo "fail: missing required file ListExamples.java"
  exit 1
fi

# previous approach
# https://stackoverflow.com/questions/962255/how-to-store-standard-error-in-a-variable
# compile_result=$(javac.exe -cp $CPATH *.java 2>&1)

# new approach
# attempt to compile the java code
# https://tecadmin.net/store-standard-error-to-a-bash-variable/
# https://www.baeldung.com/linux/silencing-bash-output
compile_result=`javac.exe -cp $CPATH *.java &> /dev/null`
status=$?
if [[ $status -ne 0 ]]
then
  echo "fail: failed to compile"
  exit 1
fi

# get the junit results
result=`java.exe -cp $CPATH org.junit.runner.JUnitCore TestListExamples`

if [[ $result == *OK* ]]
then
  echo "pass: completed all required tests"
  exit 0
else
  # https://stackoverflow.com/questions/17883661/how-to-extract-numbers-from-a-string
  numbers=`echo $result | grep -o -E "[0-9]+" | tail -n 2`
  tests_run=$((${numbers:0:1}))
  tests_failed=$((${numbers:1:2}))
  tests_passed=$(($tests_run - $tests_failed))
  
  if [[ $tests_passed -ge $tests_failed ]]
  then
    echo "pass: $tests_passed / $tests_run"
    exit 0
  else
    echo "fail: $tests_passed / $tests_run"
    exit 1
  fi
fi

# clean up grading process
# @audit should probably fix this with flags, this won't run because we exit before lol
cd ..
rm -rf grading-area