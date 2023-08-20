package com.helmetcheck;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class HelmetCheckPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private HelmetCheckConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Helmet check plugin started up...");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Helmet check plugin stopped!");
	}

	@Subscribe
	public void onMenuOpened(MenuOpened e) {
		System.out.println("displayScreenSize Ran!");
		int canvasHeight = client.getCanvasHeight();
		client.getLocalPlayer().setOverheadText(Integer.toString(canvasHeight));
		//client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", Integer.toString(canvasHeight), "");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Helmet Checker says, " + config.greeting(), null);
		}
	}

	@Provides
	HelmetCheckConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(HelmetCheckConfig.class);
	}
}
