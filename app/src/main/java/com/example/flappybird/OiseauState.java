package com.example.flappybird;

public class OiseauState {
    public enum State {
        STARTED_MOVING,
        IS_MOVING
    }

    private Oiseau oiseau;
    private State state;

    public OiseauState(Oiseau oiseau) {
        this.oiseau = oiseau;
        this.state = State.STARTED_MOVING;
    }

    public State getState() {
        return state;
    }

    public void update() {
        switch (state) {
            case STARTED_MOVING:
                    state = State.IS_MOVING;
                break;
            case IS_MOVING:
                    state = State.STARTED_MOVING;
                break;
            default:
                break;
        }
    }
}
