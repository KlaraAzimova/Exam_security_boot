package peaksoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.entity.Company;
import peaksoft.repository.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository repository;

    @Autowired
    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }


    public List<Company> getAllCompanies() {
        return repository.findAll();
    }

    public void save(Company company) {
        System.out.println(company.toString());
        repository.save(company);
    }


    public Company getById(Long id) {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Company with = " + id + " not found"));
    }


    public void deleteCompanyById(Long id) {
        repository.deleteById(id);
    }
}
