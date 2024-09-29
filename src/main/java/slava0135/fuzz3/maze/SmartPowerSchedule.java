package slava0135.fuzz3.maze;

import java.util.List;

public class SmartPowerSchedule extends PowerSchedule {
    PathIDGenerator pathIDGenerator = new PathIDGenerator();

    @Override
    public void assignEnergy(List<Seed> population) {
        for (Seed seed : population) {
            int pathId = pathIDGenerator.getPathID(seed.coverage);
            double frequency = getPathFrequency(pathId);
            seed.setEnergy(1 / Math.pow(frequency, 1.1));
        }     
    }

    private int getPathFrequency(int pathId) {
        return pathFrequency.getOrDefault(pathId, 0);
    }     
}
