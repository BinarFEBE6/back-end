//package org.binaracademy.finalproject.controllers.Auth;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.binaracademy.finalproject.dto.Request.AuthRequest.LoginV2Request;
//import org.binaracademy.finalproject.dto.Response.JwtResponse;
//import org.binaracademy.finalproject.dto.ResponseData;
//import org.binaracademy.finalproject.entity.ERole;
//import org.binaracademy.finalproject.entity.RoleEntity;
//import org.binaracademy.finalproject.entity.UserDetailsEntity;
//import org.binaracademy.finalproject.entity.UserEntity;
//import org.binaracademy.finalproject.helper.utility.StatusCode;
//import org.binaracademy.finalproject.repositories.RoleRepo;
//import org.binaracademy.finalproject.repositories.UserRepo;
//import org.binaracademy.finalproject.security.jwt.JwtUtils;
//import org.binaracademy.finalproject.security.services.UserDetailsImpl;
//import org.binaracademy.finalproject.services.UsersDetailsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.*;
//import javax.validation.Valid;
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/auth")
//@Tag(name = "Auth", description = "Operation signin and signup")
//public class LoginV2Controller {
//
//    public static final Logger logger = LoggerFactory.getLogger(LoginV2Controller.class);
//    public static final String ROLE_NOT_FOUND = "Error: Role is not found.";
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    UserRepo userRepository;
//    @Autowired
//    UsersDetailsService usersDetailsService;
//
//    @Autowired
//    RoleRepo roleRepository;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @PostMapping("/getRedirect")
//    public ResponseEntity<ResponseData<JwtResponse>> authenticateUser(@Valid @RequestBody LoginV2Request loginV2Request, Errors errors) {
//
//        if (!Boolean.TRUE.equals(userRepository.existsByEmail(loginV2Request.getEmail()))) {
//            logger.info("Email is ready to use");
//            // Create new user's account
//            UserEntity user = UserEntity.builder()
//                    .username(loginV2Request.getName())
//                    .email(loginV2Request.getEmail())
//                    .password(encoder.encode(loginV2Request.getSub()))
//                    .profile(loginV2Request.getPicture())
//                    .createAt(LocalDateTime.now()).build();
//
//            Set<RoleEntity> roles = new HashSet<>();
//
//            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> {
//                        logger.warn(ROLE_NOT_FOUND);
//                        return new RuntimeException(ROLE_NOT_FOUND);
//                    });
//            roles.add(userRole);
//            user.setRoles(roles);
//            UserEntity userEntity = userRepository.save(user);
//            usersDetailsService.create(UserDetailsEntity.builder()
//                    .user_id(userEntity.getId())
//                    .displayName(userEntity.getUsername())
//                    .createAt(LocalDateTime.now())
//                    .build());
//        }
//
//        ResponseData<JwtResponse> responseData = new ResponseData<>();
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginV2Request.getName(), loginV2Request.getSub()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        logger.info("sukses login user");
//        responseData.setStatusCode(StatusCode.OK);
//        responseData.setSuccess(true);
//        responseData.setMessage("sukses");
//        responseData.setData(new JwtResponse(jwt,
//                userDetails.getId(),
//                userDetails.getUsername(),
//                userDetails.getEmail(),
//                roles));
//        return ResponseEntity.ok(responseData);
//    }
//}
