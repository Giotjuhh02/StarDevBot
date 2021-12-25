package de.presti.ree6.webinterface.level;

import de.presti.ree6.webinterface.bot.BotInfo;
import net.dv8tion.jda.api.entities.User;

/**
 * Utility class to store information about a Users
 * Experience and their Level.
 */
public class UserLevel {

    // The User.
    User user;

    // His XP and Level.
    long xp = 0, level = 0;

    /**
     * Constructor to create a UserLevel with the needed Data.
     *
     * @param id the ID of the User.
     * @param xp his XP count.
     */
    public UserLevel(String id, long xp) {
        this.user = BotInfo.botInstance.getUserById(id);
        this.xp = xp;

        long tempXp = xp;

        while (tempXp > 1000) {
            tempXp -= 1000;
            level++;
        }
    }


    /**
     * Constructor to create a UserLevel with the needed Data.
     *
     * @param id    the ID of the User.
     * @param xp    his XP count.
     * @param level his Level.
     */
    public UserLevel(String id, long xp, long level) {
        this.user = BotInfo.botInstance.getUserById(id);
        this.xp = xp;
        this.level = level;
    }

    /**
     * Get the wanted User.
     *
     * @return the User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Change the ID of the User.
     *
     * @param id the new ID.
     */
    public void setUser(String id) {
        this.user = BotInfo.botInstance.getUserById(id);
    }

    /**
     * Get the XP count of the User.
     *
     * @return the XP count.
     */
    public long getXp() {
        return xp;
    }

    /**
     * Change the XP count.
     *
     * @param xp new XP count.
     */
    public void setXp(long xp) {
        this.xp = xp;
    }

    /**
     * Get the Level of the User.
     *
     * @return the level.
     */
    public long getLevel() {
        return level;
    }

    /**
     * Change the Level of the User.
     *
     * @param level the new level.
     */
    public void setLevel(long level) {
        this.level = level;
    }

    /**
     * Get the current Progress of the User.
     * @return the Progress.
     */
    public long getProgress() {
        long tempXp = getXp();

        while (tempXp > 1000) {
            tempXp -= 1000;
        }

        return (tempXp / 1000) * 100;
    }
}
