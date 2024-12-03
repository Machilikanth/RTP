package com.toucan.tourtptrs.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucan.tourtptrs.models.ListOfBanks;

@RestController
@RequestMapping("/toucan/rtp/listofbanks")
public class ListOfBanksController extends AbstractCoreController<ListOfBanks>{

}
