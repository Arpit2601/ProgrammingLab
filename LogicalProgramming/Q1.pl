/*
    Database
*/
parent(jatin,avantika).
parent(jolly,jatin).
parent(jolly,kattappa).
parent(manisha,avantika).
parent(manisha,shivkami).
parent(bahubali,shivkami).

male(kattappa).
male(jolly).
male(bahubali).

female(shivkami).
female(avantika).

% Q1
uncle(X, Y) :-  male(X),
                parent(Z, Y),
                parent(A, Z),
                parent(A, X).


% Q2
halfsister(X, Y) :- female(Y),
                    parent(Z, X),
                    parent(Z, Y),
                    parent(A, X),
                    parent(B, Y),
                    A \== B,
                    Z \== A,
                    Z \== B.
