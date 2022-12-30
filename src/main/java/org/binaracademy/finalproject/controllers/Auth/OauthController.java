package org.binaracademy.finalproject.controllers.Auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Oauth", description = "Operation Login with Google")
public class OauthController {

    public static final Logger logger = LoggerFactory.getLogger(OauthController.class);
    public static final String ROLE_NOT_FOUND = "Error: Role is not found.";

    private final AuthenticationManager authenticationManager;
    private final UsersDetailsService userDetailsService;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Operation(summary = "Ini tidak digunakan!,Login (EndPoint untuk user Login with google)")
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
    @GetMapping("/token")
    public ResponseEntity<ResponseData<JwtResponse>> loginGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        Map<String, Object> tempPrincipalAuth = oAuth2AuthenticationToken.getPrincipal().getAttributes();

        if (!Boolean.TRUE.equals(userRepository.existsByEmail(tempPrincipalAuth.get("email").toString()))) {
            logger.info("Email is ready to use");
            // Create new user's account
            UserEntity user = UserEntity.builder()
                    .username(tempPrincipalAuth.get("name").toString())
                    .email(tempPrincipalAuth.get("email").toString())
                    .password(encoder.encode(tempPrincipalAuth.get("sub").toString()))
                    .profile(tempPrincipalAuth.get("picture").toString())
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
            userDetailsService.create(UserDetailsEntity.builder()
                    .user_id(userEntity.getId())
                    .displayName(userEntity.getUsername())
                    .createAt(LocalDateTime.now())
                    .build());
        }

        ResponseData<JwtResponse> responseData = new ResponseData<>();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tempPrincipalAuth.get("name").toString(), tempPrincipalAuth.get("sub").toString()));

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
