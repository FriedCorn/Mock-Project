package com.mockproject.quizweb.controller;

import java.security.Principal;
import java.util.List;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.form.AccountForm;
import com.mockproject.quizweb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // For Admin feature
    @GetMapping("/account/list")
    @ResponseBody
    public List<Account> accountList(ModelAndView mv) {

        return accountService.findAll();
    }

    @GetMapping(value = { "/account-info", "/student-profile", "/instructor-profile" })
    @ResponseBody
    public ModelAndView accountDetail(ModelAndView mv, Principal principal) {
        Account account = accountService.findAccountByUsername(principal.getName());
        AccountForm accountForm = new AccountForm();

        accountForm.setDoB(account.getDoB());
        accountForm.setEmail(account.getEmail());
        accountForm.setFirstName(account.getFirstName());
        accountForm.setLastName(account.getLastName());
        accountForm.setUsername(account.getUsername());

        mv.setViewName("profile");
        mv.addObject("accountForm", accountForm);

        return mv;
    }

    @PostMapping(value = {"/account-update"})
    // @ResponseBody
    public ModelAndView accountUpdate(ModelAndView mv, @ModelAttribute("accountForm") AccountForm accountForm) {
        mv.clear();
        Account account = accountService.findAccountByUsername(accountForm.getUsername());

        if (!accountForm.getPassword().equals("")) {
            if (accountForm.getRePassword().equals("")) {
                accountForm.setDoB(account.getDoB());
                accountForm.setEmail(account.getEmail());
                accountForm.setFirstName(account.getFirstName());
                accountForm.setLastName(account.getLastName());
                accountForm.setUsername(account.getUsername());
                accountForm.setPassword("");
                mv.addObject("accountForm", accountForm);
                mv.setViewName("profile");
                mv.addObject("error", "*** Re-entered password ***");
                return mv;
            }

            if (!accountForm.getPassword().equals(accountForm.getRePassword())) {
                accountForm.setDoB(account.getDoB());
                accountForm.setEmail(account.getEmail());
                accountForm.setFirstName(account.getFirstName());
                accountForm.setLastName(account.getLastName());
                accountForm.setUsername(account.getUsername());
                accountForm.setPassword("");
                accountForm.setRePassword("");
                mv.addObject("accountForm", accountForm);
                mv.setViewName("profile");
                mv.addObject("error", "*** Re-entered password doesn't match ***");
                return mv;
            }

            account.setPassword(accountForm.getPassword());
        } else {
            account.setPassword("");
        }

        account.setDoB(accountForm.getDoB());
        account.setFirstName(accountForm.getFirstName());
        account.setLastName(accountForm.getLastName());

        accountService.update(account);

        accountForm.setDoB(account.getDoB());
        accountForm.setEmail(account.getEmail());
        accountForm.setFirstName(account.getFirstName());
        accountForm.setLastName(account.getLastName());
        accountForm.setUsername(account.getUsername());
        accountForm.setPassword("");
        accountForm.setRePassword("");
        mv.addObject("accountForm", accountForm);

        mv.addObject("success", "Update infomation successfully !");

        mv.setViewName("profile");
        return mv;
    }

    // For Admin feature
    @PostMapping("/account/delete")
    @ResponseBody
    public String accountDelete(ModelAndView mv, @ModelAttribute("account") Account deleteAccount) {

        accountService.delete(deleteAccount);

        return "OK";
    }
}
