package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.dto.OfferDTO;
import com.example.maktabproject1.servicemanagement.dto.UpdateOfferDto;

import java.util.List;

public interface OfferService {

    OfferDTO createOffer(OfferDTO offerDTO);

    OfferDTO getOfferById(Long id);

    List<OfferDTO> getAllOffers();

    OfferDTO updateOffer(Long id, UpdateOfferDto offerDTO);

    void deleteOffer(Long id);

    List<OfferDTO> getOffersByOrderId(Long orderId);

    List<OfferDTO> getOffersByOrder(Long orderId);

    void acceptOffer(Long orderId, Long offerId);
}