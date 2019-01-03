package com.cartaoponto.feriado.services;

import com.cartaoponto.feriado.entities.Pais;
import com.cartaoponto.feriado.exceptions.NotFoundException;
import com.cartaoponto.feriado.repositories.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {

    @Autowired
    private PaisRepository repository;

    public void salvarPais(Pais pais) {
        if ((pais.getId() != null) && (consultarPais(pais) == null)) {
            throw new NotFoundException("Não existe um país salvo com o código: " + pais.getId());
        }
        this.repository.save(pais);
    }

    public void removerPais(Pais pais) {
        this.repository.delete(pais);
    }

    public void consultarPaises() {
        this.repository.findAll();
    }

    public Pais consultarPais(Pais pais) {
        return this.repository.findOne(pais.getId());
    }

}
