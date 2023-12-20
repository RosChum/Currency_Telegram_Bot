package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

        if (arguments.length > 0) {
            service.updateSubscribe(message.getFrom().getId(), arguments[0]);

            SendMessage answer = new SendMessage();
            answer.setChatId(message.getChatId());

            try {
                answer.setText("Текущая цена биткоина " + TextUtil.toString(service.getBitcoinPrice()) + " USD");
                absSender.execute(answer);
                answer.setText("Новая подписка создана на стоимость " + TextUtil.toString(service.getPriseSubscribe(message.getFrom().getId())) + " USD");
                absSender.execute(answer);
            } catch (Exception e) {
                log.error("Ошибка возникла /get_price методе");
            }

        }


    }
}