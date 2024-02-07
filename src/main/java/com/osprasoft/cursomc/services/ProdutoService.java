package com.osprasoft.cursomc.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.osprasoft.cursomc.domain.Categoria;
import com.osprasoft.cursomc.domain.Produto;
import com.osprasoft.cursomc.repositories.CategoriaRepository;
import com.osprasoft.cursomc.repositories.ProdutoRepository;
import com.osprasoft.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ProdutoRepository repo;

    public Produto find(@NonNull Integer id) {
        Optional < Produto > obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " 
            + Produto.class.getName()));
    }

    public Page < Produto > search(String nome, @NonNull List < Integer > ids, Integer page, 
            Integer linesPerPage, String orderBy, @NonNull String direction) {
        PageRequest pageRequest = PageRequest.of(
                page, linesPerPage, Direction.valueOf(direction), orderBy);
        List < Categoria > categorias = categoriaRepository.findAllById(ids);
        return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);       
    }
}
