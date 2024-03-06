package com.osprasoft.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osprasoft.cursomc.domain.Cidade;
import com.osprasoft.cursomc.dto.CidadeDTO;
import com.osprasoft.cursomc.services.CidadeService;

@RestController
@RequestMapping(value = "/")
public class CidadeResource {
    
    @Autowired
    private CidadeService service;

    @RequestMapping(value = "estados/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity < List < CidadeDTO >> findCidades(@PathVariable Integer estadoId) {
        List < Cidade > list = service.findByEstado(estadoId);
        List < CidadeDTO > listDto = list.stream().map(obj -> new CidadeDTO(obj.getId(), obj.getNome())).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
