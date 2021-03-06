# SchoolTimetable
This program can build a school timetable by running generic algorithm.

The table can save as csv.

## Installation
```cmd=
git clone https://github.com/RickCompileError/SchoolTimetable.git
```

## Usage
1. click jar.jar can run the app
2. click run.bat can look some info in prompt

## Build data
### Variable
- fixed (0/1) : class time is fixed, 1 is fixed, 0 is not fixed
- Class name : can't empty
- major (0/1) : class is compulsory or elective class, 1 is compulsory, 0 is elective
- grade (1~5)
- week (1~5)
- start : class start time
- end : class end time
- teacher name : can empty
- total hour (2~3 hour)
- <> : a replacement is to be made
- \* : at least 0 or more
- \+ : at least 1 or more
### Format
delimiter can use ',' or ' '
```txt
Population
<Positive Integer>
Administrator
(<teacher name>,)+
Forbidden
(<teacher name>,(<week>,<start>,<end>,)+)*
                            .
                            .
                            .
Fixed (1) Class name major (0/1) grade (1~5) week (1~5) start end teacher name
(1,<Class name>,<major>,<grade>,<week>,<start>,<end>,<teacher name>)*
                            .
                            .
                            .
Not fixed (0) Class name major (0/1) grade (1~5) total hour (2~3) teacher name
(0,<Class name>,<major>,<grade>,<total hour>,<teacher name>)*
                            .
                            .
                            .
```

## Notice
- **compile.cmd** can compile the project and generate jar file named jar.jar
- This project can only accept csv and txt file
- Input format is limited, example can refer to input.txt & Example.csv
