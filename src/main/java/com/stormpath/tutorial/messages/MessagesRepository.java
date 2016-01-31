package com.stormpath.tutorial.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by tomasz.lelek on 30/01/16.
 */
public interface MessagesRepository extends JpaRepository<MessageDb, Long> {

    @Query("select m from Message m where m.from_email = :email OR m.to_email = :email")
    List<MessageDb> getAllConversationsWith(@Param("email") String conversationParticipantEmail);
}
