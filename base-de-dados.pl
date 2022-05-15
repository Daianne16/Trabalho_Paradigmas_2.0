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

%Quarta regra
comprado_em(D,X) :- findall([Prod, Loja, Qtd] , gasto(D,Prod,Loja,Qtd,_,_,_) , X).

%Quinta regra
total_compras(X, Res) :- findall(Val, gasto(_,_,X,_,_,_,Val), Valores), somaLista(Valores, Res).

%Sexta regra ----- NAO EXISTE


gasto('11-03-2022','tijolo','joao',10.0,'unidade',25.0,840.0).
gasto('10-01-2021','cimento','jose',50.0,'saco',24.0,650.0).
gasto('20-05-2020','tijolo','joao',25.0,'unidade',22.0,560.0).