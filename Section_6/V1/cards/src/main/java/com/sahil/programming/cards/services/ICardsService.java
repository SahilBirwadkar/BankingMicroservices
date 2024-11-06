package com.sahil.programming.cards.services;

import com.sahil.programming.cards.Models.Cards;
import com.sahil.programming.cards.dtos.CardDto;

public interface ICardsService {
    Cards createCard(String mobileNumber);

    CardDto fetchCardDetails(String mobileNumber);

    boolean updateCardDetails(CardDto cardDto);

    boolean deleteCardDetails(String mobileNumber);
}
