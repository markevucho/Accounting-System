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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestSpringContext.class})
@Transactional
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;
    @PersistenceContext
    private EntityManager entityManager;
    private static List<Company> companies;

    @BeforeClass
    public static void generateCompanyList(){
        companies=new ArrayList<>();
        companies.add(new Company("VINT","Fedorov",458648));
        companies.add(new Company("Argos","Ivanov",7894));

    }

    @After
    public void clearPersistenceContext(){
        entityManager.clear();
    }

    @Test
    public void testGetAllCompanies(){
        for(Company company: companies) entityManager.persist(company);

        List<Company> receivedCompanies=companyService.getAll();
        assertEquals(companies,receivedCompanies);
    }

    @Test
    public void testGetCompanyById(){
        Company company=new Company();
        entityManager.persist(company);

        Company receivedCompany=companyService.getById(company.getId());
        assertEquals(company,receivedCompany);
    }

    @Test
    public void testDeleteCompany(){
        Company company=new Company();
        entityManager.persist(company);
        assertTrue(entityManager.contains(company));

        companyService.delete(company.getId());
        assertFalse(entityManager.contains(company));
    }

    @Test
    public void testEditCompany(){
        Company company=new Company();
        String newName="NewName";
        String newFounder="NewFounder";
        long newIdentificationNumber=7897;
        entityManager.persist(company);

        entityManager.detach(company);
        company.setName(newName);
        company.setFounder(newFounder);
        company.setIdentificationNumber(newIdentificationNumber);

        companyService.edit(company);
        assertEquals(company,entityManager.find(Company.class,company.getId()));
    }

    @Test
    public void testCreateCompany(){
        Company createdCompany=companyService.create(new Company());

        assertTrue(entityManager.contains(createdCompany));
    }

    @Test
    public void testBindClientToCompany(){
        Company company=new Company();
        Client client=new Client();

        entityManager.persist(company);
        entityManager.persist(client);

        companyService.bindClient(company.getId(),client.getId());

        assertTrue(entityManager.find(Company.class,company.getId()).getClients().contains(client));
    }

    @Test
    public void testUnbindClientFromCompany(){
        Company company=new Company();
        Client client=new Client();
        company.getClients().add(client);

        entityManager.persist(company);

        companyService.unbindClient(company.getId(),client.getId());

        assertFalse(entityManager.find(Company.class,company.getId()).getClients().contains(client));
    }



}
