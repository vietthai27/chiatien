package com.thai27.chiatien.Controller;

import com.thai27.chiatien.DTO.Request.UserChangePasswordRequest;
import com.thai27.chiatien.DTO.Request.UserResetPasswordRequest;
import com.thai27.chiatien.DTO.Request.UserValidateSignupRequest;
import com.thai27.chiatien.DTO.Response.UserListResponse;
import com.thai27.chiatien.Entity.ChiaTienUser;
import com.thai27.chiatien.Entity.UserSignupRequest;
import com.thai27.chiatien.Exception.ResourceNotFoundException;
import com.thai27.chiatien.Exception.TokenExpiredException;
import com.thai27.chiatien.Exception.UserInfoAlreadyExistException;
import com.thai27.chiatien.Repository.ChiaTienUserRepository;
import com.thai27.chiatien.Security.JWTAuthenProvider;
import com.thai27.chiatien.Security.JWTUltil;
import com.thai27.chiatien.Service.ChiaTienUserService;
import com.thai27.chiatien.Service.UserDetailService;
import com.thai27.chiatien.Service.UserSignupRequestService;
import com.thai27.chiatien.Ulti.ResponseModel;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class ChiaTienUserController {

    @Autowired
    JWTUltil jwtUtil;

    @Autowired
    JWTAuthenProvider jwtAuth;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    ChiaTienUserService chiaTienUserService;

    @Autowired
    ChiaTienUserRepository chiaTienUserRepository;

    @Autowired
    UserSignupRequestService userSignupRequestService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/permit/user-signup-request")
    public ResponseEntity<ResponseModel<String>> userSignupRequest(@RequestBody UserSignupRequest userData) throws UserInfoAlreadyExistException {
        return ResponseEntity.ok(userSignupRequestService.createSignupRequest(userData));
    }

    @PostMapping("/permit/user-signup")
    public ResponseEntity<ResponseModel<String>> userSignup(@RequestBody UserValidateSignupRequest request) throws ResourceNotFoundException {
        return ResponseEntity.ok(chiaTienUserService.userSignup(request));
    }

    @PostMapping("/permit/login")
    public ResponseEntity<ResponseModel<String>> login(@RequestBody ChiaTienUser userData) {
        return ResponseEntity.ok(chiaTienUserService.login(userData));
    }

    @PostMapping("/permit/reset-password")
    public ResponseEntity<ResponseModel<String>> resetPassword(@RequestBody UserResetPasswordRequest userRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(chiaTienUserService.resetPassword(userRequest));
    }

    @GetMapping("/permit/get-claims-from-token")
    public ResponseEntity<ResponseModel<Claims>> getClaimsFromToken(@RequestParam String token) throws TokenExpiredException {
        return ResponseEntity.ok(chiaTienUserService.getClaimsFromToken(token));
    }

    @PostMapping("/auth/change-password")
    public ResponseEntity<ResponseModel<String>> changePassword(@RequestBody UserChangePasswordRequest userRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(chiaTienUserService.changePassword(userRequest));
    }

    @GetMapping("/auth/admin/search-user")
    public ResponseEntity<ResponseModel<UserListResponse>> searchUser(@RequestParam String search, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(chiaTienUserService.findAllByUsername(search, pageNum, pageSize));
    }

    @GetMapping("/auth/get-user-by-id/{userId}")
    public ResponseEntity<ResponseModel<ChiaTienUser>> getUserById(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(chiaTienUserService.getUserById(userId));
    }

    @GetMapping("/auth/get-user-by-username/{username}")
    public ResponseEntity<ResponseModel<ChiaTienUser>> getUserByUsername(@PathVariable String username) throws ResourceNotFoundException {
        return ResponseEntity.ok(chiaTienUserService.getByUsername(username));
    }

    @PutMapping("/auth/admin/set-moder-role/{userId}")
    public ResponseEntity<ResponseModel<String>> setUserModerRole(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(chiaTienUserService.setUserModerRole(userId));
    }

    @PutMapping("/auth/admin/unset-moder-role/{userId}")
    public ResponseEntity<ResponseModel<String>> unsetUserModerRole(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(chiaTienUserService.unsetUserModerRoles(userId));
    }

    @DeleteMapping("/auth/admin/delete-user-by-id/{userId}")
    public ResponseEntity<ResponseModel<String>> deleteUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(chiaTienUserService.deleteUserById(userId));
    }

}
