package com.rso.bank.transactions.api.v1.resources;

import com.rso.bank.transactions.Transaction;
import com.rso.bank.transactions.api.v1.configuration.RestProperties;
import com.rso.bank.transactions.services.TransactionsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private RestProperties restProperties;


    @Inject
    private TransactionsBean transactionsBean;

    @GET
    public Response getTransactions() {

        List<Transaction> transactions = transactionsBean.getTransactions(uriInfo);

        return Response.ok(transactions).build();
    }

    @GET
    @Path("/{transactionId}")
    public Response getTransaction(@PathParam("transactionId") String transactionId) {

        Transaction transaction = transactionsBean.getTransaction(transactionId);

        if (transaction == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(transaction).build();
    }

    @POST
    public Response createTransaction(Transaction transaction) {

        if (transaction.getTitle() == null || transaction.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            transaction = transactionsBean.createTransaction(transaction);
        }

        if (transaction.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(transaction).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(transaction).build();
        }
    }

    @PUT
    @Path("{transactionId}")
    public Response putTransaction(@PathParam("transactionId") String transactionId, Transaction transaction) {

        transaction = transactionsBean.putTransaction(transactionId, transaction);

        if (transaction == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (transaction.getId() != null)
                return Response.status(Response.Status.OK).entity(transaction).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{transactionId}")
    public Response deleteCustomer(@PathParam("transactionId") String transactionId) {

        boolean deleted = transactionsBean.deleteTransaction(transactionId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("healthy")
    public Response setHealth(Boolean healthy) {
        restProperties.setHealthy(healthy);
        //log.info("Setting health to " + healthy);
        return Response.ok().build();
    }
}
