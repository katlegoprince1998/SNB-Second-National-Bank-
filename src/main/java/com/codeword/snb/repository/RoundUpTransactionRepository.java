package com.codeword.snb.repository;

import com.codeword.snb.dto.RoundUpTransactionDto;
import com.codeword.snb.entity.RoundUpTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundUpTransactionRepository extends JpaRepository<RoundUpTransaction, Integer> {
}
