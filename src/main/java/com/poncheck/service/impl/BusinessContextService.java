package com.poncheck.service.impl;

import com.poncheck.entity.Business;
import com.poncheck.entity.User;
import com.poncheck.enums.Role;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.exception.UnauthorizedActionException;
import com.poncheck.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessContextService {
    private final BusinessRepository businessRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public Business getBusiness(Long businessId){
        User currentUser = authenticatedUserService.getCurrentUser();

        if(currentUser.getRole() == Role.ADMIN){
            return businessRepository.findById(businessId)
                    .orElseThrow(() -> new ResourceNotFoundException("BUSINESS_NOT_FOUND", "Business Not Found", "business", businessId));
        }

        Business userBusiness = currentUser.getBusiness();
        if(businessId != null && !businessId.equals(userBusiness.getId())){
            throw new UnauthorizedActionException("UNAUTHORIZED_ACTION", "You cannot access another business");
        }

        return userBusiness;
    }

    public Business getCurrentBusiness() {
        return authenticatedUserService.getCurrentUser().getBusiness();
    }
}
