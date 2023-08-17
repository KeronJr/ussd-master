package com.example.ussd.Service;

import com.example.ussd.Model.UssdRequest;

public interface UssdRequestService {
    UssdRequest findBySessionId(String sessionId);
    void saveUssdRequest(UssdRequest ussdRequest);
}
