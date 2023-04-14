package com.eisen.wrkncacnterbot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import com.bueno.spi.service.BotServiceProvider;

import java.util.List;

public class WrkncacnterBot implements BotServiceProvider {
    private final WinningStrategy winningStrategy;

    public WrkncacnterBot() {
        winningStrategy = new WinningStrategy();
    }
    @Override
    public int getRaiseResponse(GameIntel intel) {
        return winningStrategy.getRaiseResponse(intel);
    }

    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        return winningStrategy.getMaoDeOnzeResponse(intel);
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        return winningStrategy.decideIfRaises(intel);
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
        return winningStrategy.chooseCard(intel);
    }
}
