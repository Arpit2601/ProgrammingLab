/*
    variables with capital letters and atoms with small letters
    romeo is atom
    and 
*/
 
parent(albert, bob).
parent(albert, betsy).
parent(albert, bill).
 
parent(alice, bob).
parent(alice, betsy).
parent(alice, bill).
 
parent(bob, carl).
parent(bob, charlie).


get_grandparent(X, Y) :- parent(Z, X),
                parent(Z, Y),
                format('~w ~s grandparent of ~w and ~w~n', [Z, "is the", X, Y]).


what_grade(5) :-
  write('Go to kindergarten').
what_grade(6) :-
  write('Go to first grade').
what_grade(Other) :-
  Grade is Other - 5,
  format('Go to grade ~w', [Grade]).

customer(tom, smith, 20.55).
customer(sally, smith, 120.55).