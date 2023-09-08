package glacialExpedition.repositories;

import glacialExpedition.models.explorers.Explorer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExplorerRepository implements Repository<Explorer> {
    private List<Explorer> explorers;

    public ExplorerRepository(){
        this.explorers = new ArrayList<>();
    }

    @Override
    public Collection<Explorer> getCollection() {
        return Collections.unmodifiableCollection(this.explorers);
    }

    @Override
    public void add(Explorer entity) {
        if (this.explorers.stream().noneMatch(a -> a.getName().equals(entity.getName()))) {
            this.explorers.add(entity);
        }
    }

    @Override
    public boolean remove(Explorer entity) {
        return explorers.remove(entity);
    }

    @Override
    public Explorer byName(String name) {
        for (Explorer explorer : explorers) {
            if(explorer.getName().equals(name)){
                return explorer;
            }
        }
        return null;
    }
}
