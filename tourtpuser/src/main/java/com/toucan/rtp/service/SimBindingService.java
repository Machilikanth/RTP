package com.toucan.rtp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toucan.rtp.model.SimBinding;
import com.toucan.rtp.repository.SimBindingRepository;


@Service
public class SimBindingService extends AbstractService<SimBinding> {
	
	private static final Logger logger = LoggerFactory.getLogger(SimBindingService.class);

    @Autowired
    private SimBindingRepository simBindingRepository;

    public SimBinding sim(SimBinding binding) {
    	logger.info("Inserting SimBinding: {}", binding);
        return this.create(binding);
    }

    public SimBinding mobile(String mobileNumber) {
    	logger.info("Fetching SimBinding with mobile number: {}", mobileNumber);
        SimBinding simBinding = simBindingRepository.findByMSISDN(mobileNumber);
        if (simBinding != null) {
        	logger.info("Found SimBinding: {}", simBinding);
        } else {
        	logger.warn("No SimBinding found for mobile number: {}", mobileNumber);
        }
        return simBinding;
    }
}

