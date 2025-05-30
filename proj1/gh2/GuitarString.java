package gh2;

import deque.ArrayDeque;
import edu.princeton.cs.algs4.*;
// TODO: uncomment the following import once you're ready to start this portion
 import deque.Deque;
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    // TODO: uncomment the following line once you're ready to start this portion
     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer array with zeros.
        int cap = (int) (SR/frequency);
        buffer = new ArrayDeque<Double>(cap);

        for(int i = 0; i < cap; i ++){
            buffer.addLast(0.0);
        }

    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        double r;
        for (int i = 0; i < buffer.size(); i ++){
            r = Math.random() - 0.5;
            buffer.removeLast();
            buffer.addFirst(r);
        }

    }
    public void tic() {
        double front = buffer.removeFirst();
        double next = buffer.get(0);
        buffer.addLast(DECAY* (front + next)/2);
    }
    public double sample() {
        return buffer.get(0);
    }
}
