/** The DogLauncher class will 'test drive' the Dog class*/
public class DogLauncher {
    public static void main(String[] args){
        Dog aRandomDog = new Dog(51);
        aRandomDog.makeNoise();
    }
}
/**
 1. To run a class, we must define a main method.
    - Not all classes have a main method!
 * Roughly speaking,
 * static == anything that is constant throughout the class.
 * non-static == anything that can be instaniated and can be used as an instance.

 */