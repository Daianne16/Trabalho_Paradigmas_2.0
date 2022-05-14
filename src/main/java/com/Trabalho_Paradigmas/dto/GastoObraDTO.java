package com.Trabalho_Paradigmas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GastoObraDTO {
    public String dataCompra;
    public String produto;
    public String fornecedora;
    public Integer quantidade;
    public String tipoUnidade;
    public Double valorUnitario;
    public Double valorTotal;


}
