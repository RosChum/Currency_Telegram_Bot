package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j

public class CryptoCurrencyService {
    private final AtomicReference<Double> price = new AtomicReference<>();
    private final BinanceClient client;
    private final SubscriberRepository subscriberRepository;

    public CryptoCurrencyService(BinanceClient client, SubscriberRepository subscriberRepository) {
        this.client = client;
        this.subscriberRepository = subscriberRepository;
    }

    public double getBitcoinPrice() throws IOException {
        if (price.get() == null) {
            price.set(client.getBitcoinPrice());
        }
        return price.get();
    }

    public void saveUser(Long telegramId){
        Subscriber subscriber = new Subscriber();
        subscriber.setIdTelegram(telegramId);

        subscriberRepository.save(subscriber);
    }
    public void updateSubscribe(Long telegramId, String value){
        Subscriber subscriber = subscriberRepository.findByIdTelegram(telegramId);
        subscriber.setPriceSubscribe(Double.valueOf(value));
        subscriberRepository.save(subscriber);
    }

}
