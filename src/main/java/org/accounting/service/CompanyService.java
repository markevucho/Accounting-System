package org.accounting.service;


import org.accounting.model.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getAll();
    Company getById(long id);
    void delete(long id);
    void edit(Company company);
    Company create(Company company);
    boolean bindClient(long companyId,long clientId);
    boolean unbindClient(long companyId,long clientId);
}
