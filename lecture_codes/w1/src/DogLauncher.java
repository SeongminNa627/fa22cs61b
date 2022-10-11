/** The DogLauncher class will 'test drive' the Dog class*/
public class DogLauncher {
    public static void main(String[] args){
        Dog aRandomDog = new Dog(51);
        aRandomDog.makeNoise();
        Dog d1 = new Dog(51);
        Dog d2 = new Dog(100);
        Dog bigger = Dog.maxDog(d1, d2);
        bigger.makeNoise();
        Dog larger = d2.maxDog(d1);
        larger.makeNoise();

    }

}
/**
 1. To run a class, we must define a main method.
    - Not all classes have a main method!
 * Roughly speaking,
 * static == anything that is constant throughout the class.
 * non-static == anything that can be instaniated and can be used as an instance.


 2. Static vs Non-Static
    - Static methods are invoked using the class name, e.g. Dog.makeNoise();
    - Instance methods are invoked using an instance name, e.g. maya.makeNoise();
    - Non-static members cannot be invoked using class name: Dog.makeNoise() NOOOOOO!!!
    - Static methods must access instance variables via a specific instance, e.g. d1.
 */