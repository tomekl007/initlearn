package com.stormpath.tutorial.messages;

import org.junit.Test;

import java.util.LinkedHashMap;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by tomasz.lelek on 03/01/16.
 */
public class MessageTest {

    @Test
    public void shouldMapToMessage(){
        //given
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("text", "A");
        linkedHashMap.put("timestamp", 1l);
        linkedHashMap.put("fromEmail", "f");
        linkedHashMap.put("toEmail", "t");

        //when
        Message message = Message.mapFromLinkedHashMap(linkedHashMap);

        //then
        assertThat(message.timestamp).isEqualTo(1l);
    }

    @Test
    public void shouldMapToMessageWhenOneValueIsNull(){
        //given
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("text", "A");
        linkedHashMap.put("timestamp", null);
        linkedHashMap.put("fromEmail", "f");
        linkedHashMap.put("toEmail", "t");

        //when
        Message message = Message.mapFromLinkedHashMap(linkedHashMap);

        //then
        assertThat(message.timestamp).isNull();
    }

    @Test
    public void shouldMapFromMessageToLinkedHashMap(){
        //given
        Message message = new Message("a", 1l,"f","t");
        //when
        LinkedHashMap linkedHashMap = Message.toLinkedHashMap(message);

        //then
        assertThat(linkedHashMap.get("text")).isEqualTo("a");
    }

    @Test
    public void shouldMapFromMessageToLinkedHashMapWhenOneValueIsNull(){
        //given
        Message message = new Message("a", null,"f","t");
        //when
        LinkedHashMap linkedHashMap = Message.toLinkedHashMap(message);

        //then
        assertThat(linkedHashMap.get("timestamp")).isNull();
    }
}