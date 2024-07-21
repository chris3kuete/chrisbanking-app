package net.javaguides.chrisbanking_app.Service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



import org.springframework.stereotype.Service;

import net.javaguides.chrisbanking_app.Dto.AccountDto;
import net.javaguides.chrisbanking_app.Dto.TransactionDto;
import net.javaguides.chrisbanking_app.Repository.AccountRepository;
import net.javaguides.chrisbanking_app.Repository.TransactionRepository;
import net.javaguides.chrisbanking_app.Service.AccountService;

import net.javaguides.chrisbanking_app.entity.Account;
import net.javaguides.chrisbanking_app.entity.Transaction;
import net.javaguides.chrisbanking_app.exception.AccountException;
import net.javaguides.chrisbanking_app.mapper.AccountMapper;

@Service
public class AccountServiceImpl implements AccountService{
	
	private AccountRepository accountRepository;
	
	//Inject transaction repository
	private TransactionRepository transactionRepository;
	
	private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
	private static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";
	private static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";
	
	
    //Inject account and transaction repository dependency using constructor based dependency injection
	public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		super();
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}



	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}



	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
		
		return AccountMapper.mapToAccountDto(account);
	}



	@Override
	public AccountDto depositAmount(Long id, double amount) {
		Account account = accountRepository.getAccountById(id);
		double newAmt = account.getBalance() + amount;
		account.setBalance(newAmt);
		Account savedAccount = accountRepository.save(account);
		
		Transaction transaction = new Transaction();
		transaction.setAccountId(id);
		transaction.setAmount(amount);
		transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
		transaction.setTimestamp(LocalDateTime.now());
		
		transactionRepository.save(transaction);
		
		
		return AccountMapper.mapToAccountDto(savedAccount);
	}



	@Override
	public AccountDto withdrawAmount(Long id, double amount) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
		
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficient amount");
		}
		
		double newAmount = account.getBalance() - amount;
		account.setBalance(newAmount);
		Account savedAccount = accountRepository.save(account);
		
		Transaction transaction = new Transaction();
		transaction.setAccountId(id);
		transaction.setAmount(amount);
		transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
		transaction.setTimestamp(LocalDateTime.now());
		
		transactionRepository.save(transaction);
		
		return AccountMapper.mapToAccountDto(savedAccount);
	}



	@Override
	public List<AccountDto> showAllAccounts() {
		
		List<Account> accounts = accountRepository.findAll();
		//map account entity object to accountDto and return a list from the stream
		return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
		
		
	}



	@Override
	public void deleteAccount(Long id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
		accountRepository.delete(account);
	}



	@Override
	public void transferFunds(net.javaguides.chrisbanking_app.Dto.TransferFundDto transferFundDto) {
		//Retrieve the account from which to send the amount
		Account fromAccount = accountRepository.findById(transferFundDto.fromAccountId()).orElseThrow(() -> new AccountException("Account does not exists"));
		
		//Retrieve the account to which to send the amount
		Account toAccount = accountRepository.findById(transferFundDto.toAccountId()).orElseThrow(() -> new AccountException("Account does not exist"));
		
		if(fromAccount.getBalance() < transferFundDto.amount()) {
			throw new RuntimeException("Insuficient Amount");
		}
		
		//Debit the amount from account object
		fromAccount.setBalance(fromAccount.getBalance() - transferFundDto.amount());
		
		//Credit the amount to toAccount object
		toAccount.setBalance(toAccount.getBalance() + transferFundDto.amount());
		
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		
		Transaction transaction = new Transaction();
		transaction.setAccountId(transferFundDto.fromAccountId());
		transaction.setAmount(transferFundDto.amount());
		transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
		transaction.setTimestamp(LocalDateTime.now());
		
		transactionRepository.save(transaction);
		
		
	}



	@Override
	public List<TransactionDto> getAccountTransactions(Long accountId) {
		List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
		
		return transactions.stream()
				.map((transaction) -> convertEntityToDto(transaction))
				.collect(Collectors.toList());
	}
	
	private TransactionDto convertEntityToDto(Transaction transaction) {
		
		return new TransactionDto(
				transaction.getId(),
				transaction.getAccountId(),
				transaction.getAmount(),
				transaction.getTransactionType(),
				transaction.getTimestamp()
				);
	
	}
	

}
