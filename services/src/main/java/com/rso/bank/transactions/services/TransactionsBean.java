package com.rso.bank.transactions.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.rso.bank.transactions.Transaction;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class TransactionsBean {

    @Inject
    private EntityManager em;

    public List<Transaction> getTransactions(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Transaction.class, queryParameters);

    }

    public Transaction getTransaction(String transactionId) {

        Transaction transaction = em.find(Transaction.class, transactionId);

        if (transaction == null) {
            throw new NotFoundException();
        }

        return transaction;
    }

    public Transaction createTransaction(Transaction transaction) {

        try {
            beginTx();
            em.persist(transaction);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return transaction;
    }

    public Transaction putTransaction(String transactionId, Transaction transaction) {

        Transaction c = em.find(Transaction.class, transactionId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            transaction.setId(c.getId());
            transaction = em.merge(transaction);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return transaction;
    }

    public boolean deleteTransaction(String transactionId) {

        Transaction transaction = em.find(Transaction.class, transactionId);

        if (transaction != null) {
            try {
                beginTx();
                em.remove(transaction);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
