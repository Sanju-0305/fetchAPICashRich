package com.coinmarketcap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coinmarketcap.entity.JsonResponse;

@Repository
public interface JsonResponseRepository extends JpaRepository<JsonResponse, Long>{

}
