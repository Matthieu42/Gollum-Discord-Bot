package fr.matthieu42.gollumbot;

import fr.matthieu42.gollumbot.event.MessageListener;
import fr.matthieu42.gollumbot.command.*;
import fr.matthieu42.gollumbot.dialog.MentionHandler;
import fr.matthieu42.gollumbot.music.MusicManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GollumBot {
    public static void main(String[] args) throws IOException {
        try
        {
            Commands commandsList = new Commands();
            MusicManager manager = new MusicManager();
            MentionHandler mentionHandler = new MentionHandler(manager);
            commandsList.addCommands(new HelpCommand(commandsList),
                    new PlayCommand(manager),
                    new ClearCommand(manager),
                    new SkipCommand(manager),
                    new ShowQueueCommand(manager),
                    new WhatGameCommand(),
                    new WebToImageCommand()
            );
            JDA bot = new JDABuilder(AccountType.BOT).setToken(new String(Files.readAllBytes(Paths.get("./resources/text/token.txt")))).setGame(Game.of(Game.GameType.DEFAULT,"Protéger le précieux")).buildAsync();
            bot.addEventListener(new MessageListener(commandsList,mentionHandler));
            System.out.println("Gollum is connected to :");
            for (Guild g :bot.getGuilds()) {
                System.out.println(g.getName());
            }
        } catch (LoginException e)
        {
            e.printStackTrace();
        }
    }

}
