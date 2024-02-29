package com.osprasoft.cursomc.domain.enums;

public enum Perfil {

    ADMIN(1, "ADMIN"),
    CLIENTE(2, "CLIENTE");

    private int cod;
    private String descricao;

    private Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Perfil ep : Perfil.values()) {
            if (cod.equals(ep.getCod())) {
                return ep;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }

    public int getCod() {
        return cod;
    }
    public String getDescricao() {
        return descricao;
    }
}
