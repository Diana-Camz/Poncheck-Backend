package com.poncheck.service.impl;

import com.poncheck.dto.request.business.CreateBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateActiveBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateOwnerBusinessRequestDTO;
import com.poncheck.dto.response.business.BusinessResponseDTO;
import com.poncheck.entity.Business;
import com.poncheck.entity.Product;
import com.poncheck.entity.User;
import com.poncheck.enums.Role;
import com.poncheck.exception.InvalidBusinessOwnerException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.BusinessRepository;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository repository;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final BusinessContextService businessContextService;


    @Override
    public BusinessResponseDTO getBusinessById(Long id){
        Business business = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BUSINESS_NOT_FOUND", "Business Not Found", "business", id));

        return new BusinessResponseDTO(business);
    }

    @Override
    public List<BusinessResponseDTO> getActiveBusiness(){
        List<Business> businessList = repository.findByActiveTrue();
        return businessList.stream().map(BusinessResponseDTO::new).toList();
    }

    @Override
    public List<BusinessResponseDTO> getInactiveBusiness(){
        List<Business> businessList = repository.findByActiveFalse();
        return businessList.stream().map(BusinessResponseDTO::new).toList();
    }

    @Override
    public List<BusinessResponseDTO> getBusinessByOwner(Long id){
        List<Business> businessList = repository.findBusinessByOwner_id(id);
        return businessList.stream().map(BusinessResponseDTO::new).toList();
    }

    private String generateCode(String name){
        long totalBusiness = repository.countByName(name);
        String prefix = name.toUpperCase().replace(" ", "_");
        return prefix + "_" + String.format("%02d", totalBusiness + 1);
    }

    @Override
    public BusinessResponseDTO createBusiness(CreateBusinessRequestDTO data){
        User owner = null;
        if(data.ownerId() != null){
            owner = userRepository.findById(data.ownerId())
                    .orElseThrow(() -> new ResourceNotFoundException("OWNER_NOT_FOUND", "Owner Not Found", "user", data.ownerId()));
        }

        String businessCode = generateCode(data.name());

        Business business = new Business(
                data.name().toUpperCase(),
                businessCode,
                data.phone(),
                data.email(),
                data.address(),
                data.description(),
                data.logoUrl(),
                owner
        );
        Business businessSaved = repository.save(business);
        return new BusinessResponseDTO(businessSaved);
    }

    // only this method can be edited by owners
    @Override
    public BusinessResponseDTO updateBusiness(Long businessId, UpdateBusinessRequestDTO data){
        Long ownerId = authenticatedUserService.getCurrentUser().getId();
        Business business = repository.findByIdAndOwner_id(businessId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("BUSINESS_NOT_FOUND", "Business Not Found", "business", businessId));

        business.updateBusiness(
                data.name(),
                data.phone(),
                data.email(),
                data.address(),
                data.description(),
                data.logoUrl()
        );

        Business businessSaved = repository.save(business);
        return new BusinessResponseDTO(businessSaved);
    }

    @Override
    public BusinessResponseDTO updateActive(Long businessId, UpdateActiveBusinessRequestDTO data){
        Business business = repository.findById(businessId)
                        .orElseThrow(() -> new ResourceNotFoundException("BUSINESS_NOT_FOUND", "Business Not Found", "business", businessId));

        business.updateActive(
                data.active()
        );
        Business businessSaved = repository.save(business);
        return new BusinessResponseDTO(businessSaved);
    }

    @Override
    public BusinessResponseDTO updateOwner(Long businessId, UpdateOwnerBusinessRequestDTO data){
        Business business = repository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("BUSINESS_NOT_FOUND", "Business Not Found", "business", businessId));

        User owner = userRepository.findByIdAndBusiness_id(data.ownerId(), businessId)
                        .orElseThrow(() -> new ResourceNotFoundException("OWNER_NOT_FOUND", "Owner Not Found", "user", data.ownerId()));

        if(!business.getActive()){
            throw new InvalidBusinessOwnerException("BUSINESS_NOT_ACTIVE", "Business must be active");
        }

        if(owner.getRole() != Role.OWNER){
            throw new InvalidBusinessOwnerException("INVALID_BUSINESS_ASSIGNED", "User must have OWNER role to be assigned as business owner");
        }
        business.updateBusinessOwner(
                owner
        );

        Business businessSaved = repository.save(business);
        return new BusinessResponseDTO(businessSaved);
    }

    @Override
    public void deleteBusiness(Long id){
        Business business = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BUSINESS_NOT_FOUND", "Business Not Found", "business", id));
        repository.delete(business);
    }
}
