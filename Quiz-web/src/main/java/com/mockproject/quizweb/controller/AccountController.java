package com.mockproject.quizweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.Role;
import com.mockproject.quizweb.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    // For Admin feature
    @GetMapping("/account/list")
    public ModelAndView accountList(ModelAndView mv, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object accountRoleObject = session.getAttribute("accountRole");
        if (accountRoleObject == null || ((Role) accountRoleObject).getName().equals("user")) {
            return new ModelAndView("error/account/accessPermissionDenied");
        }

        List<Account> accountList = accountRepository.findAll();

        mv.setViewName("account/accountList");
        mv.addObject("accountList", accountList);

        return mv;
    }

    @GetMapping("/account/{accountId}")
    public ModelAndView accountDetail(ModelAndView mv, @PathVariable("accountId") long accountId,
            HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object accountIdObject = session.getAttribute("accountId");
        if (accountIdObject == null || (int) accountIdObject != accountId) {
            return new ModelAndView("error/account/accessPermissionDenied");
        }

        Account account = accountRepository.getById(accountId);
        if (account == null) {
            return new ModelAndView("error/account/accountNotFound");
        }

        mv.setViewName("account/accountDetail");
        mv.addObject("account", account);

        return mv;
    }

    @PostMapping("/account/update")
    public ModelAndView accountUpdate(ModelAndView mv, @ModelAttribute("account") Account account,
            HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object accountIdObject = session.getAttribute("accountId");
        if (accountIdObject == null) {
            return new ModelAndView("error/account/accessPermissionDenied");
        }

        accountRepository.save(account);

        return new ModelAndView("redirect:/account/" + account.getId());
    }

    // For Admin feature
    @PostMapping("/account/delete/{accountId}")
    public ModelAndView accountDelete(ModelAndView mv, @PathVariable("accountId") long accountId,
            HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object accountIdObject = session.getAttribute("accountId");
        if (accountIdObject == null) {
            return new ModelAndView("error/account/accessPermissionDenied");
        }

        Object accountRoleObject = session.getAttribute("accountRole");
        Role accountRole = (Role) accountRoleObject;
        if (!accountRole.getName().equals("admin")) {
            return new ModelAndView("error/account/accessPermissionDenied");
        }

        accountRepository.deleteById(accountId);

        return new ModelAndView("redirect:/account/list");
    }
}
