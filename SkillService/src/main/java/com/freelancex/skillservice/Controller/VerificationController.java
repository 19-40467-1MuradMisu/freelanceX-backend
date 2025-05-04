package com.freelancex.skillservice.Controller;

import com.freelancex.skillservice.dtos.common.ApiResponse;
import com.freelancex.skillservice.dtos.VerifyRequestDto;
import com.freelancex.skillservice.model.Certification;
import com.freelancex.skillservice.services.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/skill")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }


    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Certification>> verifySkill(
            @Valid @RequestBody VerifyRequestDto requestDto) {

        Certification cert = verificationService.verifySkill(
            requestDto.getUserId(),
            requestDto.getSkills()
        );

        ApiResponse<Certification> response =
            new ApiResponse<>("success", HttpStatus.OK.value(), cert);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

