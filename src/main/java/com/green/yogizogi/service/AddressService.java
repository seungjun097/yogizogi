package com.green.yogizogi.service;

import com.green.yogizogi.dto.AddressDTO;
import com.green.yogizogi.entity.Address;
import com.green.yogizogi.entity.Member;

import java.util.List;

public interface AddressService {
    //주소
    public Long getMemberByEmail(AddressDTO addressDTO, String email);

    Long register(AddressDTO addressDTO);

    List<AddressDTO> addressList(String email);


    default Address dtoToEntity(AddressDTO addressDTO){
        Address address = Address.builder()
                .phone(addressDTO.getPhone())
                .name(addressDTO.getName())
                .address1(addressDTO.getStoreAddress1())
                .address2(addressDTO.getStoreAddress2())
                .address3(addressDTO.getStoreAddress3())
                .build();
        return address;
    }

    default AddressDTO entityToDto(Address address) {
        AddressDTO addressDTO = AddressDTO.builder()
                .name(address.getName())
                .phone(address.getPhone())
                .address_id(address.getId())
                .storeAddress1(address.getAddress1())
                .storeAddress2(address.getAddress2())
                .storeAddress3(address.getAddress3())
                .address_id(address.getId())
                .build();
        return addressDTO;
    }
}
