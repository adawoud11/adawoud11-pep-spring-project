package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.ValidationRules.AccountValidationRules;
import com.example.entity.Account;
import com.example.exception.ValidationException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) {
        ValidateAccount(account);
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new ValidationException(HttpStatus.resolve(409), AccountValidationRules.USERNAME_MUST_BE_UNIQUE);
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        ValidateAccount(account);
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount == null || !existingAccount.getPassword().equals(account.getPassword())) {
            throw new ValidationException(HttpStatus.resolve(401), AccountValidationRules.INVALID_USERNAME_OR_PASSWORD);
        }
        return existingAccount;
    }

    private void ValidateAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new ValidationException(HttpStatus.resolve(401), AccountValidationRules.PASSWORD_CANNOT_BE_EMPTY);
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new ValidationException(
                    HttpStatus.resolve(401), AccountValidationRules.PASSWORD_MUST_BE_AT_LEAST_4_CHARACTERS_LONG);
        }
    }
}
