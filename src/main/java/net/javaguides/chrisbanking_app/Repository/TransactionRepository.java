package net.javaguides.chrisbanking_app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.chrisbanking_app.entity.Transaction;



public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);

}
