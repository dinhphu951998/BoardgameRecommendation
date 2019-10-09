function onLoad() {
    var radios = document.querySelectorAll("input[type='radio']");
    radios.forEach(radio => {
        radio.addEventListener('change', function() {
            onVote(radio);
        });
    });

}

function onVote(obj) {
    var gameId = obj.getAttribute("name");
    var point = obj.getAttribute("value");
//    Vote v = new Vote(gameId, point);




}

class Vote {

    gameId;
    point;

    constructor(gameId, point) {
        this.gameId = gameId;
        this.point = point;
    }

}