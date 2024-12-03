package com.toucan.tourtptrs.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucan.tourtptrs.models.BankDetails;

@RestController
@RequestMapping("/toucan/trs/bnkdtlsctrl")
public class BankDetailsController extends AbstractCoreController<BankDetails> {

}
