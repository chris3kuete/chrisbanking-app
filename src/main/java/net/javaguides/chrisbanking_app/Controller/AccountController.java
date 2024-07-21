
package net.javaguides.chrisbanking_app.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.chrisbanking_app.Dto.AccountDto;
import net.javaguides.chrisbanking_app.Dto.TransactionDto;
import net.javaguides.chrisbanking_app.Dto.TransferFundDto;
import net.javaguides.chrisbanking_app.Service.AccountService;
import net.javaguides.chrisbanking_app.entity.AccountHolderInfos;

@Controller
//@RequestMapping("/api/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	//Spring use this to automatically inject the dependency
	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	//Add Account REST API
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
		return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
	}
	
	//Get account REST API
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
		AccountDto accountDto = accountService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
		
	}
	
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request){
		double amount = request.get("amount");
		AccountDto accountDto = accountService.depositAmount(id, amount);
		return ResponseEntity.ok(accountDto);
	}
	
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request){
		double amount = request.get("amount");
		AccountDto accountDto = accountService.withdrawAmount(id, amount);
		return ResponseEntity.ok(accountDto);
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		List<AccountDto> accounts = accountService.showAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAnAccount(@PathVariable Long id){
		accountService.deleteAccount(id);
		return ResponseEntity.ok("Account is deleted successfully");
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<String> transferFund(@RequestBody TransferFundDto transferFundDto){
		accountService.transferFunds(transferFundDto);
		return ResponseEntity.ok("Transfer Successful");
	}
	
	//Build transactions REST API
	@GetMapping("/{id}/transactions")
	public ResponseEntity<List<TransactionDto>> fetchAccountTransactions(@PathVariable("id") Long accountId){
		List<TransactionDto> transactions = accountService.getAccountTransactions(accountId);
		return ResponseEntity.ok(transactions);
	}	

}
