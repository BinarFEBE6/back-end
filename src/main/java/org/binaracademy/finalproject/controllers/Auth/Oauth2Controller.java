package org.binaracademy.finalproject.controllers.Auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.binaracademy.finalproject.dto.Request.AuthRequest.GoogleRequest;
import org.binaracademy.finalproject.dto.Response.JwtResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ERole;
import org.binaracademy.finalproject.entity.RoleEntity;
import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.helper.utility.StatusCode;
import org.binaracademy.finalproject.repositories.RoleRepo;
import org.binaracademy.finalproject.repositories.UserRepo;
import org.binaracademy.finalproject.security.jwt.JwtUtils;
import org.binaracademy.finalproject.security.services.UserDetailsImpl;
import org.binaracademy.finalproject.services.UsersDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Operation signin and signup")
public class Oauth2Controller {

    public static final Logger logger = LoggerFactory.getLogger(Oauth2Controller.class);
    public static final String ROLE_NOT_FOUND = "Error: Role is not found.";
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepository;
    @Autowired
    UsersDetailsService usersDetailsService;

    @Autowired
    RoleRepo roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Operation(summary = "Login (EndPoint untuk user Login with google)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "User Login with google",
                            description = "EndPoint ini digunakan untuk user dapat melakukan login with google",
                            value = "{\n"
                                    + "    \"success\": true,\n"
                                    + "    \"statusCode\": 200,\n"
                                    + "    \"message\": \"sukses\",\n"
                                    + "    \"data\":{\n"
                                    + "            \"token\": \"gd732r63839rg773.9fggf783g387gd.9qwgf87qgr37\",\n"
                                    + "            \"type\": \"Bearer\",\n"
                                    + "            \"id\": 1,\n"
                                    + "            \"username\": \"budi123\",\n"
                                    + "            \"email\": \"budi@gmail.com\",\n"
                                    + "            \"roles\": [\n"
                                    + "                \"ROLE_USER\"\n"
                                    + "              ]\n"
                                    + "        }\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", content = @Content(examples = {
                    @ExampleObject(name = "Server Error",
                            description = "Tampilan jika server error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 500,\n"
                                    + "    \"message\": \"Server Error Message\",\n"
                                    + "    \"data\": null\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/google")
    public ResponseEntity<ResponseData<JwtResponse>> authenticateUser(@Valid @RequestBody GoogleRequest googleRequest, Errors errors) {

        if (!Boolean.TRUE.equals(userRepository.existsByEmail(googleRequest.getEmail()))) {
            logger.info("Email is ready to use");
            // Create new user's account
            UserEntity user = UserEntity.builder()
                    .username(googleRequest.getName())
                    .email(googleRequest.getEmail())
                    .password(encoder.encode(googleRequest.getSub()))
                    .profile(googleRequest.getPicture())
                    .createAt(LocalDateTime.now()).build();

            Set<RoleEntity> roles = new HashSet<>();

            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> {
                        logger.warn(ROLE_NOT_FOUND);
                        return new RuntimeException(ROLE_NOT_FOUND);
                    });
            roles.add(userRole);
            user.setRoles(roles);
            UserEntity userEntity = userRepository.save(user);
            usersDetailsService.create(UserDetailsEntity.builder()
                    .user_id(userEntity.getId())
                    .displayName(userEntity.getUsername())
                    .createAt(LocalDateTime.now())
                    .build());
        }

        ResponseData<JwtResponse> responseData = new ResponseData<>();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(googleRequest.getName(), googleRequest.getSub()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        logger.info("sukses login user");
        responseData.setStatusCode(StatusCode.OK);
        responseData.setSuccess(true);
        responseData.setMessage("sukses");
        responseData.setData(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
        return ResponseEntity.ok(responseData);
    }
}
