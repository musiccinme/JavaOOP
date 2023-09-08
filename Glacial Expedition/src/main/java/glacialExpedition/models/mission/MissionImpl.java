package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.Collection;
import java.util.List;

public class MissionImpl implements Mission {

    @Override
    public void explore(State state, List<Explorer> explorers) {
        for (int explorer = 0; explorer < explorers.size(); explorer++) {
            if(explorers.get(explorer).getEnergy() <= 0){
                continue;
            }
            List<String> exhibits = state.getExhibits();
            for (int i = 0; i < exhibits.size(); i++) {
                explorers.get(explorer).getSuitcase().getExhibits().add(exhibits.get(i));
                state.getExhibits().remove(exhibits.get(i));
                i--;
                explorers.get(explorer).search();
                if(!explorers.get(explorer).canSearch()){
                    explorers.remove(explorers.get(explorer));
                    explorer--;
                    break;
                }
            }
        }
    }
}
