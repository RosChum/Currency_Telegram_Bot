package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CheckPrice {

    private final BinanceClient client;
    private final SubscriberRepository subscriberRepository;
    private AbsSender absSender;
    private List<Subscriber> subscribers;

    @Scheduled(fixedRateString = "${frequency-course-updates}")
    public void checkPriceBitcoin() {
        try {
            subscribers = subscriberRepository.findAllByPriceSubscribeBefore(client.getBitcoinPrice());
            log.info("Start checkPriceBitcoin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedDelayString = "${frequency-send-notification}")
    @SchedulerLock(name = "TaskScheduler_scheduledTask",
            lockAtLeastFor = "PT10M", lockAtMostFor = "PT15M")
    public void sendNotification() {
        log.info("Start sendNotification");
        SendMessage sendMessage = new SendMessage();
        subscribers.forEach(subscriber -> {
            try {
                Double bitcoinPrice = client.getBitcoinPrice();
                if (subscriber.getPriceSubscribe() < bitcoinPrice) {
                    sendMessage.setChatId(subscriber.getIdTelegram());
                    sendMessage.setText("Пора покупать биткоин, цена биткоина " + TextUtil.toString(bitcoinPrice) + " USD");
                    absSender.execute(sendMessage);
                }
            } catch (IOException | TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
