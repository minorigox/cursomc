package com.osprasoft.cursomc.services;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.osprasoft.cursomc.repositories.ItemPedidoRepository;
import com.osprasoft.cursomc.repositories.PagamentoRepository;
import com.osprasoft.cursomc.repositories.PedidoRepository;
import com.osprasoft.cursomc.services.exception.ObjectNotFoundException;
import com.osprasoft.cursomc.domain.ItemPedido;
import com.osprasoft.cursomc.domain.PagamentoBoleto;
import com.osprasoft.cursomc.domain.Pedido;
import com.osprasoft.cursomc.domain.enums.EstadoPagamento;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(@NonNull Integer id) {
        Optional < Pedido > obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " 
            + Pedido.class.getName()));
    }

    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoBoleto) {
            PagamentoBoleto pagto = (PagamentoBoleto) obj.getPagamento();
            boletoService.preencherPagamentoBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }
    
}
