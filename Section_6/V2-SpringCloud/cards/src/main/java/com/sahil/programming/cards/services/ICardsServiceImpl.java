package com.sahil.programming.cards.services;

import com.sahil.programming.cards.Exception.CardAlreadyExistsException;
import com.sahil.programming.cards.Exception.CardNotFoundException;
import com.sahil.programming.cards.Models.Cards;
import com.sahil.programming.cards.Repositories.CardRepository;
import com.sahil.programming.cards.constants.CardsConstants;
import com.sahil.programming.cards.dtos.CardDto;
import com.sahil.programming.cards.mapper.CardMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ICardsServiceImpl implements ICardsService{

    private CardRepository cardRepository;

    public ICardsServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Cards createCard(String mobileNumber) {
        Optional<Cards> cardsOptional = cardRepository.findByMobileNumber(mobileNumber);

        if (cardsOptional.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobile number : "+mobileNumber);
        }

        Cards cards = new Cards();

        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);

        cards.setCardNumber(Long.toString(randomCardNumber));
        cards.setMobileNumber(mobileNumber);
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);

        return cardRepository.save(cards);
    }

    @Override
    public CardDto fetchCardDetails(String mobileNumber) {
        Optional<Cards> cardsOptional = cardRepository.findByMobileNumber(mobileNumber);
        if (cardsOptional.isEmpty()){
            throw new CardNotFoundException("Card","mobileNumber",mobileNumber);
        }

        return CardMapper.mapToCardDto(cardsOptional.get(),new CardDto());
    }

    @Override
    public boolean updateCardDetails(CardDto cardDto) {

        Optional<Cards> cardsOptional = cardRepository.findByCardNumber(cardDto.getCardNumber());
        if (cardsOptional.isEmpty()){
            throw new CardNotFoundException("Card","Card Number",cardDto.getCardNumber());
        }

        Cards card = CardMapper.mapToCards(cardDto,cardsOptional.get());
        cardRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCardDetails(String mobileNumber) {
        Optional<Cards> cardsOptional = cardRepository.findByMobileNumber(mobileNumber);
        if (cardsOptional.isEmpty()){
            throw new CardNotFoundException("Card","mobileNumber",mobileNumber);
        }
        cardRepository.delete(cardsOptional.get());
        return true;
    }
}
