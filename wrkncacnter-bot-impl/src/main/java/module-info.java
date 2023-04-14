import com.eisen.wrkncacnterbot.WrkncacnterBot;

module wrkncacnter.bot.impl {
    requires bot.spi;
    exports com.eisen.wrkncacnterbot;
    provides com.bueno.spi.service.BotServiceProvider with WrkncacnterBot;
}