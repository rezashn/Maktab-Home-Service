package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.dto.SubServiceDto;
import com.example.maktabproject1.ResponseDto;

import java.util.List;

public interface SubServiceService {

    ResponseDto<SubServiceDto> createSubService(SubServiceDto dto);

    ResponseDto<SubServiceDto> getSubServiceById(Long id);

    ResponseDto<List<SubServiceDto>> getAllSubServices();

    ResponseDto<SubServiceDto> updateSubService(Long id, SubServiceDto dto);

    ResponseDto<Void> deleteSubService(Long id);
}
