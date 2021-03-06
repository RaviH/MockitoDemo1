package com.mayabansi.webapp.domain;

// Generated Sep 19, 2010 12:31:53 AM by Hibernate Tools 3.3.0.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * OrderPayment generated by hbm2java
 */
@Entity
@Table(name = "order_payment", catalog = "MockitoDemo1")
public class OrderPayment implements java.io.Serializable {

	private Long id;
	private long version;
	private double amount;
	private Date paymentDate;
	private long accountNumber;

	public OrderPayment() {
	}

	public OrderPayment(double amount, Date paymentDate, long accountNumber) {
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.accountNumber = accountNumber;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Version
	@Column(name = "version", nullable = false)
	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Column(name = "amount", nullable = false, precision = 22, scale = 0)
	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "payment_date", nullable = false, length = 19)
	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Column(name = "account_number", nullable = false)
	public long getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

}
