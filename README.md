Just a few words about this project..


Libraries / Technologies used :
Design Pattern : MVP 
 
Dependency Injection : Dagger2




On the launch screen we are going to see the calculator.


Some implementation specifics:

First of all this calculator takes as an input an arithmetic expression and it evaluates it at the end when
you press the result ("=") button. My approach on this is to use a set of enums for different cases for instance
CalculatorOperator which is a set of operators and a different enum for numbers etc. A list of those Enums
declared in the presenter drives the UI so any re-arrange of calculator's buttons is possible in a matter of minutes.

Additionally I wanted to test if my approach is extensible so that is why I 've implemented the Parenthesis
which is not part of the simple calculator but of the scientific one. I strongly believe that implementing the
full set of the scientific calculator would be a matter of couple more days ideally.



<img src=" https://user-images.githubusercontent.com/9167722/84258903-5ca76980-ab20-11ea-952b-96b940e8e81c.png  "width=100">