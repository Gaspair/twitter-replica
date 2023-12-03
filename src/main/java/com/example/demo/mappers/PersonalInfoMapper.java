package com.example.demo.mappers;

import com.example.demo.dto.PersonalInfoDTO;
import com.example.demo.model.PersonalInfo;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonalInfoMapper {

    PersonalInfoMapper INSTANCE = Mappers.getMapper( PersonalInfoMapper.class );

    @Mapping(target = "firstName", source = "personalInfo.firstName")
    @Mapping(target = "lastName", source = "personalInfo.lastName")
    @Mapping(target = "married", source = "personalInfo.married")
    @Mapping(target = "email", source = "personalInfo.email")
    PersonalInfoDTO personalInfoToPersonalInfoDTO(PersonalInfo personalInfo);

    @Mapping(target = "firstName", source = "personalInfoDTO.firstName")
    @Mapping(target = "lastName", source = "personalInfoDTO.lastName")
    @Mapping(target = "married", source = "personalInfoDTO.married")
    @Mapping(target = "email", source = "personalInfoDTO.email")
    PersonalInfo personalInfoDTOToPersonalInfo(PersonalInfoDTO personalInfoDTO);
}
