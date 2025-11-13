package com.thai27.chiatien.Service;

import com.thai27.chiatien.Entity.UserSignupRequest;
import com.thai27.chiatien.Repository.ChiaTienUserRepository;
import com.thai27.chiatien.Repository.UserSignupRequestRepository;
import com.thai27.chiatien.Ulti.GenerateRandomString;
import com.thai27.chiatien.Ulti.ResponseModel;
import com.thai27.chiatien.Ulti.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserSignupRequestService {

    @Autowired
    GenerateRandomString randomString;

    @Autowired
    SendEmail sendEmail;

    @Autowired
    UserSignupRequestRepository userSignupRequestRepository;

    @Autowired
    ChiaTienUserRepository chiaTienUserRepository;

    public ResponseModel<String> createSignupRequest(UserSignupRequest userSignupRequest) {
        if (chiaTienUserRepository.findByUsername(userSignupRequest.getUsername()).isPresent()) {
            return ResponseModel.warning(null, "Tên người dùng " + userSignupRequest.getUsername() + " đã tồn tại !!!");
        } else if (chiaTienUserRepository.findByEmail(userSignupRequest.getEmail()).isPresent()) {
            return ResponseModel.warning(null, "Email " + userSignupRequest.getEmail() + " đã tồn tại !!!");
        } else {
            UserSignupRequest createUser = new UserSignupRequest();
            createUser.setUsername(userSignupRequest.getUsername());
            createUser.setPassword(userSignupRequest.getPassword());
            createUser.setEmail(userSignupRequest.getEmail());
            String requestCode = randomString.generateRandomCode(5);
            createUser.setRequestCode(requestCode);
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            createUser.setRequestTime(formattedDate);
            userSignupRequestRepository.save(createUser);
            return ResponseModel.success(null, sendEmail.sendSignupRequest(userSignupRequest.getEmail(), userSignupRequest.getUsername(), requestCode));
        }
    }
}
