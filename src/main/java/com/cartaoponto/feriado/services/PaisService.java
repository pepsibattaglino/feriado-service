package com.cartaoponto.feriado.services;

import com.cartaoponto.feriado.dto.PaisDTO;

import com.cartaoponto.feriado.entities.PaisEntity;
import com.cartaoponto.feriado.exceptions.InvalidInputDataException;
import com.cartaoponto.feriado.exceptions.NotFoundException;
import com.cartaoponto.feriado.repositories.PaisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {

    @Autowired
    private PaisRepository repository;

    public PaisService(PaisRepository repository) {
        this.repository = repository;
    }

    public PaisDTO salvarPais(PaisDTO paisDTO) {
        if ((paisDTO.getId() != null) && (consultarPais(paisDTO) == null)) {
            throw new NotFoundException("Não existe um país salvo com o código: " + paisDTO.getId());
        }
        ObjectMapper om = new ObjectMapper();
        PaisEntity paisEntity = om.convertValue(paisDTO, PaisEntity.class);
        PaisEntity result =  this.repository.save(paisEntity);
        paisDTO.setId(result.getId());
        return paisDTO;
    }

    public void removerPais(PaisDTO paisDTO) {
        if (paisDTO.getId() == null){
            throw new InvalidInputDataException("Não é possível remover um país com ID nulo.");
        }
        ObjectMapper om = new ObjectMapper();
        PaisEntity paisEntity = om.convertValue(paisDTO, PaisEntity.class);
        this.repository.delete(paisEntity);
    }

    public PaisDTO consultarPais(PaisDTO paisDTO) {
        if (paisDTO.getId() == null){
            throw new NotFoundException("Não existe um país salvo com o código: " + paisDTO.getId());
        }
        ObjectMapper om = new ObjectMapper();
        PaisEntity paisEntity = om.convertValue(paisDTO, PaisEntity.class);
        PaisEntity result = this.repository.findOne(paisEntity.getId());
        paisDTO.setId(result.getId());
        return paisDTO;
    }
}
