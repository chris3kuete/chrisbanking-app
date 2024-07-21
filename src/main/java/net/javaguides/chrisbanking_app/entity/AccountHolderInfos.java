package net.javaguides.chrisbanking_app.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customers_Info")
public class AccountHolderInfos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long Id;
	
	@Column(nullable=false, unique=false, length=25)
	private String FirstName;
	
	@Column(nullable=false, unique=false, length=25)
	private String LastName;
	
	@Column(nullable=false, unique=true, length=25)
	private String phonenumber;
	
	@Column(nullable=false, unique=true, length=40)
	private String email;
	
	@Column(nullable=false, unique=false, length=20)
	private String DateOfBirth;
	
	@Column(nullable=false, unique=false, length=40)
	private String Address;
	
	@Column(nullable=false, unique=false, length=25)
	private Long zipCode;
	
	@Column(nullable=false, unique=false, length=25)
	private String City;
	
	@Column(nullable=false, unique=false, length=25)
	private String Gender;
	
	@Column(nullable=false, unique=true, length=10)
	private String Pin;
	
	@Column(nullable=false, unique=true, length=10)
	private String confirmPin;

}
