package com.green.yogizogi.service;

import com.green.yogizogi.dto.AddressDTO;
import com.green.yogizogi.entity.Address;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.repository.AddressRepository;
import com.green.yogizogi.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;


    @Override
    public Long getMemberByEmail(AddressDTO addressDTO, String email) {
        Member member = memberRepository.findByEmail(email);

        Address address = dtoToEntity(addressDTO);
        address.setMember(member);

        Address saveAddress = addressRepository.save(address);
        System.out.println(saveAddress.getId());
        return saveAddress.getId();
    }

    @Override
    public Long register(AddressDTO addressDTO) {
        Address address = Address.builder()
                .address1(addressDTO.getStoreAddress1())
                .address2(addressDTO.getStoreAddress2())
                .address3(addressDTO.getStoreAddress3())
                .build();

        Address savedAddress = addressRepository.save(address);
        return savedAddress.getId();
    }

    @Override
    @Transactional
    public List<AddressDTO> addressList(String email) {
        Member member = memberRepository.findByEmail(email);
        List<Address> addressList = addressRepository.findByMember(member);
        return addressList.stream().map(address->entityToDto(address)).collect(Collectors.toList());
    }

}