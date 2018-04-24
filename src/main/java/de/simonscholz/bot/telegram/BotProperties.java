package de.simonscholz.bot.telegram;

import com.github.fzakaria.ascii85.Ascii85;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@ConfigurationProperties("bot")
public class BotProperties {
	private String apiKey = "<~2)?sD1b^^E1HIff85!iFG@FQD7kd=c6u7'87sIpO;+YhI=tjN\"=>45cEr~>";

	public String getApiKey() {
		return new String(Ascii85.decode(apiKey), StandardCharsets.US_ASCII);
	}
}