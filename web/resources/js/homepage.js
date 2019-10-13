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
    var searchValue = document.getElementById("search-input").value.trim();
    if (searchValue && searchValue.length != 0) {
        var result = search(searchValue);
        if (!result) {
            var confirm = window.confirm("Click ok to perform advanced search");
            if (confirm) {
                document.getElementById("search-form").submit();
            }
            return;
        }
        var gameSection = document.getElementsByClassName("game-list")[0];
        gameSection.replaceWith(result);
    }
}

function onBeforeUnload() {
    if (votes != null && votes.length > 0) {
        var url = "vote";
        var param = "xml=" + Utils.convertArrayToXML(votes, "votes");
        Utils.callToServer(url, "POST", param, false, function() {
            localStorage.removeItem("votes");
        });
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
    searchValue = searchValue.toLowerCase();
    var exp = "//games[contains(translate(title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + searchValue + "')]";
    var resultDom = Utils.applyXPath(exp, Utils.parseToXmlDom(gamesString), null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null, "board-game");

    if (!resultDom) {
        return null;
    }

    var resultXsl = new Document();
    resultXsl = Utils.applyXsl(resultDom, xslDom, resultXsl);
    return resultXsl;
}