package com.pacilkom.bot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMethodOnWebhookNotAllowed() throws Exception {
        this.mockMvc.perform(get("/webhook")).andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void appropriateWebhookPostRequest() throws Exception {
        this.mockMvc.perform(post("/webhook").contentType("application/json")
                .content("{\n" +
                "    \"update_id\": 890164771,\n" +
                "    \"message\": {\n" +
                "        \"message_id\": 23,\n" +
                "        \"from\": {\n" +
                "            \"id\": 439700050,\n" +
                "            \"is_bot\": false,\n" +
                "            \"first_name\": \"Bina Sarana Informatika\",\n" +
                "            \"username\": \"BSI_aja\",\n" +
                "            \"language_code\": \"en-gb\"\n" +
                "        },\n" +
                "        \"chat\": {\n" +
                "            \"id\": 439700050,\n" +
                "            \"first_name\": \"Bina Sarana Informatika\",\n" +
                "            \"username\": \"BSI_aja\",\n" +
                "            \"type\": \"private\"\n" +
                "        },\n" +
                "        \"date\": 1524599606,\n" +
                "        \"text\": \"/hello I am human\",\n" +
                "        \"entities\": [\n" +
                "            {\n" +
                "                \"offset\": 0,\n" +
                "                \"length\": 3,\n" +
                "                \"type\": \"bot_command\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.text")
                        .value("Hello, this is your message: I am human"))
                .andExpect(jsonPath("$.method").value("sendmessage"));
    }
}