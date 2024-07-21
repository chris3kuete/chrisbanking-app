package net.javaguides.chrisbanking_app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.chrisbanking_app.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	Account getAccountById(Long id);

}
