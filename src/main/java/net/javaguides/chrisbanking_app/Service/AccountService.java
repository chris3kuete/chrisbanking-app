package net.javaguides.chrisbanking_app.Service;

import java.util.List;

import net.javaguides.chrisbanking_app.Dto.AccountDto;
import net.javaguides.chrisbanking_app.Dto.TransactionDto;
import net.javaguides.chrisbanking_app.Dto.TransferFundDto;


public interface AccountService {
	
	AccountDto createAccount(AccountDto accountDto);
	
	AccountDto getAccountById(Long id);
	
	AccountDto depositAmount(Long id, double amount);
	
	AccountDto withdrawAmount(Long id, double amount);
	
	List<AccountDto> showAllAccounts();
	
	void deleteAccount(Long id);
	
	void transferFunds(TransferFundDto transferFundDto);
	
	List<TransactionDto> getAccountTransactions(Long accountId);
	
	 

}
