package com.estudantepedromateus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudantepedromateus.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
