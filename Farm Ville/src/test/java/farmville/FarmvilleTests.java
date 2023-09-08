package farmville;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FarmvilleTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Farm
    private Farm farm;
    private Animal animal;

    @Before
    public void setUp() {
        this.farm = new Farm("Mari", 2);
        this.animal = new Animal("Sheep", 2);
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(farm.getName(), "Mari");
        //Assert.assertEquals("Mari", farm.getName());
    }

    @Test
    public void testGetCapacity(){
        Assert.assertEquals(farm.getCapacity(), 2);
    }

    /*@Test
    public void testGetCount(){
        Assert.assertEquals(farm.getCount(), 1);
    }*/

    @Test(expected = IllegalArgumentException.class)
    public void testFarmIsFull(){
        this.farm.add(animal);
        Animal animal1 = new Animal("Pig", 1);
        this.farm.add(animal1);
        Animal animal2 = new Animal("Chicken", 4);
        this.farm.add(animal2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnimalExists() {
        this.farm.add(animal);
        this.farm.add(animal);
    }

    @Test
    public void testAddSuccessful(){
        Assert.assertEquals(0, farm.getCount());
        farm.add(animal);
        Assert.assertEquals(1, farm.getCount());
    }

    @Test
    public void testNullRemove(){
        Animal animal1 = new Animal("Horse", 1);
        //farm.remove("Horse");
        Assert.assertEquals(false, farm.remove("Horse"));
    }

    @Test
    public void testRemoveSuccessful(){
        farm.add(animal);
        Assert.assertEquals(1, farm.getCount());
        farm.remove(animal.getType());
        Assert.assertEquals(0, farm.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCapacityLessThanZero(){
        Farm farm1 = new Farm("Krisi", -1);

    }

    @Test(expected = NullPointerException.class)
    public void testSetNameNull(){
        Farm farm1 = new Farm(" ", 4);

    }


}
