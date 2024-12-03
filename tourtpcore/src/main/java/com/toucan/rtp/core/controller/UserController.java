package com.toucan.rtp.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucan.rtp.core.model.User;

@RestController
@RequestMapping("/toucan/core/user")
public class UserController extends AbstractCoreController<User> {
}
