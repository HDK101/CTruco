package com.eisen.wrkncacnterbot;

import com.bueno.spi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WinningStrategy implements PlayingStrategy {

    public boolean hasZap(GameIntel intel) {
        return intel.getCards().stream().anyMatch(card -> card.isZap(intel.getVira()));
    }

    public Optional<TrucoCard> retrieveZap(GameIntel intel) {
        return  intel.getCards().stream().filter(card -> card.isZap(intel.getVira())).findFirst();

    }
    public boolean hasAtleastManilha(GameIntel intel) {
        return intel.getCards().stream().anyMatch(trucoCard -> trucoCard.isManilha(intel.getVira()));
    }

    public Integer calculateDeckValue(GameIntel intel) {
        if (intel.getCards().isEmpty()) return 0;
        return intel.getCards().stream().map(card -> card.relativeValue(intel.getVira())).reduce(Integer::sum).orElseThrow();
    }

    public Optional<TrucoCard> getWeakestCardFromDeck(GameIntel intel) {
        return intel.getCards().stream().max((a, b) -> a.compareValueTo(b, intel.getVira()));
    }

    public Optional<TrucoCard> getHighestCardNonManilha(GameIntel intel) {
        return intel
                .getCards()
                .stream()
                .filter(trucoCard -> !trucoCard.isManilha(intel.getVira()))
                .max((a, b) -> a.compareValueTo(b, intel.getVira()));
    }

    public TrucoCard selectCardNonStrongest(GameIntel intel) {
        TrucoCard weakestCard = getWeakestCardFromDeck(intel).orElseThrow();

        if (intel.getOpponentCard().isPresent()) {
            TrucoCard opponentCard = intel.getOpponentCard().get();
            Optional<TrucoCard> weakestCardButStrongerThanOpponent = intel
                    .getCards()
                    .stream()
                    .filter(trucoCard -> !trucoCard.isManilha(intel.getVira()))
                    .filter(trucoCard -> trucoCard.compareValueTo(opponentCard, intel.getVira()) >= 0)
                    .min((a, b) -> a.compareValueTo(b, intel.getVira()));

            return weakestCardButStrongerThanOpponent.orElse(weakestCard);
        }
        return getHighestCardNonManilha(intel).orElse(weakestCard);
    }
    @Override
    public int getRaiseResponse(GameIntel intel) {
        Integer deckValue = calculateDeckValue(intel);

        if (intel.getHandPoints() == 6 || deckValue >= 24) return 0;

        if (hasZap(intel) || hasAtleastManilha(intel)) return 1;

        return -1;
    }

    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        Optional<TrucoCard> trucoCard = retrieveZap(intel);

        if (trucoCard.isPresent()) return true;

        Integer deckValue = calculateDeckValue(intel);

        return deckValue >= 20;
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        if (intel.getRoundResults().size() == 1) return false;
        else {
            return hasZap(intel);
        }
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
        return CardToPlay.of(selectCardNonStrongest(intel));
    }
}
