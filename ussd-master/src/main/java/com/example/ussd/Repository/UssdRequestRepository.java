package com.example.ussd.Repository;

//import com.example.ussd.Model.User;
import com.example.ussd.Model.Userz;
import com.example.ussd.Model.UssdRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UssdRequestRepository extends JpaRepository<UssdRequest, Long> {
    UssdRequest findBySessionId(String sessionId);
}
