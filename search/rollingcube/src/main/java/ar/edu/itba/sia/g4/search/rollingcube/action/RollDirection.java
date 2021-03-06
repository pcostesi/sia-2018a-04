package ar.edu.itba.sia.g4.search.rollingcube.action;

public enum RollDirection {
    NORTH(-1,0),
    SOUTH(1,0),
    EAST(0,1),
    WEST(0,-1);

    public final int i;
    public final int j;

    RollDirection(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public RollDirection getOppositeDirection(){
        switch (this){
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            default:
                throw new IllegalArgumentException("Can't roll that way");
        }
    }
}
