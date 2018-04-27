package com.pacilkom.feats.scele.latestTime;

import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class SceleTimeCommandTest {
    
    private Long chatId = Long.valueOf(001);
    private SendMessage msg;
    private Pattern pattern;  
    private Matcher matcher;
    private String time;
    
    private static final String TIME_PATTERN = 
            "(^.*[a-zA-z]{3} [a-zA-z]{3} [0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} [a-zA-z]{3} [0-9]{4})$";

    @Before
    public void setUp() throws Exception {
       
        msg = new SceleTimeCommand().execute(chatId);
        pattern = Pattern.compile(TIME_PATTERN);  
    }

    @Test
    public void hasCorrectSyntaxInSendMessage() {
       String check = msg.getText();
       assertTrue(pattern.matcher(check).matches());
    }
}

