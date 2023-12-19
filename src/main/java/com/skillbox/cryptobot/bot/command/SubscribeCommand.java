package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class SubscribeCommand implements IBotCommand {

    private final CryptoCurrencyService service;
    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        for (String s : arguments) {
            System.out.println(s);
        }
//        service.updateSubscribe(message.getFrom().getId(), arguments.toString());

    }
}