package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
@AllArgsConstructor
public class GetSubscriptionCommand implements IBotCommand {

    private final CryptoCurrencyService service;

    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        Double priseSubscribe = service.getPriseSubscribe(message.getFrom().getId());

        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        if (priseSubscribe != null) {
            answer.setText("Вы подписаны на стоимость биткоина " + TextUtil.toString(priseSubscribe) + " USD");
        } else {
            answer.setText("Активные подписки отсутствуют");
        }
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}