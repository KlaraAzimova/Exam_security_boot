package peaksoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Company;
import peaksoft.service.CompanyService;


@Controller
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    private String getAllCompanies(Model model) {
        model.addAttribute("allCompanies", companyService.getAllCompanies());
        return "company/mainCompany";
    }

    @GetMapping("/new")
    private String newCompany(Model model) {
        model.addAttribute("newCompany", new Company());
        return "company/newCompany";
    }

    @PostMapping("/save")
    private String saveCompany(@ModelAttribute("newCompany") Company company) {
        companyService.save(company);
        return "redirect:/api/companies";
    }

    @GetMapping("/update/{id}")
    private String updateCompany(@PathVariable("id") Long id, Model model) {
        model.addAttribute("company", companyService.getById(id));
        return "company/updateCompany";
    }

    @PostMapping("/update")
    private String saveUpdateCompany(@ModelAttribute("company") Company company) {
        companyService.save(company);
        return "redirect:/api/companies";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompanyById(id);
        return "redirect:/api/companies";
    }
}