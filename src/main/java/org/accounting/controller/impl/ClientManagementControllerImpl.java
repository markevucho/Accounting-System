package org.accounting.controller.impl;


import org.accounting.controller.ClientManagementController;
import org.accounting.model.Company;
import org.accounting.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.accounting.model.Client;
import org.accounting.service.ClientService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/clients")
public class ClientManagementControllerImpl implements ClientManagementController {

    @Autowired
    ClientService clientService;

    @Override
    @RequestMapping(method=RequestMethod.GET,headers = "content-type=application/json")
    public ResponseEntity<List<Client>> getAllClients(){

        return new ResponseEntity<>(clientService.getAll(),HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{id}",method=RequestMethod.GET,headers = "content-type=application/json")
    public ResponseEntity<Client> getClientById(@PathVariable long id){
        Client client=clientService.getById(id);
        if(client!=null) return new ResponseEntity<>(client,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public ResponseEntity deleteClient(@PathVariable long id){
        clientService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{id}",method=RequestMethod.PUT,headers = "content-type=application/json")
    public ResponseEntity editClient(@RequestBody Client client,@PathVariable long id){
            client.setId(id);
            clientService.edit(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="",method=RequestMethod.POST,headers = "content-type=application/json")
    public ResponseEntity createClient(@RequestBody Client client){
        Client newClient=clientService.create(client);
        HttpHeaders headers = new HttpHeaders();
        try{
            headers.set("Location", "http://localost:8080/accounting/clients/" + newClient.getId());
            return new ResponseEntity(newClient, headers, HttpStatus.CREATED);
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @RequestMapping(value="/{id}/companies",method=RequestMethod.GET,headers="content-type=application/json")
    public ResponseEntity<Set<Company>> getServingCompanies(@PathVariable long id){

        return new ResponseEntity<>(clientService.getById(id).getCompanies(),HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{client_id}/{company_id}",method=RequestMethod.PUT)
    public ResponseEntity bindCompanyToClient(@PathVariable("client_id") long clientId,@PathVariable("company_id") long companyId){
        if(clientService.bindCompany(clientId,companyId)) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value="/{client_id}/{company_id}",method=RequestMethod.DELETE)
    public ResponseEntity unbindCompanyFromClient(@PathVariable("client_id") long clientId,@PathVariable("company_id") long companyId){
        if(clientService.unbindCompany(clientId,companyId)) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }



}
