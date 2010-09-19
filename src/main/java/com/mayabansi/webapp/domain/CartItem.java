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
 * CartItem generated by hbm2java
 */
@Entity
@Table(name = "cart_item", catalog = "MockitoDemo1")
public class CartItem implements java.io.Serializable {

	private Long id;
	private long version;
	private double estimatedTax;
	private double price;
	private Date dateAdded;
	private double qty;
	private String productType;

	public CartItem() {
	}

	public CartItem(double estimatedTax, double price, Date dateAdded,
			double qty, String productType) {
		this.estimatedTax = estimatedTax;
		this.price = price;
		this.dateAdded = dateAdded;
		this.qty = qty;
		this.productType = productType;
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

	@Column(name = "estimated_tax", nullable = false, precision = 22, scale = 0)
	public double getEstimatedTax() {
		return this.estimatedTax;
	}

	public void setEstimatedTax(double estimatedTax) {
		this.estimatedTax = estimatedTax;
	}

	@Column(name = "price", nullable = false, precision = 22, scale = 0)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_added", nullable = false, length = 19)
	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Column(name = "qty", nullable = false, precision = 22, scale = 0)
	public double getQty() {
		return this.qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	@Column(name = "product_type", nullable = false)
	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}
