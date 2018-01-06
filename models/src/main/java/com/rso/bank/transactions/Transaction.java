package com.rso.bank.transactions;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "transaction")
@NamedQueries(value =
        {
                @NamedQuery(name = "Transaction.getAll", query = "SELECT t FROM transaction t"),
                @NamedQuery(name = "Transaction.findByTransaction", query = "SELECT t FROM transaction t WHERE t.accountId = " +
                        ":accountId")
        })
@UuidGenerator(name = "idGenerator")
public class Transaction {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String title;

    private String description;

    private double amount;

    private int type;

    private String status;

    private Date submitted;

    @Column(name = "account_id")
    private String accountId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
