package com.example.springboot.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.AvaliacaoModel;


@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, UUID> {
    
}
