package fr.matthieu42.gollumbot.dialog;

import fr.matthieu42.gollumbot.music.MusicManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MentionHandler {
    protected final MusicManager manager;

    public MentionHandler(MusicManager manager) {
        this.manager = manager;
    }

    public void handle(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw().toLowerCase();
        TextChannel currentChannel = event.getTextChannel();
        Guild currentGuild = event.getGuild();
        Random rand = new Random();
        Boolean predefined = false;
        try {
            ArrayList<String> keyWord = new ArrayList<>(Arrays.asList(new String(Files.readAllBytes(Paths.get("./resources/text/keyword.txt"))).split("\n")));
            //Predefined answer
            for (String s : keyWord){
                if(msg.contains(s))
                    predefined = true;
            }
            if(predefined) {
                if (msg.contains("bonjour"))
                    currentChannel.sendMessage("Bonjour glorieux " + event.getAuthor().getAsMention()).complete();
                if (msg.contains("qui") || msg.contains("quoi"))
                    currentChannel.sendMessage("Pas demander ! Aucune importance !").complete();
                if (msg.contains("sméagol"))
                    currentChannel.sendMessage("Mon...nom!...Sméagol ! Gollum ! Gollum ").complete();
                if (msg.contains("chante")) {
                    int random = rand.nextInt(2);
                    VoiceChannel voiceChannel = currentGuild.getMember(event.getAuthor()).getVoiceState().getChannel();
                    if (!currentGuild.getAudioManager().isConnected() && !currentGuild.getAudioManager().isAttemptingToConnect()) {
                        if (voiceChannel != null)
                            currentGuild.getAudioManager().openAudioConnection(voiceChannel);
                    }
                    switch (random) {
                        case 0:
                            currentChannel.sendMessage("Le lac est beau\n" +
                                    "Fraiche est son eau\n" +
                                    "C'est délicieux\n" +
                                    "C'que nous voulons\n" +
                                    "C'est du poisson\n" +
                                    "Fort bien goûteux\n" +
                                    "\n").complete();
                            if (voiceChannel != null) {
                                manager.loadTrack(currentChannel, "https://www.youtube.com/watch?v=zY5oRoG99Y0");
                            }
                            break;
                        case 1:
                            currentChannel.sendMessage("Pauvre petit moucheron\n" +
                                    "Pourquoi es-tu grognon\n" +
                                    "Dans la toile emprisonné\n" +
                                    "Bientôt tu seras mangé").complete();
                            if (voiceChannel != null) {
                                manager.loadTrack(currentChannel, "https://www.youtube.com/watch?v=2CsLCL3eB2s");
                            }
                            break;
                    }
                }
            }
            //randomised answer
            else {
                String[] answer = new String(Files.readAllBytes(Paths.get("./resources/text/answer.txt"))).split("\n");
                int random = rand.nextInt(answer.length);
                currentChannel.sendMessage(answer[random]).complete();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
