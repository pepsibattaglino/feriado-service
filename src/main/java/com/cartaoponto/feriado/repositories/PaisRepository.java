package com.cartaoponto.feriado.repositories;

import com.cartaoponto.feriado.entities.PaisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends CrudRepository<PaisEntity, Long> {


}
