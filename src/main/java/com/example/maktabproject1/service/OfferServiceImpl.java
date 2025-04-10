package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.OfferDTO;
import com.example.maktabproject1.entity.OfferEntity;
import com.example.maktabproject1.entity.OrderEntity;
import com.example.maktabproject1.entity.SpecialistEntity;
import com.example.maktabproject1.repository.OfferRepository;
import com.example.maktabproject1.repository.OrderRepository;
import com.example.maktabproject1.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final SpecialistRepository specialistRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, SpecialistRepository specialistRepository, OrderRepository orderRepository) {
        this.offerRepository = offerRepository;
        this.specialistRepository = specialistRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OfferDTO createOffer(OfferDTO offerDTO) {
        OfferEntity offerEntity = new OfferEntity();
        SpecialistEntity specialist = specialistRepository.findById(offerDTO.getSpecialistId()).orElseThrow();
        OrderEntity order = orderRepository.findById(offerDTO.getOrderId()).orElseThrow();

        offerEntity.setSpecialist(specialist);
        offerEntity.setOrder(order);
        offerEntity.setSuggestedPrice(offerDTO.getProposedPrice());
        offerEntity.setOfferDate(LocalDateTime.now());
        offerEntity.setExecutionTime(offerDTO.getOfferDuration());

        OfferEntity savedOffer = offerRepository.save(offerEntity);
        return mapOfferEntityToDto(savedOffer);
    }

    @Override
    public OfferDTO getOfferById(Long id) {
        OfferEntity offerEntity = offerRepository.findById(id).orElseThrow();
        return mapOfferEntityToDto(offerEntity);
    }

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll().stream().map(this::mapOfferEntityToDto).collect(Collectors.toList());
    }

    @Override
    public OfferDTO updateOffer(Long id, OfferDTO offerDTO) {
        OfferEntity offerEntity = offerRepository.findById(id).orElseThrow();
        SpecialistEntity specialist = specialistRepository.findById(offerDTO.getSpecialistId()).orElseThrow();
        OrderEntity order = orderRepository.findById(offerDTO.getOrderId()).orElseThrow();

        offerEntity.setSpecialist(specialist);
        offerEntity.setOrder(order);
        offerEntity.setSuggestedPrice(offerDTO.getProposedPrice());
        offerEntity.setExecutionTime(offerDTO.getOfferDuration());

        OfferEntity updatedOffer = offerRepository.save(offerEntity);
        return mapOfferEntityToDto(updatedOffer);
    }

    @Override
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public List<OfferDTO> getOffersByOrderId(Long orderId) {
        List<OfferEntity> offerEntities = offerRepository.findByOrderId(orderId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferEntity offerEntity : offerEntities) {
            offerDTOs.add(mapOfferEntityToDto(offerEntity));
        }
        return offerDTOs;
    }
    @Override
    public void acceptOffer(Long orderId, Long offerId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow();
        OfferEntity offer = offerRepository.findById(offerId).orElseThrow();
        order.setAcceptedOffer(offer);
        orderRepository.save(order);
    }

    private OfferDTO mapOfferEntityToDto(OfferEntity offerEntity) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offerEntity.getId());
        offerDTO.setSpecialistId(offerEntity.getSpecialist().getId());
        offerDTO.setOrderId(offerEntity.getOrder().getId());
        offerDTO.setProposedPrice(offerEntity.getSuggestedPrice());
        offerDTO.setOfferDate(offerEntity.getOfferDate());
        offerDTO.setOfferDuration(offerEntity.getExecutionTime());
        return offerDTO;
    }
}