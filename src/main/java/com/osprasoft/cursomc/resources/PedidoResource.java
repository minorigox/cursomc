package com.osprasoft.cursomc.resources;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.osprasoft.cursomc.domain.Categoria;
import com.osprasoft.cursomc.domain.Pedido;
import com.osprasoft.cursomc.dto.CategoriaDTO;
import com.osprasoft.cursomc.services.PedidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity < Pedido > find(@PathVariable @NonNull Integer id) {
        Pedido obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity < Void > insert(@Valid @RequestBody Pedido obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(
            "/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity < Page < Pedido >> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page, 
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage, 
            @RequestParam(value = "orderBy", defaultValue = "instante")String orderBy, 
            @RequestParam(value = "direction", defaultValue = "DESC")@NonNull String direction) {
        Page < Pedido > list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
