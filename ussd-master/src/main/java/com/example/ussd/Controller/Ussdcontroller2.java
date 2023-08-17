package com.example.ussd.Controller;

import com.example.ussd.Model.RegistrationStep;
import com.example.ussd.Model.Userz;
import com.example.ussd.Model.UssdRequest;
import com.example.ussd.Repository.UserRepository;
import com.example.ussd.Repository.UssdRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class Ussdcontroller2 {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UssdRequestRepository ussdRequestRepository;

    @PostMapping(value = "/ussd", consumes = "application/json")
    public String processUssdRequest(@RequestBody UssdRequest ussdRequest, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        String sessionId = ussdRequest.getSessionId();
        String input = ussdRequest.getInput(); // Renamed from "text"
        int newRequest = ussdRequest.getNewRequest();
        String response = "";

        boolean isNewRequest = newRequest == 1;

        if (isNewRequest || input.equals("")) {
            httpSession.invalidate();
            httpSession = request.getSession(true);

            if (isNewRequest) {
                // Insert into Ussdrequest table
                UssdRequest newUssdRequest = new UssdRequest();
                newUssdRequest.setSessionId(ussdRequest.getSessionId());
                newUssdRequest.setPhoneNumber(ussdRequest.getPhoneNumber());
                newUssdRequest.setLevel(1);
                newUssdRequest.setInput("");
                ussdRequestRepository.save(newUssdRequest);

                // Start the session with a menu for language selection
                response = " Select your preferred language:\n";
                response += "1. English\n";
                response += "2. Kinyarwanda";
            }
        } else {
            // Retrieve the UssdRequest based on the session ID
            UssdRequest savedUssdRequest = ussdRequestRepository.findBySessionId(sessionId);

            if (savedUssdRequest != null) {
                int currentLevel = savedUssdRequest.getLevel();
                String language = savedUssdRequest.getLanguage();
                RegistrationStep currentStep = savedUssdRequest.getCurrentStep();

                if (currentStep == null) {
                    currentStep = RegistrationStep.LANGUAGE;
                    savedUssdRequest.setCurrentStep(currentStep);
                }

                switch (currentStep) {
                    case LANGUAGE:
                        if (input.equalsIgnoreCase("1")) {
                            savedUssdRequest.setLanguage("English");
                            savedUssdRequest.setCurrentStep(RegistrationStep.FIRST_NAME);
                            response = " Please enter your First Name:";
                        } else if (input.equalsIgnoreCase("2")) {
                            savedUssdRequest.setLanguage("Kinyarwanda");
                            savedUssdRequest.setCurrentStep(RegistrationStep.FIRST_NAME);
                            response = " Andika izina ryawe rya mbere:";
                        } else {
                            response = " Invalid input. Select your preferred language:\n";
                            response += "1. English\n";
                            response += "2. Kinyarwanda";
                        }
                        break;
                    case FIRST_NAME:
                        savedUssdRequest.setFirstNameInput(input);
                        savedUssdRequest.setCurrentStep(RegistrationStep.LAST_NAME);
                        response = language.equalsIgnoreCase("Kinyarwanda")
                                ? " Andika izina ryawe rya nyuma:"
                                : " Please enter your Last Name:";
                        break;
                    case LAST_NAME:
                        savedUssdRequest.setLastNameInput(input);
                        savedUssdRequest.setCurrentStep(RegistrationStep.NATIONAL_ID);
                        response = language.equalsIgnoreCase("Kinyarwanda")
                                ? " Nyamuneka andika inomero y'irangamuntu:"
                                : " Please enter your National ID:";
                        break;
                    case NATIONAL_ID:
                        savedUssdRequest.setNationalIdInput(input);
                        savedUssdRequest.setCurrentStep(RegistrationStep.LOCATION);
                        response = language.equalsIgnoreCase("Kinyarwanda")
                                ? " Nyamuneka andika aho uherereye:"
                                : " Please enter your Location:";
                        break;
                    case LOCATION:
                        savedUssdRequest.setLocationInput(input);
                        savedUssdRequest.setCurrentStep(RegistrationStep.CONFIRMATION);

                        String firstName = savedUssdRequest.getFirstNameInput();
                        String lastName = savedUssdRequest.getLastNameInput();
                        String nationalId = savedUssdRequest.getNationalIdInput();

                        response = language.equalsIgnoreCase("Kinyarwanda")
                                ? "   Emeza amakuru yawe:\n"
                                + "Izina ryawe rya mbere: " + firstName + "\n"
                                + "izina ryawe rya nyuma: " + lastName + "\n"
                                + "Numero yawe y'irangamuntu: " + nationalId + "\n"
                                + "Aho uherereye: " + input + "\n"
                                + "Andika 1 kwemeza cyangwa 2 kugira ngo uhagarike."
                                : "  Confirm your information:\n"
                                + "First Name: " + firstName + "\n"
                                + "Last Name: " + lastName + "\n"
                                + "National ID: " + nationalId + "\n"
                                + "Location: " + input + "\n"
                                + "Reply with 1 to confirm or 2 to cancel.";
                        break;

                    case CONFIRMATION:
                        if (input.equals("1")) {
                            Userz newUser = new Userz();
                            newUser.setFirstName(savedUssdRequest.getFirstNameInput());
                            newUser.setLastName(savedUssdRequest.getLastNameInput());
                            newUser.setNationalId(savedUssdRequest.getNationalIdInput());
                            newUser.setLocation(savedUssdRequest.getLocationInput());
                            userRepository.save(newUser);

                            ussdRequestRepository.delete(savedUssdRequest);

                            response = language.equalsIgnoreCase("Kinyarwanda")
                                    ? "END Kwiyandikisha birangiye. Amakuru yawe yabitswe."
                                    : "END Registration completed. Your information has been saved.";
                        } else if (input.equals("2")) {
                            savedUssdRequest.setCurrentStep(RegistrationStep.END);
                            ussdRequestRepository.save(savedUssdRequest);
                            response = language.equalsIgnoreCase("Kinyarwanda")
                                    ? "END Kwiyandikisha byahagaritswe."
                                    : "END Registration canceled.";
                        } else {
                            response = language.equalsIgnoreCase("Kinyarwanda")
                                    ? " Iyinjiza ritemewe. Subiza  1. kugirango wemeze cyangwa 2. guhagarika."
                                    : " Invalid input. Reply with 1 to confirm or 2 to cancel.";
                        }
                        break;

                    case END:
                        response = language.equalsIgnoreCase("Kinyarwanda")
                                ? "END Murakoze kwiyandikisha!"
                                : "END Thank you for registering!";
                        break;
                }

                currentLevel++;
                savedUssdRequest.setLevel(currentLevel);
                ussdRequestRepository.save(savedUssdRequest);
            } else {
                response = "END Invalid session ID. Please start a new session.";
            }
        }

        return response;
    }
}
