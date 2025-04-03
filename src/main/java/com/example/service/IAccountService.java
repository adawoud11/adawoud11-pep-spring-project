package com.example.service;

import com.example.entity.Account;

public interface IAccountService {
    Account register(Account account);

    Account getAccountById(int id);

}
