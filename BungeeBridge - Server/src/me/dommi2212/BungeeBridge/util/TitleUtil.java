package me.dommi2212.BungeeBridge.util;

import me.dommi2212.BungeeBridge.PackedTitle;
import me.dommi2212.BungeeBridge.TitleMode;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TitleUtil {
	
	public static void sendTitle(PackedTitle packettitle, ProxiedPlayer player) {
		Title title = BungeeCord.getInstance().createTitle();

		if(packettitle.getMode() == TitleMode.CLEAR) title = title.clear();
		else if(packettitle.getMode() == TitleMode.RESET) title = title.reset();
		else {
			title = title.title(new TextComponent(packettitle.getTitle()));
			title = title.subTitle(new TextComponent(packettitle.getSubtitle()));
			
			if(packettitle.getFadein() >= 0) {
				title = title.fadeIn(packettitle.getFadein());
				title = title.stay(packettitle.getStay());
				title = title.fadeOut(packettitle.getFadeout());
			}
		}
		title.send(player);
	}

}
