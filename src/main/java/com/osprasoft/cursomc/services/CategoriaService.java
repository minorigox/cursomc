package com.osprasoft.cursomc.services;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osprasoft.cursomc.repositories.CategoriaRepository;
import com.osprasoft.cursomc.domain.Categoria;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Integer id) {
        Optional < Categoria > obj = repo.findById(id);
        return obj.orElse(null);
    }
    
}
