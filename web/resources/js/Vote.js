class Vote {

    gameId;
    point;

    constructor(gameId, point) {
        this.gameId = gameId;
        this.point = point;
    }

    static toXML(vote) {
        return "<vote><votePK><gameId>" + vote.gameId + "</gameId></votePK><point>" + vote.point + "</point></vote>";
    }
}