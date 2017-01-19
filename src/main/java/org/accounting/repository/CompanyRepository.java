package org.accounting.repository;

import org.accounting.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company,Long>{
}
