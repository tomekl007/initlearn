package com.stormpath.tutorial.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by tomasz.lelek on 30/01/16.
 */
public interface MessagesRepository extends JpaRepository<MessageDb, Long> {

    @Query("select m from MessageDb m where m.from_email = :email OR m.to_email = :email ORDER BY timestamp desc")
    List<MessageDb> getAllConversationsWith(@Param("email") String conversationParticipantEmail);

    @Query("select m from MessageDb m where ( m.from_email = :from_email AND m.to_email = :to_email ) OR " +
            "( m.from_email = :to_email AND m.to_email = :from_email ) ORDER BY timestamp desc LIMIT 1")
    MessageDb getLastMessageForConversations(@Param("from_email") String fromEmail, @Param("to_email") String toEmail);
}
