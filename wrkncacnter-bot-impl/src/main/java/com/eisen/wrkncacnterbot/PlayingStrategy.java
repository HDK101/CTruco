package com.eisen.wrkncacnterbot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;

public interface PlayingStrategy {
    int getRaiseResponse(GameIntel intel);
    boolean getMaoDeOnzeResponse(GameIntel intel);
    boolean decideIfRaises(GameIntel intel);
    CardToPlay chooseCard(GameIntel intel);
}