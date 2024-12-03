package com.toucan.tourtptrs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toucan.tourtptrs.helper.Transactions;
import com.toucan.tourtptrs.models.AccountDetails;
import com.toucan.tourtptrs.models.Wallet;
import com.toucan.tourtptrs.models.requests.TransactionWalletRequest;
import com.toucan.tourtptrs.models.requests.VpaTransactionRequest;
import com.toucan.tourtptrs.models.response.TransactionResponse;
import com.toucan.tourtptrs.models.response.VpaTransactionResponseHelper;
import com.toucan.tourtptrs.repositories.WalletRepository;
import com.toucan.tourtptrs.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/toucan/trs/trsctrl")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    WalletRepository walletRepoO;

    @PostMapping("/initiate/wallet/transaction")
    public TransactionResponse<Wallet> postWalletTransaction(@Valid @RequestBody TransactionWalletRequest entity) {
        return transactionService.postWalletTransaction(entity);
    }

    @PostMapping("/initiate/account/transaction")
    public TransactionResponse<VpaTransactionResponseHelper> postTransaction(@Valid @RequestBody VpaTransactionRequest entity) {
        return transactionService.postVpaTransaction(entity);
    }
    
    @PostMapping("/initiate")
    public List<AccountDetails> postTransaction(@RequestBody AccountDetails entity ) {
        return transactionService.credit(entity);
    }
    
    @PostMapping("/fetch")
    public List<AccountDetails>mobilenumber(@RequestBody AccountDetails entity){
    	return transactionService.mobilenumber(entity);
    }

}
