var votes = [];
var gamesDom;
var xslDom;



function initRatePoint(obj) {
    var point = obj.getAttribute("value") * 2;
    var resultRadio = obj.querySelector('input[value="' + point + '"]');
    resultRadio.checked = true;
}


function onLoad() {
    //init vote array
    var voteString = localStorage.getItem("votes");
    votes = JSON.parse(voteString);
    if (votes == null) {
        votes = [];
    }

    //init games
    gamesDom = Utils.parseToXmlDom(gamesString);
    //init stylesheet
    xslDom = Utils.parseToXmlDom(xslString);

    //init rate point
    var rateFieldSet = document.querySelectorAll("fieldset[value]");
    for (var i = 0; i < rateFieldSet.length; i++) {
        initRatePoint(rateFieldSet[i]);
    }

    //init search
    var searchForm = document.getElementById("search-form");
    if (searchForm != null) {
        searchForm.addEventListener("submit", onFormSubmit);
    }
}

function onFormSubmit(e) {
    e.preventDefault();
    console.log(e);
    var searchValue = document.getElementById("search-input").value;
    search(searchValue);
}

function onBeforeUnload() {
    console.log("unload");
    if (votes != null && votes.length > 0) {
        var xhr = Utils.getXMLHttpRequest();
        if (xhr == null) {
            alert("The browser not support XML HTTP");
            return;
        }
        var url = "vote";
        xhr.onreadystatechange = function() {
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
    var point = obj.getAttribute("value") / 2;

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


function search(searchValue) {
    var exp = "//games[contains(title, '" + searchValue + "')]";
    var resultDom = Utils.applyXPath(exp, gamesDom, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null, "board-game");
    console.log(resultDom);

    var resultXsl = new Document();
    resultXsl = Utils.applyXsl(resultDom, xslDom, resultXsl);

    var gameSection = document.getElementsByClassName("game-list")[0];
    //search có kết quả
    if (resultDom.childNodes.length == 0) {
        var notFoundNode = document.createElement("div");
        notFoundNode.setAttribute("class", "game-list");
        var h2 = document.createElement("h2");
        var notFoundText = document.createTextNode("There is no item");
        h2.appendChild(notFoundText);
        notFoundNode.appendChild(h2);
        gameSection.replaceWith(notFoundNode);
        return;
    }

    gameSection.replaceWith(resultXsl);
}