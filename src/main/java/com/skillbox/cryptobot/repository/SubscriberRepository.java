package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {

    Subscriber findByIdTelegram(Long idTelegram);
    List<Subscriber> findAllByPriceSubscribeAfter(Double price);
}
