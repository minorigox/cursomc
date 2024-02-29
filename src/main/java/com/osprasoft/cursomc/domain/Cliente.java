package com.osprasoft.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.osprasoft.cursomc.domain.enums.TipoCliente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    @Column(unique = true)
    private String email;
    private String cpfCnpj;
    private Integer tipoPessoa;

    @JsonIgnore
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List < Endereco > enderecos = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "telefone")
    private Set < String > telefones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis")
    private Set < Integer > perfis = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List < Pedido > pedidos = new ArrayList<>();
    
    public Cliente(Integer id, String nome, String email, String cpfCnpj, TipoCliente tipoPessoa, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfCnpj = cpfCnpj;
        this.senha = senha;
        this.tipoPessoa = (tipoPessoa == null) ? null : tipoPessoa.getCod();
    }

    public Cliente() {
        //addPerfil(Perfil.CLIENTE);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    public List<Endereco> getEnderecos() {
        return enderecos;
    }
    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    public Set<String> getTelefones() {
        return telefones;
    }
    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCpfCnpj() {
        return cpfCnpj;
    }
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
    public TipoCliente getTipoPessoa() {
        return TipoCliente.toEnum(tipoPessoa);
    }
    public void setTipoPessoa(TipoCliente tipoPessoa) {
        this.tipoPessoa = tipoPessoa.getCod();
    }
    // public Set < Perfil > getPerfis() {
    //     return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    // }
    // public void addPerfil(Perfil perfil) {
    //     perfis.add(perfil.getCod());
    // }
}
