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

    @GetMapping("/find/{companyId}")
    private String getCompanyById(@PathVariable Long companyId, Model model) {
        model.addAttribute("company", companyService.getById(companyId));
        return "company/mainCompany";

    }

    @GetMapping("/update/{id}")
    private String updateCompany(@PathVariable("id") Long id, Model model) {
        model.addAttribute("company", companyService.getById(id));
        return "company/updateCompany";
    }


    @PostMapping("/update/{id}")
    private String saveUpdateCompany(@PathVariable("id") Long id,
                                     @ModelAttribute("company") Company company) {
        companyService.updateCompany(id, company);
        return "redirect:/api/companies";
    }


    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompanyById(id);
        return "redirect:/api/companies";
    }
}