package org.accounting.controller;


import org.accounting.model.Client;
import org.accounting.model.Company;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface CompanyManagementController {

    ResponseEntity getAllCompanies();
    ResponseEntity getCompanyById(long id);
    ResponseEntity deleteCompany(long id);
    ResponseEntity editCompany(Company company,long id);
    ResponseEntity createCompany(Company company);
    ResponseEntity getRegistratedClients(long id);
    ResponseEntity bindClientToCompany(long companyId, long clientId);
    ResponseEntity unbindClientFromCompany(long companyId, long clientId);
}
