package me.itsjbey.polley.types;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Poll {

    String name;
    String description;
    LinkedHashMap<String, Integer> votes = new LinkedHashMap<>();
    boolean running;
    ArrayList<String> hasVoted = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<String> getVotes() {

        ArrayList<String> strings = new ArrayList<String>();

        for (String s : votes.keySet()) {

            strings.add(s);

        }

        return strings;
    }

    public void setVotes(ArrayList<String> votes) {

        this.votes.clear();

        for (String vote : votes) {

            this.votes.put(vote, 0);

        }

    }

    public int getVotesFor(String s) {

        if (votes.containsKey(s)) {

            return votes.get(s);

        }

        return 0;

    }

    public boolean hasVoted(HumanEntity p) {

        return hasVoted.contains(p.getUniqueId().toString());

    }

    public void addVote(int i, HumanEntity p) {

        if (votes.size() >= i) {

            int j = 1;

            for (String s : votes.keySet()) {

                if (i == j) {

                    System.out.println("Added");

                    votes.put(s, votes.get(s) + 1);

                    hasVoted.add(p.getUniqueId().toString());

                    break;

                }

                j++;

            }

        }

    }

    public void resetVoted() {

        hasVoted.clear();

    }
}
