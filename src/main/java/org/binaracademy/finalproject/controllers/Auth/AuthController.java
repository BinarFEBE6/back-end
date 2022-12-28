package org.binaracademy.finalproject.controllers.Auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.binaracademy.finalproject.dto.Request.AuthRequest.LoginRequest;
import org.binaracademy.finalproject.dto.Request.AuthRequest.LoginV2Request;
import org.binaracademy.finalproject.dto.Request.AuthRequest.SignupRequest;
import org.binaracademy.finalproject.dto.Response.JwtResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ERole;
import org.binaracademy.finalproject.entity.RoleEntity;
import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.helper.utility.ErrorParsingUtility;
import org.binaracademy.finalproject.helper.utility.StatusCode;
import org.binaracademy.finalproject.repositories.RoleRepo;
import org.binaracademy.finalproject.repositories.UserRepo;
import org.binaracademy.finalproject.security.jwt.JwtUtils;
import org.binaracademy.finalproject.security.services.UserDetailsImpl;
import org.binaracademy.finalproject.services.UsersDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
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

    @Operation(summary = "Login (EndPoint untuk user Login \"https://febe6.up.railway.app/api/auth/signin\")")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Login User",
                            description = "EndPoint ini digunakan untuk user dapat melakukan login " +
                                    "Kemudian jika user berhasil login maka akan direturn data seperti dibawah," +
                                    "yang dimana data token dapat digunakan untuk (authorization) nanti," +
                                    "jika bisa simpan token ke local stroge user untuk dapat digunakan disetiap endpoint)",
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
            @ApiResponse(responseCode = "400", content = @Content(examples = {
                    @ExampleObject(name = "Request Error",
                            description = "Tampilan jika request error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 400,\n"
                                    + "    \"message\": \"Request Error Message\",\n"
                                    + "    \"data\": null\n"
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
    @PostMapping("/signin")
    public ResponseEntity<ResponseData<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, Errors errors) {

        ResponseData<JwtResponse> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            responseData.setStatusCode(StatusCode.BAD_REQUEST);
            responseData.setSuccess(false);
            responseData.setMessage(ErrorParsingUtility.parse(errors).toString());
            logger.warn("request invalid :{}", ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        logger.info("sukses login user : {}", loginRequest.getUsername());
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

    @Operation(summary = "Signup (EndPoint untuk user register \"https://febe6.up.railway.app/api/auth/signup\")")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Signup User",
                            description = "EndPoint ini digunakan untuk user melakukan signup/register akun, jika user berhasil melakukan" +
                                    "signup maka akan dikirim kan data seperti dibawah ini",
                            value = "{\n"
                                    + "    \"success\": true,\n"
                                    + "    \"statusCode\": 200,\n"
                                    + "    \"message\": \"User registered successfully!\",\n"
                                    + "    \"data\":{\n"
                                    + "            \"id\": 1,\n"
                                    + "            \"username\": \"budi123\",\n"
                                    + "            \"email\": \"budi@gmail.com\",\n"
                                    + "            \"password\": \"8472387462387\",\n"
                                    + "            \"profile\": \"http:/urlprofile\",\n"
                                    + "            \"createAt\": \"2022-12-06T01:46:30.179Z\",\n"
                                    + "            \"updateAt\": \"2022-12-06T01:46:30.179Z\",\n"
                                    + "            \"roles\": [\n"
                                    + "               {\n"
                                    + "                 \"id\": 1,\n"
                                    + "                 \"name\": \"ROLE_USER\"\n"
                                    + "               }\n"
                                    + "              ]\n"
                                    + "        }\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", content = @Content(examples = {
                    @ExampleObject(name = "Request Error",
                            description = "Tampilan jika request error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 400,\n"
                                    + "    \"message\": \"Request Error Message\",\n"
                                    + "    \"data\": null\n"
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
    @PostMapping("/signup")
    public ResponseEntity<ResponseData<UserEntity>> registerUser(@Valid @RequestBody SignupRequest signUpRequest, Errors errors) {
        ResponseData<UserEntity> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            responseData.setStatusCode(StatusCode.BAD_REQUEST);
            responseData.setSuccess(false);
            responseData.setMessage(ErrorParsingUtility.parse(errors).toString());
            logger.warn("request invalid :{}", ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            logger.warn("Error: Username is already taken!");
            responseData.setStatusCode(StatusCode.BAD_REQUEST);
            responseData.setSuccess(false);
            responseData.setMessage("Error: Username is already taken!");
            return ResponseEntity.badRequest().body(responseData);
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            logger.warn("Error: Email is already in use!");
            responseData.setStatusCode(StatusCode.BAD_REQUEST);
            responseData.setSuccess(false);
            responseData.setMessage("Error: Email is already in use!");
            return ResponseEntity.badRequest().body(responseData);
        }

        // Create new user's account
        UserEntity user = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> {
                        logger.warn(ROLE_NOT_FOUND);
                        return new RuntimeException(ROLE_NOT_FOUND);
                    });
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> {
                                    logger.warn(ROLE_NOT_FOUND);
                                    return new RuntimeException(ROLE_NOT_FOUND);
                                });
                        roles.add(adminRole);

                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> {
                                    logger.warn(ROLE_NOT_FOUND);
                                    return new RuntimeException(ROLE_NOT_FOUND);
                                });
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        UserEntity data = userRepository.save(user);
        usersDetailsService.create(UserDetailsEntity.builder()
                .displayName(data.getUsername())
                .birthDate(null)
                .address(null)
                .gender(null)
                .user_id(data.getId())
                .user(null)
                .createAt(LocalDateTime.now())
                .updateAt(null).build());
        responseData.setStatusCode(StatusCode.OK);
        responseData.setSuccess(true);
        responseData.setMessage("User registered successfully!");
        responseData.setData(user);
        logger.info("new signup sukses user : {}", signUpRequest.getUsername());
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/getRedirect")
    public ResponseEntity<ResponseData<JwtResponse>> authenticateUser(@Valid @RequestBody LoginV2Request loginV2Request) {

        if (!Boolean.TRUE.equals(userRepository.existsByEmail(loginV2Request.getEmail()))) {
            logger.info("Email is ready to use");
            // Create new user's account
            UserEntity user = UserEntity.builder()
                    .username(loginV2Request.getName())
                    .email(loginV2Request.getEmail())
                    .password(encoder.encode(loginV2Request.getSub()))
                    .profile(loginV2Request.getPicture())
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
                new UsernamePasswordAuthenticationToken(loginV2Request.getName(), loginV2Request.getSub()));

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
