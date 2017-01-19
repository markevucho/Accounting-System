package org.accounting.service;


import org.accounting.model.Client;
import org.accounting.model.Company;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringContext.class})
@Transactional
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    @PersistenceContext
    private EntityManager entityManager;
    private static List<Client> clients;

    @BeforeClass
    public static void generateClientList(){
        clients=new ArrayList<>();
        clients.add(new Client("Vasia","Ivanov","Fedorovich",32));
        clients.add(new Client("Kostya","Tszyu","Borisovych",46));
        clients.add(new Client("Nikolai","Petrov","Stepanovych",23));
    }

    @After
    public void clearPersistenceContext(){
        entityManager.clear();
    }

    @Test
    public void testGetAllClients(){
        for(Client client: clients) entityManager.persist(client);

        List<Client> receivedClients=clientService.getAll();
        assertEquals(clients,receivedClients);
    }

    @Test
    public void testGetClientById(){
        Client client=new Client();
        entityManager.persist(client);

        Client receivedClient=clientService.getById(client.getId());

        assertEquals(client,receivedClient);
    }

    @Test
    public void testDeleteClient(){
        Client client=new Client();
        entityManager.persist(client);

        assertTrue(entityManager.contains(client));

        clientService.delete(client.getId());
        assertFalse(entityManager.contains(client));
    }

    @Test
    public void testEditClient(){
        Client client=new Client();
        String newName="NewName";
        String newSurname="NewSurname";
        entityManager.persist(client);

        entityManager.detach(client);
        client.setName(newName);
        client.setSurname(newSurname);

        clientService.edit(client);
        assertEquals(client,entityManager.find(Client.class,client.getId()));
    }

    @Test
    public void testCreateClient(){
        Client createdClient=clientService.create(new Client());

        assertTrue(entityManager.contains(createdClient));
    }

    @Test
    public void testBindCompanyToClient(){
        Client client=new Client();
        Company company=new Company();

        entityManager.persist(client);
        entityManager.persist(company);

        clientService.bindCompany(client.getId(),company.getId());

        assertTrue(entityManager.find(Client.class,client.getId()).getCompanies().contains(company));
    }

    @Test
    public void testUnbindCompanyFromClient(){
        Client client=new Client();
        Company company=new Company();
        client.getCompanies().add(company);

        entityManager.persist(client);

        clientService.unbindCompany(client.getId(),company.getId());

        assertFalse(entityManager.find(Client.class,client.getId()).getCompanies().contains(company));
    }

}
