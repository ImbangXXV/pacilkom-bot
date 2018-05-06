package com.pacilkom.feats.example;

import com.pacilkom.feats.interfaces.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class HelpCommand implements BotCommand {

    public SendMessage execute(Long chatId) throws Exception {
        return new SendMessage(chatId, "Here are some features you may want from me:\n" +
                "Basic Commands Category:\n" +
                "1. /start = Shows welcome message, start the bot connection with your chat.\n" +
                "2. /help = Shows list of commands currently available.\n" +
                "3. /about = Shows credits of this bot's creators.\n" +
                "4. /hello [text] = Shows \"Hello, this is your message: [text]\"\n\n" +
                "Commands with No Authentication:\n" +
                "5. /news = Shows 5 recent announcements in main page of SCELE Fasilkom UI.\n" +
                "6. /time = Shows current time according to Fasilkom UI server time.\n\n" +
                "Commands with CSUI Account Authentication: (you must login first " +
                "to use these features)\n" +
                "7. /record = Shows your academic record in CSUI Faculty. There 3 features " +
                "on record, you can either see the specific course, summarizeIp record on" +
                " a semester, or just summarizeIp all the courses you've taken so far (IPK)\n" +
                "8. Coming soon\n" +
                "9. Coming soon\n" +
                "10. Coming soon\n");
    }
}
