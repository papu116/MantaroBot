package net.kodehawa.mantarobot.commands.moderation;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.kodehawa.mantarobot.MantaroBot;
import net.kodehawa.mantarobot.data.MantaroData;
import net.kodehawa.mantarobot.db.entities.GuildData;

public class ModLog {

    public enum ModAction {
        TEMP_BAN, BAN, KICK, MUTE, UNMUTE, WARN, PRUNE
    }

	public static void log(Member author, User target, String reason, ModAction action, long caseN, String... time) {
		GuildData guildDB = MantaroData.db().getGuild(author.getGuild());
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.addField("Responsible Moderator", author.getEffectiveName(), true);
		if(target != null) embedBuilder.addField("Member", target.getName(), true);
		embedBuilder.addField("Reason", reason, false);
		if(target != null){
			embedBuilder.setThumbnail(target.getEffectiveAvatarUrl());
		} else {
			embedBuilder.setThumbnail(author.getUser().getEffectiveAvatarUrl());
		}
		switch (action) {
			case BAN:
				embedBuilder.setAuthor("Ban | Case #" + caseN, null, author.getUser().getEffectiveAvatarUrl());
				break;
			case TEMP_BAN:
				embedBuilder.setAuthor("Temp Ban | Case #" + caseN, null, author.getUser().getEffectiveAvatarUrl())
						.addField("Time", time[0], true);
				break;
			case KICK:
				embedBuilder.setAuthor("Kick | Case #" + caseN, null, author.getUser().getEffectiveAvatarUrl());
				break;
			case MUTE:
				embedBuilder.setAuthor("Mute | Case #" + caseN, null, author.getUser().getEffectiveAvatarUrl());
				break;
			case UNMUTE:
				embedBuilder.setAuthor("Un-mute | Case #" + caseN, null, author.getUser().getEffectiveAvatarUrl());
				break;
			case PRUNE:
				embedBuilder.setAuthor("Prune | Case #" + caseN, null, author.getUser().getEffectiveAvatarUrl());
				break;
		case WARN:
				embedBuilder.setAuthor("Warn | Case #" + caseN, null, author.getUser().getEffectiveAvatarUrl());
				break;}

        if (guildDB.getData().getGuildLogChannel() != null) {
            if(MantaroBot.getInstance().getTextChannelById(guildDB.getData().getGuildLogChannel()) != null) {
                MantaroBot.getInstance().getTextChannelById(guildDB.getData().getGuildLogChannel()).sendMessage(embedBuilder.build()).queue();
            }
        }
    }

    public static void logUnban(Member author, String target, String reason) {
        GuildData guildDB = MantaroData.db().getGuild(author.getGuild());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.addField("Responsible Moderator", author.getEffectiveName(), true);
        embedBuilder.addField("Member ID", target, true);
        embedBuilder.addField("Reason", reason, false);
        embedBuilder.setAuthor("Unban", null, author.getUser().getEffectiveAvatarUrl());
        if (guildDB.getData().getGuildLogChannel() != null) {
            MantaroBot.getInstance().getTextChannelById(guildDB.getData().getGuildLogChannel()).sendMessage(embedBuilder.build()).queue();
        }
    }
}

