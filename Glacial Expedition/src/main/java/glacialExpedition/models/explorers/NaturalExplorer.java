package glacialExpedition.models.explorers;

public class NaturalExplorer extends BaseExplorer {
    private static final double ENERGY = 60;

    public NaturalExplorer(String name) {
        super(name, ENERGY);
    }

    @Override
    public void search() {
            this.setEnergy(this.getEnergy() - 7);

        if(this.getEnergy() < 0){
            this.setEnergy(0);
        }
    }
}
