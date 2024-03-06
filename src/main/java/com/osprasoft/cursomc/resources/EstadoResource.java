package com.osprasoft.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osprasoft.cursomc.domain.Estado;
import com.osprasoft.cursomc.dto.EstadoDTO;
import com.osprasoft.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity < List < EstadoDTO >> findAll() {
        List < Estado > list = service.findAll();
        List < EstadoDTO > listDto = list.stream()
                .map(obj -> new EstadoDTO(obj.getId(), obj.getNome()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
