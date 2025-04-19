package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.ResponseDto;
import com.example.maktabproject1.dto.SubServiceDto;

import java.util.List;

public interface SubServiceService {

    ResponseDto<SubServiceDto> createSubService(SubServiceDto dto);

    ResponseDto<SubServiceDto> getSubServiceById(Long id);

    ResponseDto<List<SubServiceDto>> getAllSubServices();

    ResponseDto<SubServiceDto> updateSubService(Long id, SubServiceDto dto);

    ResponseDto<Void> deleteSubService(Long id);
}
