package byow.Core;

public class Avatar {
    int x;
    int y;
    public Avatar(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void moveUp(){
        this.y ++;
    }
    public void moveDown(){
        this.y --;
    }
    public void moveRight(){
        this.x ++;
    }
    public void moveLeft(){
        this.x --;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
