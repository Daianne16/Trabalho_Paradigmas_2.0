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
%Versão 2
p_comprado_em(D,X) :- findall(Prod , gasto(D,Prod,_,_,_,_,_) , X).

%Quinta regra
total_compras(X, Res) :- findall(Val, gasto(_,_,X,_,_,_,Val), Valores), somaLista(Valores, Res).

%Sexta regra ----- NAO EXISTE
%Faz a soma das quantidades
pega_qtds([], []) .
pega_qtds([H|T], Res) :- pega_qtds(T,Prov), findall(Qtd, gasto(_,H,_,Qtd,_,_,_), Produtos), somaLista(Produtos, QtdTotal),  Res = [[H,QtdTotal]|Prov].
%Faz a comparação do maior
produto_mais_comprado(X) :- findall(Prod, gasto(_,Prod,_,_,_,_,_), Produtos), sort(Produtos, ListaParaSoma), pega_qtds(ListaParaSoma, X).

