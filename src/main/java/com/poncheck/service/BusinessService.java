package com.poncheck.service;

import com.poncheck.dto.request.business.CreateBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateActiveBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateOwnerBusinessRequestDTO;
import com.poncheck.dto.response.business.BusinessResponseDTO;

import java.util.List;

public interface BusinessService {
    BusinessResponseDTO getBusinessById(Long id);
    List<BusinessResponseDTO> getActiveBusiness();
    List<BusinessResponseDTO> getInactiveBusiness();
    List<BusinessResponseDTO> getBusinessByOwner(Long id);
    BusinessResponseDTO createBusiness(CreateBusinessRequestDTO data);
    BusinessResponseDTO updateBusiness(Long id, UpdateBusinessRequestDTO data);
    BusinessResponseDTO updateActive(Long id, UpdateActiveBusinessRequestDTO data);
    BusinessResponseDTO updateOwner(Long id, UpdateOwnerBusinessRequestDTO data);
    void deleteBusiness(Long id);
}
