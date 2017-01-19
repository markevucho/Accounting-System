package org.accounting.service.impl;

import org.accounting.model.Client;
import org.accounting.model.Company;
import org.accounting.repository.ClientRepository;
import org.accounting.repository.CompanyRepository;
import org.accounting.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Company> getAll(){

        return companyRepository.findAll();
    }

    @Override
    public Company getById(long id){

        return companyRepository.findOne(id);
    }

    @Override
    public void delete(long id){

        companyRepository.delete(id);
    }

    @Override
    public void edit(Company company){

        companyRepository.save(company);
    }

    @Override
    public Company create(Company company){
        Company returnCompany=null;
        try {
            returnCompany=companyRepository.save(company);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
            return returnCompany;
    }

    @Override
    public boolean bindClient(long companyId,long clientId){
        if(companyRepository.exists(companyId)&&clientRepository.exists(clientId)){
            companyRepository.findOne(companyId).getClients().add(clientRepository.findOne(clientId));
            return true;
        }
        else return false;
    }

    @Override
    public boolean unbindClient(long companyId,long clientId){
        if(companyRepository.exists(companyId)&&clientRepository.exists(clientId)){
            companyRepository.findOne(companyId).getClients().remove(clientRepository.findOne(clientId));
            return true;
        }
        else return false;
    }
}
