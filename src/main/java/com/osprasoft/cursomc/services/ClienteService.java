package com.osprasoft.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osprasoft.cursomc.domain.Cidade;
import com.osprasoft.cursomc.domain.Cliente;
import com.osprasoft.cursomc.domain.Endereco;
import com.osprasoft.cursomc.domain.enums.TipoCliente;
import com.osprasoft.cursomc.dto.ClienteDTO;
import com.osprasoft.cursomc.dto.ClienteNewDTO;
import com.osprasoft.cursomc.repositories.ClienteRepository;
import com.osprasoft.cursomc.repositories.EnderecoRepository;
import com.osprasoft.cursomc.services.exception.DataIntegrityException;
import com.osprasoft.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(@NonNull Integer id) {
        Optional < Cliente > obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " 
            + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        if (id != null) {
            find(id);
            try {
                repo.deleteById(id);
            }
            catch (DataIntegrityViolationException e) {
                throw new DataIntegrityException(
                    "Não é possível excluir um cliente que possui pedidos.");
            }
        }
    }

    public List < Cliente > findAll() {
        return repo.findAll();
    }

    public Page < Cliente > findPage(Integer page, 
            Integer linesPerPage, String orderBy, @NonNull String direction) {
        PageRequest pageRequest = PageRequest.of(
                page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDTO) {
        Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfCnpj(), TipoCliente.toEnum(objDTO.getTipoPessoa()));
        Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDTO.getTelefone1());
        if (objDTO.getTelefone2() != null) {
            cli.getTelefones().add(objDTO.getTelefone2());
        }
        if (objDTO.getTelefone3() != null) {
            cli.getTelefones().add(objDTO.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
    
}
