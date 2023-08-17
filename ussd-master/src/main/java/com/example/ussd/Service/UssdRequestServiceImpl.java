package com.example.ussd.Service;

import com.example.ussd.Model.UssdRequest;
import com.example.ussd.Repository.UssdRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UssdRequestServiceImpl implements UssdRequestService{

    private final UssdRequestRepository ussdRequestRepository;

    @Autowired
    public UssdRequestServiceImpl(UssdRequestRepository ussdRequestRepository) {
        this.ussdRequestRepository = ussdRequestRepository;
    }

    @Override
    public UssdRequest findBySessionId(String sessionId) {
        return ussdRequestRepository.findBySessionId(sessionId);
    }

    @Override
    public void saveUssdRequest(UssdRequest ussdRequest) {
        ussdRequestRepository.save(ussdRequest);
    }
}
