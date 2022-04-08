package de.presti.ree6.webinterface.sql.entities;

import net.dv8tion.jda.api.entities.User;

/**
 * Utility class to store information about a Users
 * Experience and their Level.
 */
public class UserLevel {

    // The ID of the User.
    String userId;

    // The User.
    User user;

    // His XP and Level.
    long experience, level = 0;

    // The Rank of the User.
    int rank;

    /**
     * Constructor to create a UserLevel with the needed Data.
     *
     * @param userId     the ID of the User.
     * @param rank       the current Rank in the leaderboard.
     * @param experience his XP count.
     */
    public UserLevel(String userId, int rank, long experience) {
        this.userId = userId;
        this.experience = experience;
        this.rank = rank;

        long tempXp = experience;

        while (tempXp > 1000) {
            tempXp -= 1000;
            level++;
        }
    }


    /**
     * Constructor to create a UserLevel with the needed Data.
     *
     * @param userId     the ID of the User.
     * @param rank       the current Rank of the User.
     * @param experience his XP count.
     * @param level      his Level.
     */
    public UserLevel(String userId, int rank, long experience, long level) {
        this.userId = userId;
        this.rank = rank;
        this.experience = experience;
        this.level = level;
    }

    /**
     * Get the ID of the wanted User.
     *
     * @return the ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Change the ID of the User.
     *
     * @param userId the new ID.
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * Change the User Entity of the User.
     *
     * @param user the new User.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the XP count of the User.
     *
     * @return the XP count.
     */
    public long getExperience() {
        return experience;
    }

    /**
     * Change the XP count.
     *
     * @param experience new XP count.
     */
    public void setExperience(long experience) {
        this.experience = experience;
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
     * Get the current Rank of the User.
     * @return the current rank.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Added experience to the UserLevel.
     * @param experience the experience that should be added.
     * @return true, if you leveled up | false, if not.
     */
    public boolean addExperience(long experience) {
        long newExperience = getExperience() + experience;

        long calculatedLevel = calculateLevel(newExperience);

        setExperience(newExperience);

        if (calculatedLevel > level) {
            level = calculatedLevel;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the needed Experience for the next Level.
     * @return the needed Experience.
     */
    public long getExperienceForNextLevel() {
        float tempXp = getExperience();

        int multiplier = 1;

        while (tempXp > 1000) {
            tempXp -= 1000;
            multiplier++;
        }
        return 1000L * multiplier;
    }

    /**
     * Get the current Progress of the User.
     *
     * @return the Progress.
     */
    public int getProgress() {
        float tempXp = getExperience();

        while (tempXp > 1000) {
            tempXp -= 1000;
        }

        return Math.round(((tempXp / 1000F) * 100F));
    }

    /**
     * Get the current Experience but formatted.
     * @return a formatted version of the current Experience.
     */
    public String getFormattedExperience() {
        String end;

        if(getExperience() >= 1000000000000L) {
            end = ((getExperience() / 1000000000000L) + "").replace("l", "") + "mil";
        } else if(getExperience() >= 1000000000) {
            end = ((getExperience() / 1000000000) + "").replace("l", "") + "mil";
        } else if(getExperience() >= 1000000) {
            end = ((getExperience() / 1000000) + "").replace("l", "") + "mio";
        } else if(getExperience() >= 1000) {
            end = ((getExperience() / 1000) + "").replace("l", "") + "k";
        } else {
            end = "" + getExperience();
        }

        return end;
    }

    /**
     * Get the Experience but formatted.
     *
     * @param experience the Experience that should be formatted.
     *
     * @return a formatted version of the Experience.
     */
    public String getFormattedExperience(long experience) {
        String end;

        if(experience >= 1000000000000L) {
            end = ((experience / 1000000000000L) + "").replace("l", "") + "mil";
        } else if(experience >= 1000000000) {
            end = ((experience / 1000000000) + "").replace("l", "") + "mil";
        } else if(experience >= 1000000) {
            end = ((experience / 1000000) + "").replace("l", "") + "mio";
        } else if(experience >= 1000) {
            end = ((experience / 1000) + "").replace("l", "") + "k";
        } else {
            end = "" + getExperience();
        }

        return end;
    }

    /**
     * Calculate on which Level you would be by your experience.
     * @param experience the experience.
     * @return which Level.
     */
    public long calculateLevel(long experience) {
        long calculatedLevel = 0;

        while (experience > 1000) {
            experience -= 1000;
            calculatedLevel++;
        }

        return calculatedLevel;
    }
}
