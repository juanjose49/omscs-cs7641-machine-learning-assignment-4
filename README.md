#OMSCS: Machine Learning - Assignment 4


Greetings! The purpose of this readme file is to offer the reader the steps required to make use of this BURLAP machine learning package with several of my own custom classes (included) and Eclipse (not included). This readme is split by the different Markov Decision Processes that were required for completion of the assignment. The instructions below will assume that you have already successfully downloaded and opened Eclipse.

##Running the Grid World: Low Difficulty Analysis:

1. While inside the directory structure burlap-assignment-4/src/burlap/assignment4/ right-click on the EasyGridWorldLauncher.
2. Go to the “Run As…” section and select “Java Application.
3. All three algorithms will run and the aggregate analysis and optimal policies will be printed to the console.

##Running the Grid World: High Difficulty Analysis:

1. While inside the directory structure burlap-assignment-4/src/burlap/assignment4/ right-click on the HardGridWorldLauncher.
2. Go to the “Run As…” section and select “Java Application.
3. All three algorithms will run and the aggregate analysis and optimal policies will be printed to the console.

##Sample Output
This is the sort of output you get out of the box by running the HardGridWorldLauncher as a Java Application:

```
/////Hard Grid World Analysis/////

This is your grid world:
[0,0,0,0,0,0,0,0,0,0,0]
[0,0,0,0,0,0,0,0,0,0,0]
[0,0,0,0,0,0,0,0,0,0,0]
[0,0,0,1,1,1,1,1,0,0,0]
[0,0,0,1,0,0,0,1,0,0,0]
[0,0,0,1,0,0,0,1,0,0,0]
[0,0,0,1,0,0,0,1,0,0,0]
[0,0,0,1,1,1,1,1,0,0,0]
[0,0,0,0,0,0,0,0,0,0,0]
[0,0,0,0,0,0,0,0,0,0,0]
[0,0,0,0,0,0,0,0,0,0,1]



//Value Iteration Analysis//
Passes: 1
...
Passes: 20
Value Iteration,5217,1850,620,589,53,40,34,29,25,24,33,30,29,30,22,25,27,27,21,27

This is your optimal policy:
num of rows in policy is 11
[>,>,>,>,>,>,>,>,>,>,<]
[>,>,>,>,>,>,>,>,>,^,^]
[>,>,>,>,>,>,>,>,>,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,>,>,>,>,>,>,^,^,^]
[^,>,>,>,>,>,>,>,^,^,^]
[>,>,>,>,>,>,>,>,^,^,*]



Num generated: 1500; num unique: 95
//Policy Iteration Analysis//
Total policy iterations: 1
...
Total policy iterations: 20
Policy Iteration,1616,994,119,461,515,645,77,37,24,21,31,29,26,26,28,31,25,28,28,31
Passes: 19

This is your optimal policy:
num of rows in policy is 11
[>,>,>,>,>,>,>,>,>,>,v]
[>,>,>,>,>,>,>,>,>,^,^]
[>,>,>,>,>,>,>,>,>,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,^,*,*,*,*,*,^,^,^]
[^,^,>,>,>,>,>,>,^,^,^]
[^,>,>,>,>,>,>,>,^,^,^]
[>,>,>,>,>,>,>,>,^,^,*]



//Q Learning Analysis//
Q Learning,709,760,114,88,132,132,293,396,279,139,111,171,42,149,117,74,104,214,81,72
Passes: 19

This is your optimal policy:
num of rows in policy is 11
[^,^,^,>,>,>,<,>,>,>,>]
[v,<,^,>,v,^,^,>,^,<,^]
[^,>,>,<,>,>,>,v,^,<,^]
[^,^,^,*,*,*,*,*,>,^,^]
[^,>,v,*,*,*,*,*,^,v,<]
[v,v,^,*,*,*,*,*,<,^,^]
[v,^,^,*,*,*,*,*,^,^,<]
[^,^,v,*,*,*,*,*,^,^,^]
[<,^,>,>,>,v,v,>,>,>,<]
[>,^,<,>,<,>,^,>,>,^,^]
[>,^,<,v,>,>,v,<,^,^,*]



//Aggregate Analysis//

The data below shows the number of steps/actions the agent required to reach 
the terminal state given the number of iterations the algorithm was run.
Iterations,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20
Value Iteration,5217,1850,620,589,53,40,34,29,25,24,33,30,29,30,22,25,27,27,21,27
Policy Iteration,1616,994,119,461,515,645,77,37,24,21,31,29,26,26,28,31,25,28,28,31
Q Learning,709,760,114,88,132,132,293,396,279,139,111,171,42,149,117,74,104,214,81,72

The data below shows the number of milliseconds the algorithm required to generate 
the optimal policy given the number of iterations the algorithm was run.
Iterations,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20
Value Iteration,88,6,8,10,12,15,17,18,15,15,17,16,21,18,20,21,23,33,34,43
Policy Iteration,15,6,9,12,15,15,33,19,20,23,25,27,40,52,56,59,49,39,41,45
Q Learning,11,10,6,8,11,8,7,8,12,13,13,17,15,10,10,11,11,12,10,11
```
