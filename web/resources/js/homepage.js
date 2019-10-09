var votes = [];

function onLoad() {
    var voteString = localStorage.getItem("votes");
    votes = JSON.parse(voteString);
    if (votes == null) {
        votes = [];
    }
}

function onBeforeUnload() {
    console.log("unload");
    if (votes != null && votes.length > 0) {
        var xhr = getXMLHttpRequest();
        if (xhr == null) {
            alert("The browser not support XML HTTP");
            return;
        }
        var url = "vote";
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                localStorage.removeItem("votes");
            }
        };
        xhr.open("POST", url, false);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        var param = "xml=" + Utils.convertArrayToXML(votes, "votes");
        xhr.send(param);
    }
}



function onVote(obj) {
    var gameId = obj.getAttribute("name");
    var point = obj.getAttribute("value");

    var vote = votes.find(v => v.gameId == gameId);
    if (vote != null) {
        vote.point = point;
    } else {
        vote = new Vote(gameId, point);
        votes.push(vote);
    }
    console.log(Vote.toXML(vote));

    localStorage.setItem("votes", JSON.stringify(votes));
}

function getXMLHttpRequest() {
    var xmlHttp = null;
    try {
        xmlHttp = new XMLHttpRequest();

    } catch (e) {
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
}

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

