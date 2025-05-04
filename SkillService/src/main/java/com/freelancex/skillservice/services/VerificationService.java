package com.freelancex.skillservice.services;

import com.freelancex.skillservice.dtos.SkillVerifiedEvent;
import com.freelancex.skillservice.kafka.KafkaProducerServiceImpl;
import com.freelancex.skillservice.model.Certification;
import com.freelancex.skillservice.repository.CertificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VerificationService {

    private final CertificationRepository certificationRepository;
    private final KafkaProducerServiceImpl kafkaProducerService;
   
    private static final List<String> SUPPORTED_SKILLS = List.of("java", "python", "javascript");

    public VerificationService(CertificationRepository certificationRepository, KafkaProducerServiceImpl kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
        this.certificationRepository = certificationRepository;
    }

    public Certification verifySkill(UUID userId, String skills) {
        List<String> skillList = List.of(skills.split(","));

        boolean isValid = true;

        for (String skill : skillList) {
            if (!SUPPORTED_SKILLS.contains(skill.toLowerCase().trim())) {
                isValid = false;
                break;
            }
        }

        Certification certification = new Certification();
        certification.setUserId(userId);
        certification.setSkills(skills);

        certificationRepository.save(certification);

        SkillVerifiedEvent event = new SkillVerifiedEvent();
        event.setUserId(userId);
        event.setVerified(isValid);

        kafkaProducerService.sendSkillVerifiedEvent(event);

        return certification;
    }
}