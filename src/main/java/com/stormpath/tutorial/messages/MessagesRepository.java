package com.stormpath.tutorial.messages;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tomasz.lelek on 30/01/16.
 */
public interface MessagesRepository extends JpaRepository<Message, Long> {
}
