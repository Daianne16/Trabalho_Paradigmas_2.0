%Trabalho prático de Paradigmas
%Nomes: Daianne Cynthia Leal, Marcélia Almeida Silva

%Primeira regra
compramos(X) :- gasto(_,X,_,_,_,_,_) .

%Segunda regra
somaLista([],0).
somaLista([H|T], Sum) :- somaLista(T, R1), Sum is H+R1 .
quantidade(X, Res) :- findall(Qtd, gasto(_,X,_,Qtd,_,_,_), Quantidades), somaLista(Quantidades, Res).

%Terceira regra
valor_total(X, Res) :- findall(Val, gasto(_,X,_,_,_,_,Val), Valores), somaLista(Valores, Res).

%Quarta regra ----- ERRADO
comprado_em(D,X) :- findall(P , gasto(D,P,_,_,_,_,_) , X).

%Quinta regra
total_compras(X, Res) :- findall(Val, gasto(_,_,X,_,_,_,Val), Valores), somaLista(Valores, Res).

%Sexta regra ----- NAO EXISTE
