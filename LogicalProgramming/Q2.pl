% paths is of form (City, [optimal list of vertices from City to source], cost/distance/duration)
:- dynamic(pathsDistance/3).
:- dynamic(pathsDuration/3).
:- dynamic(pathsCost/3).

% Bus (Number, Origin, Destination Place, Departure Time, Arrival Time, Distance, Cost)
bus(1, 'Amingaon', 'Jalukbari', 14.5, 15, 10, 10).
bus(2, 'Amingaon', 'Chandmari', 16, 16.5, 7, 8).
bus(3, 'Jalukbari', 'Panbazar', 16, 16.5, 7, 8).
bus(4, 'Panbazar', 'Chandmari', 16, 16.5, 7, 8).
bus(5, 'Panbazar', 'Paltanbazar', 16, 16.5, 7, 8).
bus(5, 'Chandmari', 'Maligaon', 16, 16.5, 7, 8).
bus(5, 'Maligaon', 'Lokhra', 16, 16.5, 7, 8).

% Instantiate all the edgegs on the basis of cost, distance and time 
% Since bus database has directional paths edges will have undirectional
% paths as A->B is same as B->A with same cost/distance/duration
cost_edges(Bus_number, Source, Destination, Cost) :-
    bus(Bus_number, Source, Destination, _, _, _, Cost).
cost_edges(Bus_number, Destination, Source, Cost) :-
    bus(Bus_number, Source, Destination, _, _, _, Cost).

distance_edges(Bus_number, Source, Destination, Distance) :-
    bus(Bus_number, Source, Destination, _, _, Distance, _).
distance_edges(Bus_number, Destination, Source, Distance) :-
    bus(Bus_number, Source, Destination, _, _, Distance, _).
    
duration_edges(Bus_number, Source, Destination, Duration) :-
    bus(Bus_number, Source, Destination, Departure, Arrival, _, _),
    Duration is Arrival - Departure.
duration_edges(Bus_number, Destination, Source, Duration) :-
    bus(Bus_number, Source, Destination, Departure, Arrival, _, _),
    Duration is Arrival - Departure.
    
route(Source, Destination) :- 
    findShortestPathByDistance(Source, Destination).
    % findShortestPathByCost(Source, Destination),
    % findShortestPathByDuration(Source, Destination).

% start walking from Source and keep track of optimal path to every other city
% as soon as we reach Destination break and print path.
findShortestPathByDistance(Source, Destination) :-
    retractall(pathsDistance(_, _, _)),
    moveForDistance(Source),
    pathsDistance(Destination, _, _)
    ->(printPathDistance(Destination)).
    

moveForDistance(Source) :-
    moveForDistance(Source, [Source], [Source], 0).

moveForDistance(_).

% Visited contains the vertices already seen from source
% useful in case of cycles

% For all the neighbour edges first check if other side vertex is already visited or not
% if not then see if visiting it leads to shortest path
moveForDistance(From, Visited, Path, Distance) :-
    distance_edges(_, From, To, D),
    not(memberchk(To, Visited)),
    NewDistance is D+Distance,
    shortestPathToThisNeighbour(To, [To|Path], NewDistance),
    moveForDistance(To, [To|Visited], [To|Path], NewDistance).


shortestPathToThisNeighbour(City, NewPath, Distance) :-
    pathsDistance(City, _, D),
    !, Distance<D, 
    retract(pathsDistance(City, _, _)),
    % format('(~w < ~w) ~w is closer than ~w~n', [Distance, D, NewPath, PathToSource]), 
    assert(pathsDistance(City, NewPath, Distance)).

shortestPathToThisNeighbour(City, NewPath, Distance) :-
	% format('New path:~w ~w\n', [City, NewPath]), 
    assert(pathsDistance(City, NewPath, Distance)).

printPathDistance(Destination) :-
    pathsDistance(Destination, ReversePath, _),
    reverse(ReversePath, Path),
    printAtoB(Path, 0, 0, 0).
    % format('Distance: ~w, Time: ~w, Cost: ~w~n', [Distance, Cost, Time]).

printAtoB([A,B|T], Cost, Time, Distance) :-
    distance_edges(N, A, B, D),
    cost_edges(N, A, B, C),
    duration_edges(N, A, B, Duration),
    NewDistance is D+Distance,
    NewCost is Cost + C,
    NewTime is Time + Duration,
    format('~w,~w->', [A, N]),
    printAtoB([B|T], NewCost, NewTime, NewDistance).

printAtoB([A|[]], Cost, Time, Distance):- format('~w~n Distance: ~w, Cost: ~w, Time: ~w', [A,Distance, Cost, Time]).