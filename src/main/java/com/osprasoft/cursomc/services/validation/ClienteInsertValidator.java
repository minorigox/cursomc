package com.osprasoft.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.osprasoft.cursomc.domain.Cliente;
import com.osprasoft.cursomc.domain.enums.TipoCliente;
import com.osprasoft.cursomc.dto.ClienteNewDTO;
import com.osprasoft.cursomc.repositories.ClienteRepository;
import com.osprasoft.cursomc.resources.exception.FieldMessage;
import com.osprasoft.cursomc.services.validation.utils.BR;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator < ClienteInsert, ClienteNewDTO > {
    
    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List < FieldMessage > list = new ArrayList<>();
        if (objDto.getTipoPessoa().equals(TipoCliente.PESSOAFISICA.getCod())
            && !BR.isValidCpf(objDto.getCpfCnpj()))  {
                list.add(new FieldMessage("cpfCnpj", "CPF inválido."));
        }

        if (objDto.getTipoPessoa().equals(TipoCliente.PESSOAJURIDICA.getCod())
            && !BR.isValidCnpj(objDto.getCpfCnpj()))  {
                list.add(new FieldMessage("cpfCnpj", "CNPJ inválido."));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if (aux != null) {
            list.add(new FieldMessage("email", "Email já existente."));
        }

       for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
    return list.isEmpty();
    }
}