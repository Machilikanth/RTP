package com.toucan.tourtptrs.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucan.tourtptrs.models.UpiPin;

@RestController
@RequestMapping("/toucan/trs/upipinctrl")
public class UpiPinController extends AbstractCoreController<UpiPin>{

}
