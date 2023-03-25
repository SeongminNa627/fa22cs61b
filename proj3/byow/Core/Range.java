package byow.Core;

public class Range{
    int from;
    int to;
    public Range(int from, int to){
        this.from = from;
        this.to = to;
    }
    public boolean equals(Range range){
        return this.from == range.from && this.to == range.to;
    }
}