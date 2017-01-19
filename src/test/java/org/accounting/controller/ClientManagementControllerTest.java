package org.accounting.controller.impl;


import org.accounting.controller.ClientManagementController;
import org.accounting.model.Client;
import org.accounting.model.Company;
import org.accounting.service.ClientService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientManagementControllerTest {

    @Mock
    private ClientService clientService;
    @InjectMocks
    private ClientManagementController clientController=new ClientManagementControllerImpl();

    private static List<Client> clients;
    private static Client client;

    @BeforeClass
    public static void generateTestData(){
        clients=new ArrayList<>();
        clients.add(new Client("Ivan","Petrov","Vasylovych",45));
        clients.add(new Client("Igor","Ivanov","Fedorovych",16));

        client=new Client("Vasia","Pupkin","Fedorovych",23);
    }


    @Test
    public void testRequestAllClients(){
        when(clientService.getAll()).thenReturn(clients);
        ResponseEntity<List<Client>> response=clientController.getAllClients();

        assertEquals(clients,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());

        verify(clientService,times(1)).getAll();
    }

    @Test
    public void testRequestClientById_ExistsClient(){
        when(clientService.getById(anyLong())).thenReturn(client);

        ResponseEntity<Client> response=clientController.getClientById(anyLong());

        assertEquals(client,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());

        verify(clientService,times(1)).getById(anyLong());
    }

    @Test
    public void testRequestClientById_NoClient(){
        when(clientService.getById(anyLong())).thenReturn(null);

        ResponseEntity<Client> response=clientController.getClientById(anyLong());

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

        verify(clientService,times(1)).getById(anyLong());
    }

    @Test
    public void testRequestDeleteClient(){
        doNothing().when(clientService).delete(anyLong());

        ResponseEntity<Client> response=clientController.deleteClient(anyLong());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(clientService,times(1)).delete(anyLong());
    }

    @Test
    public void testRequestEditClient(){
        doNothing().when(clientService).edit(client);

        ResponseEntity<Client> response=clientController.editClient(client,anyLong());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(clientService,times(1)).edit(client);
    }

    @Test
    public void testRequestCreateClient_Successfully(){
        client.setId(1L);
        when(clientService.create(any(Client.class))).thenReturn(client);

        ResponseEntity<Client> response=clientController.createClient(any(Client.class));

        assertEquals(client,response.getBody());
        assertEquals("http://localost:8080/accounting/clients/"+client.getId(),response.getHeaders().getLocation().toString());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());

        verify(clientService,times(1)).create(any(Client.class));
    }

    @Test
    public void testRequestCreateClient_Unsuccessfully(){
        when(clientService.create(any(Client.class))).thenReturn(null);

        ResponseEntity<Client> response=clientController.createClient(any(Client.class));

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        verify(clientService,times(1)).create(any(Client.class));
    }

    @Test
    public void testRequestGetServingCompanies(){
        Set<Company> companies=new LinkedHashSet();
        companies.add(new Company("Agro","Velychko",894));
        client.setCompanies(companies);

        when(clientService.getById(anyLong())).thenReturn(client);

        ResponseEntity<Set<Company>> response=clientController.getServingCompanies(anyLong());

        assertEquals(companies,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());

        verify(clientService,times(1)).getById(anyLong());
    }

    @Test
    public void testRequestBindCompanyToClient_Successfully(){
        when(clientService.bindCompany(1L,2L)).thenReturn(true);

        ResponseEntity response=clientController.bindCompanyToClient(1L,2L);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(clientService,times(1)).bindCompany(1L,2L);
    }

    @Test
    public void testRequestBindCompanyToClient_Unsuccessfully(){
        when(clientService.bindCompany(1L,2L)).thenReturn(false);

        ResponseEntity response=clientController.bindCompanyToClient(1L,2L);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        verify(clientService,times(1)).bindCompany(1L,2L);
    }

    @Test
    public void testRequestUnbindCompanyFromClient_Successfully(){
        when(clientService.unbindCompany(1L,2L)).thenReturn(true);

        ResponseEntity response=clientController.unbindCompanyFromClient(1L,2L);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(clientService,times(1)).unbindCompany(1L,2L);
    }

    @Test
    public void testRequestUnbindCompanyFromClient_Unsuccessfully(){
        when(clientService.unbindCompany(1L,2L)).thenReturn(false);

        ResponseEntity response=clientController.unbindCompanyFromClient(1L,2L);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        verify(clientService,times(1)).unbindCompany(1L,2L);
    }


}
