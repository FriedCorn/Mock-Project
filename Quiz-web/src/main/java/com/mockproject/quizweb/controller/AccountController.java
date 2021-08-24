package com.mockproject.quizweb.controller;

import java.util.List;

import com.mockproject.quizweb.domain.Account;
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

    @GetMapping("/account/{accountId}")
    @ResponseBody
    public Account accountDetail(ModelAndView mv, @PathVariable("accountId") int accountId) {

        return accountService.getById(accountId);
    }

    @PostMapping("/account/update")
    @ResponseBody
    public Account accountUpdate(ModelAndView mv, @ModelAttribute("account") Account updateAccount) {
        accountService.update(updateAccount);

        return updateAccount;
    }

    // For Admin feature
    @PostMapping("/account/delete")
    @ResponseBody
    public String accountDelete(ModelAndView mv, @ModelAttribute("account") Account deleteAccount) {

        accountService.delete(deleteAccount);

        return "OK";
    }
}
