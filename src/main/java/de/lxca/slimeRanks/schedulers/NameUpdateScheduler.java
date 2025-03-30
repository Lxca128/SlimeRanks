package de.lxca.slimeRanks.schedulers;

import de.lxca.slimeRanks.objects.RankManager;

public class NameUpdateScheduler implements Runnable {
    @Override
    public void run() {
        RankManager.getInstance().reloadDisplays();
    }
}
