package com.thai27.chiatien.Service;

import com.thai27.chiatien.DTO.Data.UserListDto;
import com.thai27.chiatien.DTO.Request.UserChangePasswordRequest;
import com.thai27.chiatien.DTO.Request.UserResetPasswordRequest;
import com.thai27.chiatien.DTO.Request.UserValidateSignupRequest;
import com.thai27.chiatien.DTO.Response.UserListResponse;
import com.thai27.chiatien.Entity.ChiaTienUser;
import com.thai27.chiatien.Entity.Role;
import com.thai27.chiatien.Entity.UserSignupRequest;
import com.thai27.chiatien.Exception.ResourceNotFoundException;
import com.thai27.chiatien.Exception.TokenExpiredException;
import com.thai27.chiatien.Repository.ChiaTienUserRepository;
import com.thai27.chiatien.Repository.RoleRepository;
import com.thai27.chiatien.Repository.UserSignupRequestRepository;
import com.thai27.chiatien.Security.JWTAuthenProvider;
import com.thai27.chiatien.Security.JWTUltil;
import com.thai27.chiatien.Ulti.Constant;
import com.thai27.chiatien.Ulti.GenerateRandomString;
import com.thai27.chiatien.Ulti.ResponseModel;
import com.thai27.chiatien.Ulti.SendEmail;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiaTienUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChiaTienUserService.class);

    @Autowired
    JWTUltil jwtUtil;

    @Autowired
    JWTAuthenProvider jwtAuth;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ChiaTienUserRepository chiaTienUserRepository;

    @Autowired
    UserSignupRequestRepository userSignupRequestRepository;

    @Autowired
    GenerateRandomString randomString;

    @Autowired
    SendEmail sendEmail;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;

    public ResponseModel<String> login(ChiaTienUser userData) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userData.getUsername(),
                        userData.getPassword()
                );
        Authentication authen = jwtAuth.authenticate(token);
        String jwtToken = jwtUtil.generate(authen);
        return ResponseModel.success(jwtToken, "Đăng nhập thành công");
    }

    public ResponseModel<String> userSignup(UserValidateSignupRequest request) throws ResourceNotFoundException {
        UserSignupRequest userSignupRequest =
                userSignupRequestRepository.findByRequestCode(request.getValidateCode()).orElseThrow(() -> new ResourceNotFoundException("Mã xác thực " + request.getValidateCode() + " không tồn tại trong hệ thống"));
        if (!userSignupRequestRepository.getCodeByEmail(request.getEmail()).equals(request.getValidateCode())) {
            return ResponseModel.warning(null, "Mã xác thực đã hết hạn vui lòng kiểm tra lại email");
        } else {
            ChiaTienUser userData = new ChiaTienUser();
            userData.setUsername(userSignupRequest.getUsername());
            userData.setPassword(encoder.encode(userSignupRequest.getPassword()));
            userData.setEmail(userSignupRequest.getEmail());
            Optional<Role> userRole = roleRepository.findByRolename(Constant.ROLE_USER);
            userData.setRoles(List.of(userRole.get()));
            chiaTienUserRepository.save(userData);
            userSignupRequestRepository.deleteAllByEmail(userSignupRequest.getEmail());
            return ResponseModel.success(null, sendEmail.sendSignupSuccess(userSignupRequest.getEmail(), userSignupRequest.getUsername()));
        }
    }

    public ResponseModel<Claims> getClaimsFromToken(String token) throws TokenExpiredException {
        try {
            return ResponseModel.success(jwtUtil.getClaims(token), "Lấy thông tin user thành công");
        } catch (Exception e) {
            throw new TokenExpiredException("Phiên đăng nhập hết hạn vui lòng đăng nhập lại");
        }
    }

    public ResponseModel<String> resetPassword(UserResetPasswordRequest userRequest) throws ResourceNotFoundException {
        ChiaTienUser resetUser = chiaTienUserRepository.findByUsername(userRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Tên người dùng không tồn tại trong hệ thống: " + userRequest.getUsername()));
        if (userRequest.getEmail().equals(resetUser.getEmail())) {
            String newPassword = randomString.generateRandomCode(8);
            String newEncodedPassword = encoder.encode(newPassword);
            resetUser.setPassword(newEncodedPassword);
            chiaTienUserRepository.save(resetUser);
            return ResponseModel.success(null, sendEmail.sendNewPassword(resetUser.getEmail(), resetUser.getUsername(), newPassword));
        } else throw new RuntimeException("Email người dùng nhập không khớp với email đã đăng ký");
    }

    public ResponseModel<String> changePassword(UserChangePasswordRequest userRequest) throws ResourceNotFoundException {
        String username = jwtUtil.getUsername(userRequest.getToken());
        ChiaTienUser changePassUser = chiaTienUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Tên người dùng không tồn tại trong hệ thống: " + username));
        if (encoder.matches(userRequest.getOldPassword(), changePassUser.getPassword())) {
            changePassUser.setPassword(encoder.encode(userRequest.getNewPassword()));
            chiaTienUserRepository.save(changePassUser);
            return ResponseModel.success(null, "Đổi mật khẩu thành công vui lòng đăng nhập lại");
        } else throw new RuntimeException("Mật khẩu người dùng nhập không trùng với mật khẩu trên hệ thống");
    }

    public ResponseModel<ChiaTienUser> getUserById(Long id) throws ResourceNotFoundException {
        return ResponseModel.success(chiaTienUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không có người dùng với id:" + id)), "Lấy dữ liệu thành công");
    }

    public ResponseModel<ChiaTienUser> getByUsername(String username) throws ResourceNotFoundException {
        return ResponseModel.success(chiaTienUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Không có người dùng với username:" + username)), "Lấy dữ liệu thành công");
    }

    public ResponseModel<String> setUserModerRole(Long userId) throws ResourceNotFoundException {
        if (!chiaTienUserRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Không có người dùng với id:" + userId);
        } else roleRepository.setUserModerRole(userId);
        return ResponseModel.success(null, "Thêm quyền Moder thành công");
    }

    public ResponseModel<String> unsetUserModerRoles(Long userId) throws ResourceNotFoundException {
        if (!chiaTienUserRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Không có người dùng với id:" + userId);
        } else roleRepository.unsetUserModerRole(userId);
        return ResponseModel.success(null, "Hủy quyền Moder thành công");
    }

    public ResponseModel<UserListResponse> findAllByUsername(String username, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<ChiaTienUser> userListEntity;
        if (username.isEmpty()) {
            userListEntity = chiaTienUserRepository.findAllExcludingAdmin(pageRequest);
        } else {
            String likeUsername = "%" + username + "%";
            userListEntity = chiaTienUserRepository.
                    findAllByUsernameLikeIgnoreCaseExcludingAdmin(likeUsername, pageRequest);
        }
        List<UserListDto> userListSearchDto = userListEntity
                .stream().map(userList -> modelMapper.map(userList, UserListDto.class))
                .collect(Collectors.toList());
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.setContent(userListSearchDto);
        userListResponse.setPageNo(userListEntity.getNumber());
        userListResponse.setPageSize(userListEntity.getSize());
        userListResponse.setTotalElements(userListEntity.getTotalElements());
        userListResponse.setTotalPages(userListEntity.getTotalPages());
        userListResponse.setLast(userListEntity.isLast());
        return ResponseModel.success(userListResponse, "Lấy dữ liệu thành công");
    }

    public ResponseModel<UserListResponse> findAllUser() {
        List<ChiaTienUser> userListEntity = chiaTienUserRepository.findAllExcludingAdminList();
        List<UserListDto> userListSearchDto = userListEntity
                .stream().map(userList -> modelMapper.map(userList, UserListDto.class))
                .collect(Collectors.toList());
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.setContent(userListSearchDto);
        return ResponseModel.success(userListResponse, "Lấy dữ liệu thành công");
    }

    public ResponseModel<String> deleteUserById(Long userId) {
        try {
            ChiaTienUser deleteUser = chiaTienUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại trong hệ thống"));
            roleRepository.deleteAllUserRoles(userId);
            chiaTienUserRepository.delete(deleteUser);
            return ResponseModel.success(null, "Xóa người dùng thành công");
        } catch (Exception e) {
            LOGGER.error("Delete user error", e);
            return ResponseModel.error(null, "Xóa người dùng thất bại");
        }


    }

}
