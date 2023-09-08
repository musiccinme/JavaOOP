package glacialExpedition.core;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.StateRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private ExplorerRepository explorerRepository;
    private StateRepository stateRepository;
    private int countExploredStates;

    public ControllerImpl(){
        this.explorerRepository = new ExplorerRepository();
        this.stateRepository = new StateRepository();
    }

    @Override
    public String addExplorer(String type, String explorerName) {

        switch (type){
            case "AnimalExplorer":
                AnimalExplorer animalExplorer = new AnimalExplorer(explorerName);
                explorerRepository.add(animalExplorer);
                break;
            case "GlacierExplorer":
                GlacierExplorer glacierExplorer = new GlacierExplorer(explorerName);
                explorerRepository.add(glacierExplorer);
                break;
            case "NaturalExplorer":
                NaturalExplorer naturalExplorer = new NaturalExplorer(explorerName);
                explorerRepository.add(naturalExplorer);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.EXPLORER_INVALID_TYPE);
        }

        return String.format(ConstantMessages.EXPLORER_ADDED, type, explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        State state = new StateImpl(stateName);
        state.getExhibits().addAll(Arrays.asList(exhibits));
        stateRepository.add(state);
        return String.format(ConstantMessages.STATE_ADDED, stateName);

    }

    @Override
    public String retireExplorer(String explorerName) {
        if(this.explorerRepository.getCollection().stream().noneMatch(a -> a.getName().equals(explorerName))){
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXPLORER_DOES_NOT_EXIST, explorerName));
        }
        this.explorerRepository.remove(explorerRepository.byName(explorerName));
        return String.format(ConstantMessages.EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        List<Explorer> suitableExplorers = explorerRepository.getCollection().stream()
                .filter(a -> a.getEnergy() > 50).collect(Collectors.toList());

        if(suitableExplorers.size() == 0){
            throw new IllegalArgumentException(ExceptionMessages.STATE_EXPLORERS_DOES_NOT_EXISTS);
        }

        Mission mission = new MissionImpl();
        State state = stateRepository.byName(stateName);
        int sizeBefore = suitableExplorers.size();
        mission.explore(state, suitableExplorers);
        countExploredStates++;
        int retired = sizeBefore - suitableExplorers.size();
        return String.format(ConstantMessages.STATE_EXPLORER, stateName, retired);

    }

    @Override
    public String finalResult() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(ConstantMessages.FINAL_STATE_EXPLORED, countExploredStates))
                .append(System.lineSeparator());
        builder.append(ConstantMessages.FINAL_EXPLORER_INFO);
        explorerRepository.getCollection().stream().forEach(a -> {
            builder.append(String.format(ConstantMessages.FINAL_EXPLORER_NAME, a.getName()))
                    .append(System.lineSeparator());
            builder.append((String.format(ConstantMessages.FINAL_EXPLORER_ENERGY, a.getEnergy())))
                    .append(System.lineSeparator());
            if(a.getSuitcase().getExhibits().size() == 0){
                builder.append((String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS, "none")))
                        .append(System.lineSeparator());
            }else {
                Collection<String> items = a.getSuitcase().getExhibits();
                builder.append((String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS, String.join(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER, items))))
                        .append(System.lineSeparator());
            }
        });
        return builder.toString();
    }
}
