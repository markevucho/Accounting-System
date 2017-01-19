package org.accounting.service;


import org.accounting.model.Client;
import org.accounting.model.Company;

import java.util.List;

public interface ClientService {

    List<Client> getAll();
    Client getById(long id);
    void delete(long id);
    void edit(Client client);
    Client create(Client client);
    boolean bindCompany(long clientId,long companyId);
    boolean unbindCompany(long clientId,long companyId);

}
