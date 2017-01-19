package org.accounting.controller.impl;


import org.accounting.controller.CompanyManagementController;
import org.accounting.model.Client;
import org.accounting.model.Company;
import org.accounting.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value="/companies")
public class CompanyManagementControllerImpl implements CompanyManagementController {

    @Autowired
    CompanyService companyService;

    @Override
    @RequestMapping(method= RequestMethod.GET,headers="content-type=application/json")
    public ResponseEntity<List<Company>> getAllCompanies(){

        return new ResponseEntity<>(companyService.getAll(),HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{id}",method=RequestMethod.GET,headers="content-type=application/json")
    public ResponseEntity<Company> getCompanyById(@PathVariable long id){
        Company company=companyService.getById(id);
        if(company!=null) return new ResponseEntity<>(company,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public ResponseEntity deleteCompany(@PathVariable long id){
        companyService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{id}",method=RequestMethod.PUT,headers="content-type=application/json")
    public ResponseEntity editCompany(@RequestBody Company company, @PathVariable long id){
       company.setId(id);
        companyService.edit(company);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="",method=RequestMethod.POST,headers="content-type=application/json")
    public ResponseEntity createCompany(@RequestBody Company company){
        Company newCompany=companyService.create(company);
        HttpHeaders headers = new HttpHeaders();
        try{
            headers.set("Location", "http://localost:8080/accounting/companies/"+newCompany.getId());
            return new ResponseEntity(newCompany, headers, HttpStatus.CREATED);
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @RequestMapping(value="/{id}/clients",method=RequestMethod.GET,headers="content-type=application/json")
    public ResponseEntity<Set<Client>> getRegistratedClients(@PathVariable long id){

        return new ResponseEntity<>(companyService.getById(id).getClients(),HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{company_id}/{clientd_id}",method=RequestMethod.PUT)
    public ResponseEntity bindClientToCompany(@PathVariable("company_id") long companyId,@PathVariable("client_id") long clientId){
        if(companyService.bindClient(companyId,clientId)) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value="/{company_id}/{clientd_id}",method=RequestMethod.DELETE)
    public ResponseEntity unbindClientFromCompany(@PathVariable("company_id") long companyId,@PathVariable("client_id") long clientId){
        if(companyService.unbindClient(companyId, clientId)) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
