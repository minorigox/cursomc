package com.osprasoft.cursomc.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.osprasoft.cursomc.repositories.PedidoRepository;
import com.osprasoft.cursomc.services.exception.ObjectNotFoundException;
import com.osprasoft.cursomc.domain.Pedido;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id) {
        Optional < Pedido > obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " 
            + Pedido.class.getName()));
    }
    
}
