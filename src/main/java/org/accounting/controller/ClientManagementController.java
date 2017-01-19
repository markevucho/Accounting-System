package org.accounting.controller;


import org.accounting.model.Client;
import org.accounting.model.Company;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Set;

public interface ClientManagementController {

    ResponseEntity getAllClients();
    ResponseEntity getClientById(long id);
    ResponseEntity deleteClient(long id);
    ResponseEntity editClient(Client client,long id);
    ResponseEntity createClient(Client client);
    ResponseEntity getServingCompanies(long id);
    ResponseEntity bindCompanyToClient(long clientId, long companyId);
    ResponseEntity unbindCompanyFromClient(long clientId, long companyId);

}
