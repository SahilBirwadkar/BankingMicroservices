package com.sahil.programming.cards.mapper;

import com.sahil.programming.cards.Models.Cards;
import com.sahil.programming.cards.dtos.CardDto;

public class CardMapper {
    public static CardDto mapToCardDto(Cards cards,CardDto cardDto) {
        cardDto.setCardNumber(cards.getCardNumber());
        cardDto.setMobileNumber(cards.getMobileNumber());
        cardDto.setCardType(cards.getCardType());
        cardDto.setTotalLimit(cards.getTotalLimit());
        cardDto.setAmountUsed(cards.getAmountUsed());
        cardDto.setAvailableAmount(cards.getAvailableAmount());
        return cardDto;
    }

    public static Cards mapToCards(CardDto cardDto, Cards cards){
        cards.setCardNumber(cardDto.getCardNumber());
        cards.setMobileNumber(cardDto.getMobileNumber());
        cards.setCardType(cardDto.getCardType());
        cards.setTotalLimit(cardDto.getTotalLimit());
        cards.setAmountUsed(cardDto.getAmountUsed());
        cards.setAvailableAmount(cardDto.getAvailableAmount());
        return cards;
    }
}
