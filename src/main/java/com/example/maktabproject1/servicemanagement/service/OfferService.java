package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.dto.OfferDTO;

import java.util.List;

public interface OfferService {

    OfferDTO createOffer(OfferDTO offerDTO);

    OfferDTO getOfferById(Long id);

    List<OfferDTO> getAllOffers();

    OfferDTO updateOffer(Long id, OfferDTO offerDTO);

    void deleteOffer(Long id);

    List<OfferDTO> getOffersByOrderId(Long orderId);

    void acceptOffer(Long orderId, Long offerId);
}