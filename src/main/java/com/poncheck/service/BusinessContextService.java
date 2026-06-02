package com.poncheck.service;

import com.poncheck.entity.Business;
import com.poncheck.entity.User;
import com.poncheck.enums.Role;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessContextService {
    private final BusinessRepository businessRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public Business getBusiness(Long id){
        User currentUser = authenticatedUserService.getCurrentUser();
        Business business;

        if(currentUser.getRole() == Role.ADMIN){
            business = businessRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Business Not Found", "business", id));
        }else{
            business = currentUser.getBusiness();
        }

        return business;
    }
}
