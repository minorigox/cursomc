package com.osprasoft.cursomc.domain;

import com.osprasoft.cursomc.domain.enums.EstadoPagamento;
import jakarta.persistence.Entity;

@Entity
public class PagamentoCartao extends Pagamento {
    private static final long serialVersionUID = 1L;
    private Integer numeroParcelas;

    public PagamentoCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroParcelas) {
        super(id, estado, pedido);
        this.numeroParcelas = numeroParcelas;
    }

    public PagamentoCartao() {
    }
    
    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }
    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }
}
