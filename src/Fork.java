
public class Fork {
    private int number;
    private boolean free;

    public Fork(int number) {
        this.free = true;
        this.number = number;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getNumber() {
        return number;
    }
}
