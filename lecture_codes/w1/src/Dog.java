public class Dog {
    public int weightInPounds;
    // How big a dog is
    public Dog(int w){
        weightInPounds = w;
    }
    public void makeNoise(){
        if (weightInPounds < 10) {
            System.out.println("yip!");
        }else if (weightInPounds < 30) {
            System.out.println("bark.");
        }else {
            System.out.println("woooooof!");
        }
    }
}
/**
 * Roughly speaking,
 * static == anything that is constant throughout the class.
 * non-static == anything that can be instaniated and can be used as an instance.
 */
