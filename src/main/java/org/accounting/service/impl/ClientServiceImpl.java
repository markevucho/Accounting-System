package org.accounting.service.impl;

import org.accounting.model.Company;
import org.accounting.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.accounting.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.accounting.model.Client;
import org.accounting.service.ClientService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Client> getAll(){

        return clientRepository.findAll();
    }

    @Override
    public Client getById(long id){

        return clientRepository.findOne(id);
    }

    @Override
    public void delete(long id){
        clientRepository.delete(id);
    }

    @Override
    public void edit(Client client){
        clientRepository.save(client);
    }

    @Override
    public Client create(Client client){
        Client returnClient=null;
        try {
            returnClient=clientRepository.save(client);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
            return returnClient;
    }

    @Override
    public boolean bindCompany(long clientId,long companyId){
        if(clientRepository.exists(clientId)&&companyRepository.exists(companyId)){
                clientRepository.findOne(clientId).getCompanies().add(companyRepository.findOne(companyId));
            return true;
        }
        else return false;
    }

    @Override
    public boolean unbindCompany(long clientId,long companyId){
        if(clientRepository.exists(clientId)&&companyRepository.exists(companyId)){
            clientRepository.findOne(clientId).getCompanies().remove(companyRepository.findOne(companyId));
            return true;
        }
        else return false;
    }
}

