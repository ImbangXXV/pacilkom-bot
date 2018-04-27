package com.pacilkom.feats.scele.latestTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

import org.junit.Before;
import org.junit.Test;

public class TimeGetterTest {
    private Pattern pattern;  
    private Matcher matcher;
    private static final String TIME_PATTERN = 
        "(^.*[a-zA-z]{3} [a-zA-z]{3} [0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} [a-zA-z]{3} [0-9]{4})$";
    
    @Before
    public void setUp() throws Exception {
        pattern = Pattern.compile(TIME_PATTERN);  
    }
    
    @Test
    public void hasCorrectSyntaxInSendMessage() {
       String check = TimeGetter.getTime();
       assertTrue(pattern.matcher(check).matches());
    }
}
