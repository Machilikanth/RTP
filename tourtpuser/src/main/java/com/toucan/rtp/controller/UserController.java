package com.toucan.rtp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucan.rtp.model.User;

@RestController
@RequestMapping("/toucan/rtp/acc")
public class UserController extends AbstractCoreController<User> {

}
