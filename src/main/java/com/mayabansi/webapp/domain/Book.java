package com.mayabansi.webapp.domain;

// Generated Sep 19, 2010 12:31:53 AM by Hibernate Tools 3.3.0.GA

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Book generated by hbm2java
 */
@Entity
@Table(name = "book", catalog = "MockitoDemo1")
public class Book implements java.io.Serializable {

    private Long id;
    private long version;
    private String title;
    private double price;
    private int yearPublished;

    public Book() {
    }

    public Book(String title, double price, int yearPublished) {
        this.title = title;
        this.price = price;
        this.yearPublished = yearPublished;
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

    public Book setVersion(long version) {
        this.version = version;
        return this;
    }

    @Column(name = "title", nullable = false, length = 20)
    public String getTitle() {
        return this.title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(name = "price", nullable = false, precision = 22, scale = 0)
    public double getPrice() {
        return this.price;
    }

    public Book setPrice(double price) {
        this.price = price;
        return this;
    }

    @Column(name = "year_published", nullable = false)
    public int getYearPublished() {
        return this.yearPublished;
    }

    public Book setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
        return this;
    }
}
