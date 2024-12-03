package com.toucan.tourtptrs.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucan.tourtptrs.models.Cards;

@RestController
@RequestMapping("/toucan/trs/cardsctrl")
public class CardsController extends AbstractCoreController<Cards>{

}
